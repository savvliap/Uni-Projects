package facilityGame;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;

public class FClient {
	// Server address
	String serverHost;
	int serverPort;

	// Game parameters
	int n;
	long seed;
	EnumGameType gameType;

	// Determine if client is player A or player B
	boolean clientIsPlayerB;

	// Player A or B
	EnumPlayer clientRole;
	EnumPlayer serverRole;

	FPlayer clientPlayer; // The player object for the client
	EnumPlayerType playerType;

	FacilityGame game;
	ObjectInputStream in;
	ObjectOutputStream out;

	boolean verbose; // print information during the execution
	boolean silentMode; // avoid printing info

	// Socket for connecting to the server
	Socket socket;

	// A thread to monitor the player state
	boolean enableMonitorThread = true;
	MonitorThread monitorThread;

	public FClient(FClientArgs clientArgs) {
		this(clientArgs.verbose, clientArgs.serverHost, clientArgs.serverPort, clientArgs.n, clientArgs.seed,
				clientArgs.gameType, clientArgs.playerType, clientArgs.clientIsPlayerB);
	}

	public FClient(boolean parVerbose, String parServerHost, int parServerPort, int parN, long parSeed,
			EnumGameType gameType, EnumPlayerType playerType, boolean parClientIsPlayerB) {
		verbose = parVerbose;
		silentMode = false;
		serverHost = parServerHost;
		serverPort = parServerPort;
		n = parN;
		seed = parSeed;
		this.gameType = gameType;
		this.playerType = playerType;

		clientIsPlayerB = parClientIsPlayerB;
		clientRole = (clientIsPlayerB ? EnumPlayer.PLAYER_B : EnumPlayer.PLAYER_A);
		serverRole = (clientIsPlayerB ? EnumPlayer.PLAYER_A : EnumPlayer.PLAYER_B);

		monitorThread = new MonitorThread();
	}

	public void printGameStatus() {
		if (verbose) {
			game.printStatusLong();
			game.printAllMoves();
		} else {
			if (!silentMode) {
				game.printStatus();
			}
		}
	}

	public void printGameInfo() {
		game.printGameInfo();
	}

	public GameScore getGameScore() {
		return game.getScore();
	}
	
	public boolean checkSuccess() {
		return (game.checkSuccess(clientIsPlayerB) == 1);
	}

	public void initiate() throws FacilityGameException {
		if (!silentMode) {
			System.out.println("FClient: FacilityClient " + Finals.VERSION);
			// System.out.println("FClient: FacilityClient is starting ...");
		}

		// check parameters
		FacilityGame.checkParameters(false, playerType, n, gameType, clientRole);

		if (verbose) {
			System.out.println(
					"FClient: FacilityClient is trying to connect to " + serverHost + " at port " + serverPort);
		}

		try {
			socket = new Socket(InetAddress.getByName(serverHost), serverPort);

			if (verbose) {
				System.out.println("FClient: FacilityClient: connection established !");
			}

			out = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));

			out.writeUTF("FClient " + Finals.VERSION);
			out.flush();

			in = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));

			String msg1FromServer = in.readUTF();
			if (verbose) {
				System.out.println("Message 1 from Server: " + msg1FromServer);
			}
			boolean ok = in.readBoolean();
			if (!ok) {
				throw new FacilityGameException("The server terminated the client connection: " + msg1FromServer);
			}

			if (!silentMode) {
				System.out.println("FClient: Sending game parameters: n:=" + n + ", seed:=" + seed + ", GameType:= "
						+ gameType + ", Client is Player B:= " + clientIsPlayerB);
			}
			out.writeInt(n);
			out.writeLong(seed);
			out.writeObject(gameType);
			out.writeBoolean(clientIsPlayerB);
			out.flush();

			game = new FacilityGame(EnumClientServer.CLIENT, n, seed, gameType);
			// System.out.println("FClient: FacilityClient created local game
			// instance");

			String msg2FromServer = in.readUTF();
			
			if (msg2FromServer.startsWith(Finals.ERROR_MSG_PREFIX)) {
				System.err.println("Message 2 from Server: " + msg2FromServer);
			} else if (!silentMode) {
				System.out.println("Message 2 from Server: " + msg2FromServer);
			}

		} catch (SocketException e) {
			System.err.println("Could not connect to host:" + serverHost + ", port:" + serverPort);
			System.err.println("Are you sure that the server is up and running at this address and port?");

			System.err.println(e);
			System.exit(-1);
			// e.printStackTrace();
		} catch (IOException e) {
			System.err.println(e);
			System.exit(-1);
			// e.printStackTrace();
		} catch (FacilityGameException e) {
			System.err.println(e);
		}

	}

	public void setSilentMode(boolean silentMode) {
		this.silentMode = silentMode;
	}

	public void play(FPlayer parClientPlayer) throws IOException {

		clientPlayer = parClientPlayer;

		// Initialize the player
		clientPlayer.initialize(game);

		monitorThread.setState(game.getCurMoveIndex(), EnumPlayerState.WAITING_FOR_OPPONENT);
		if (enableMonitorThread) {
			monitorThread.start();
		}

		String aboutOpponent = in.readUTF();
		if (clientIsPlayerB) {
			game.setAboutPlayerA(aboutOpponent);
			game.setAboutPlayerB(clientPlayer.about());
		} else {
			game.setAboutPlayerA(clientPlayer.about());
			game.setAboutPlayerB(aboutOpponent);
		}

		out.writeUTF(clientPlayer.about());
		out.flush();

		out.writeInt(clientPlayer.AFM);
		out.flush();

		out.writeUTF(clientPlayer.FirstName);
		out.flush();

		out.writeUTF(clientPlayer.LastName);
		out.flush();

		if (in.readBoolean()) {

			if (!silentMode) {
				System.out.println("FClient (" + clientRole + ") : OK, the game is starting ...");
			}

			int countMoveServer = 0;
			int countMoveClient = 0;

			if (clientIsPlayerB) {
				while (!game.isFinished()) {
					if (verbose) {
						System.out.println("FClient: Receiving move of Server");
					}
					monitorThread.setState(game.getCurMoveIndex(), EnumPlayerState.WAITING_FOR_OPPONENT);
					int moveOfServer = in.readInt();
					game.processMove(moveOfServer, serverRole);
					if (verbose) {
						System.out.println(
								"FClient: move of Server:" + countMoveServer + ", location of Server:" + moveOfServer);
					}
					countMoveServer++;

					if (game.isFinished()) {
						continue;
					}

					// FClient
					monitorThread.setState(game.getCurMoveIndex(), EnumPlayerState.WAITING_FOR_ME);
					int moveOfClient = clientPlayer.nextMove(game);
					game.processMove(moveOfClient, clientRole);

					if (verbose) {
						System.out.println("FClient: Sending move");
						System.out.println("FClient: move:" + countMoveClient + ", location of FClient:" + moveOfClient
								+ ", sending move");
					}
					countMoveClient++;
					out.writeInt(moveOfClient);
					out.flush();

				}
			} else {
				// client is Player A
				while (!game.isFinished()) {

					// FClient
					monitorThread.setState(game.getCurMoveIndex(), EnumPlayerState.WAITING_FOR_ME);
					int moveOfClient = clientPlayer.nextMove(game);
					game.processMove(moveOfClient, clientRole);

					if (verbose) {
						System.out.println("FClient: Sending move");
						System.out.println("FClient: move:" + countMoveClient + ", location of FClient:" + moveOfClient
								+ ", sending move");
					}
					countMoveClient++;
					out.writeInt(moveOfClient);
					out.flush();

					if (game.isFinished()) {
						continue;
					}

					if (verbose) {
						System.out.println("FClient: Receiving move of FServer");
					}
					monitorThread.setState(game.getCurMoveIndex(), EnumPlayerState.WAITING_FOR_OPPONENT);
					int moveOfServer = in.readInt();
					game.processMove(moveOfServer, serverRole);
					if (verbose) {
						System.out.println(
								"FClient: move of Server:" + countMoveServer + ", location of Server:" + moveOfServer);
					}
					countMoveServer++;

				}
			}

			monitorThread.setState(game.getCurMoveIndex(), EnumPlayerState.TERMINATING);

			if (!silentMode) {
				System.out.println("FClient (" + clientRole + "): The game finished !!");
				System.out.println("FClient (" + clientRole + "): Game score: " + game.getScore());
			}

			// System.out
			// .println("SimpleClient is sending an object (String) to the
			// server .. ");
			// String msg2 = "Hi from the Client";
			// out.writeObject(msg2);
			// out.flush();
			String proof = in.readUTF();

			if (!silentMode) {
				System.out.println("\nPROOF OF PARTICIPATION: " + proof + "\n");
			}
		} else {
			System.err.println("\nWrong Personal Informations!!!\n");
			if (enableMonitorThread) {
				// monitorThread.stop();
				monitorThread.requestStop();
			}
			monitorThread.setState(game.getCurMoveIndex(), EnumPlayerState.TERMINATING);
		}

		in.close();
		out.close();
		socket.close();

	}

	public void printRoles() {
		System.out.println("FClient: " + clientRole);
		System.out.println("FServer: " + serverRole);
	}

	public static FClientArgs processArguments(boolean silentMode, String[] args) {
		FClientArgs clientArgs = new FClientArgs();

		if (!silentMode) {
			System.out.println("FClient: Usage" + "java -cp <jarfile.jar> facilityGame.FClient "
					+ "<player type> <verbose> <server-host> <server-port> <n> <seed> <game type> <player A>");
		}

		// Player type
		if (args.length >= 1) {
			clientArgs.playerType = EnumPlayerType.valueOf(args[0]);
		} else {
			// default parameter values
			clientArgs.playerType = Finals.DEFAULT_PLAYER_TYPE;
		}

		// Verbose mode
		if (args.length >= 2) {
			clientArgs.verbose = Boolean.parseBoolean(args[1]);
		} else {
			// default parameter values
			clientArgs.verbose = Finals.DEFAULT_VERBOSE_MODE;
		}

		// Server address
		if (args.length >= 4) {
			clientArgs.serverHost = args[2];
			clientArgs.serverPort = Integer.parseInt(args[3]);
		} else {
			// default parameter values
			clientArgs.serverHost = Finals.DEFAULT_HOST;
			clientArgs.serverPort = Finals.DEFAULT_PORT;
		}

		// Game parameters
		// int n;
		// long seed;
		// EnumGameType gameType = Finals.DEFAULT_GAME_TYPE;

		if (args.length >= 6) {
			clientArgs.n = Integer.parseInt(args[4]);
			clientArgs.seed = Long.parseLong(args[5]);
		} else {
			// default parameter values
			clientArgs.n = Finals.DEFAULT_N;
			clientArgs.seed = Finals.DEFAULT_SEED;
		}

		if (args.length >= 7) {
			try {
				clientArgs.gameType = EnumGameType.valueOf(args[6]);
			} catch (Exception e) {
				clientArgs.gameType = Finals.DEFAULT_GAME_TYPE;
				System.err.println(e);
				System.err.println("GameType will be by default: " + clientArgs.gameType);
			}
		}

		// Who is Player A
		clientArgs.clientIsPlayerB = Finals.CLIENT_IS_PLAYER_B;
		if (args.length >= 8) {
			try {
				EnumPlayer role = EnumPlayer.valueOf(args[7]);
				if (role == EnumPlayer.PLAYER_A) {
					clientArgs.clientIsPlayerB = false;
				}
			} catch (Exception e) {
				clientArgs.clientIsPlayerB = Finals.CLIENT_IS_PLAYER_B;
				System.err.println(e);
				System.err.println("FClient will be by default: "
						+ (clientArgs.clientIsPlayerB ? EnumPlayer.PLAYER_B : EnumPlayer.PLAYER_A));
			}
		}
		return clientArgs;
	}

	public EnumPlayer getClientRole() {
		return clientRole;
	}

	public static void main(String[] args) {

		FClientArgs clientArgs = FClient.processArguments(false, args);

		FClient client = new FClient(clientArgs);

		try {
			client.initiate();

			// Prepare PlayerB
			// Replace the DummyFPlayer object with your own player

			// Create player object for PlayerA

			FPlayer clientPlayer = CreatePlayerObject.create(client.getClientRole(), clientArgs.playerType);

			if (!client.silentMode) {
				// Print the player type and player info
				System.out.println("FClient: " + client.playerType + ", " + clientPlayer.about());
			}

			// Play the game
			client.play(clientPlayer);

			if (!client.silentMode) {
				// Print the game info
				client.printGameInfo();

				// Print the game status
				client.printGameStatus();

				// print roles
				client.printRoles();
			}
		} catch (IOException e) {
			System.err.println(e);
			System.exit(-1);
		} catch (FacilityGameException e) {
			System.err.println(e);
			System.exit(-1);
		}
	}

}
