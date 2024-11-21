package facilityGame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import javax.print.attribute.standard.Severity;

public class FacilityGame implements FacilityGameAPI {
	private int n;
	private long seed;
	private EnumGameType gameType;

	private int[] value; // The value of each facility location
	private EnumFacilityStatus[] status; // The owner of the facility
	private GameScore score;

	// The complete history of the game, starting with move 0
	private int curMoveIndex; // The number of moves so far
	private Vector<EnumPlayer> moveByPlayer; // Which player made this move?
	private Vector<Integer> moveLocation; // Which location did the player
											// occupy?

	private String aboutPlayerA;
	private String aboutPlayerB;
	
	private boolean flagForSwitching;

	private EnumClientServer whatAmI;

	public FacilityGame(EnumClientServer whatAmI, int size, long seed,
			EnumGameType gameType) throws FacilityGameException {
		this.whatAmI = whatAmI;
		n = size;
		this.seed = seed;
		this.gameType = gameType;

		aboutPlayerA = null;
		aboutPlayerB = null;

		// History data
		curMoveIndex = 0;
		moveByPlayer = new Vector<EnumPlayer>();
		// moveByPlayer.setSize(n);
		moveLocation = new Vector<Integer>();
		// moveLocation.setSize(n);

		// score object
		score = new GameScore();

		// create a random number generator object
		Random random = new Random(seed);

		switch (gameType) {
		case COPY: // Prepare the values table
		{
			if (n < 8) {
				System.err.println("FacilityGame COPY mode requires at least n=8 nodes (currently: n=" + n + ")");
				throw new FacilityGameException(
						"FacilityGame COPY mode requires at least n=8 nodes (currently: n=" + n + ")");
			}

			final int MINVALUE = 10;
			value = new int[n];
			int remainder = n % 2;
			boolean oddSize = (remainder == 0);
			int half = n / 2;

			ArrayList<Integer> alist = new ArrayList<Integer>();
			for (int i = 0; i < half; i++) {
				alist.add(MINVALUE + 1 + i);
			}

			Collections.shuffle(alist, random);

			Integer[] partA = alist.toArray(new Integer[0]);

			for (int i = 0; i < half; i++) {
				value[i] = partA[i];
			}
			if (oddSize) {
				value[half] = MINVALUE;
			}
			for (int i = 0; i < half; i++) {
				value[half + remainder + i] = partA[i];
			}

			// Initiate the status table
			status = new EnumFacilityStatus[n];
			for (int i = 0; i < n; i++) {
				status[i] = EnumFacilityStatus.FREE;
			}

			// Block the middle nodes
			if (oddSize) {
				status[half] = EnumFacilityStatus.BLOCKED;
			}
			break;
		}
		case COMPLEMENT: // Prepare the values table for complementary execution
		{
			if (n < 8) {
				System.err.println("FacilityGame COMPLEMENT mode requires at least n=8 nodes (currently: n=" + n + ")");
				throw new FacilityGameException(
						"FacilityGame COMPLEMENT mode requires at least n=8 nodes (currently: n=" + n + ")");
			}

			final int MINVALUE = 10;
			value = new int[n];
			int remainder = n % 2;
			boolean oddSize = (remainder == 0);
			int half = n / 2;

			ArrayList<Integer> alist = new ArrayList<Integer>();
			for (int i = 0; i < half; i++) {
				alist.add(MINVALUE + 1 + 2*i); // values of first half should be odd numbers (MINVALUE=10)
			}

			Collections.shuffle(alist, random);

			Integer[] partA = alist.toArray(new Integer[0]);

			for (int i = 0; i < half; i++) {
				value[i] = partA[i];
			}
			if (oddSize) {
				value[half] = MINVALUE;
			}
			for (int i = 0; i < half; i++) {
				value[half + remainder + i] = 9999 - partA[i];  // values of second half should be complementary to first half (total=9999)
			}

			// Initiate the status table
			status = new EnumFacilityStatus[n];
			for (int i = 0; i < n; i++) {
				status[i] = EnumFacilityStatus.FREE;
			}

			// Block the middle nodes
			if (oddSize) {
				status[half] = EnumFacilityStatus.BLOCKED;
			}
			break;
		}
		case NOTRIPLES: // Prepare the values table
			// Fill it with random values from 1, 2, ...,
			// Finals.MAX_LOCATION_VALUE
			value = new int[n];
			for (int i = 0; i < n; i++) {
				value[i] = 1 + random.nextInt(Finals.MAX_LOCATION_VALUE - 1);
			}

			// Initiate the status table
			status = new EnumFacilityStatus[n];
			for (int i = 0; i < n; i++) {
				status[i] = EnumFacilityStatus.FREE;
			}
		case NOQUADRUPLES: // Prepare the values table
			// Fill it with random values from 1, 2, ...,
			// Finals.MAX_LOCATION_VALUE
			value = new int[n];
			for (int i = 0; i < n; i++) {
				value[i] = 1 + random.nextInt(Finals.MAX_LOCATION_VALUE - 1);
			}

			// Initiate the status table
			status = new EnumFacilityStatus[n];
			for (int i = 0; i < n; i++) {
				status[i] = EnumFacilityStatus.FREE;
			}
		case SWITCHING: // Prepare the values table
			// Fill it with random values from 1, 2, ...,
			// Finals.MAX_LOCATION_VALUE
			value = new int[n];
			for (int i = 0; i < n; i++) {
				value[i] = 1 + random.nextInt(Finals.MAX_LOCATION_VALUE - 1);
			}

			// Initiate the status table
			status = new EnumFacilityStatus[n];
			for (int i = 0; i < n; i++) {
				status[i] = EnumFacilityStatus.FREE;
			}
			flagForSwitching = false;
		case NORMAL: // Prepare the values table
			// Fill it with random values from 1, 2, ...,
			// Finals.MAX_LOCATION_VALUE
			value = new int[n];
			for (int i = 0; i < n; i++) {
				value[i] = 1 + random.nextInt(Finals.MAX_LOCATION_VALUE - 1);
			}

			// Initiate the status table
			status = new EnumFacilityStatus[n];
			for (int i = 0; i < n; i++) {
				status[i] = EnumFacilityStatus.FREE;
			}
			break;
		default:
			System.err.println("Invalid game type: " + gameType);
			throw new FacilityGameException("Invalid game type: " + gameType);
			// break;
		}

	}

	// Return the number of nodes in the game
	public int getN() {
		return n;
	}

	public String getAboutPlayerA() {
		return aboutPlayerA;
	}

	public void setAboutPlayerA(String aboutPlayerA) {
		this.aboutPlayerA = aboutPlayerA;
	}

	public String getAboutPlayerB() {
		return aboutPlayerB;
	}

	public void setAboutPlayerB(String aboutPlayerB) {
		this.aboutPlayerB = aboutPlayerB;
	}

	// Return the seed for the pseudorandom number generator
	public long getSeed() {
		return seed;
	}

	// Get the value of a specific node
	public int getValue(int i) {
		return value[i];
	}

	// Get an array with the values of all nodes
	public int[] getValue() {
		return value;
	}
	
	// Get a copy of the array with the values of all nodes
	public int[] getValueCopy() {
		int[] value_copy = new int[value.length];
		System.arraycopy( value, 0, value_copy, 0, value.length );
		return value_copy;
	}

	// Get the status of a specific node
	public EnumFacilityStatus getStatus(int node) {
		return status[node];
	}

	// Get array with the status of all nodes
	public EnumFacilityStatus[] getStatus() {
		return status;
	}
	
	// Get a copy of the array with the status of all nodes
	public EnumFacilityStatus[] getStatusCopy() {
		EnumFacilityStatus[] status_copy = new EnumFacilityStatus[status.length];
		System.arraycopy( status, 0, status_copy, 0, status.length );
		return status_copy;
	}

	// Get current score
	public GameScore getScore() {
		return score;
	}

	// Are there any moves left in the game?
	public boolean isFinished() {
		boolean finished = true;

		for (EnumFacilityStatus s : status) {
			if (s == EnumFacilityStatus.FREE) {
				finished = false;
				break;
			}
		}

		return finished;
	}

	// The number of moves so far
	public int getCurMoveIndex() {
		return curMoveIndex;
	}

	// A vector with the information of which player made each move.
	public Vector<EnumPlayer> getMoveByPlayer() {
		return moveByPlayer;
	}

	// A vector with the information of which location were occupied in each
	// move.
	public Vector<Integer> getMoveLocation() {
		return moveLocation;
	}

	public void processMove(int location, EnumPlayer player) {

		// int size = moveByPlayer.size();
		// if (curMoveIndex > size) {
		// moveByPlayer.setSize(size + n);
		// moveLocation.setSize(size + n);
		// }
		// moveByPlayer.set(curMoveIndex, player);
		// moveLocation.set(curMoveIndex, location);
		moveByPlayer.add(player);
		moveLocation.add(location);
		curMoveIndex++;

		if (location >= 0) {
			occupyLocation(location, player);
		} else {
			System.err.println("Invalid Location: " + location);
		}

		if (gameType == EnumGameType.COPY) {
			checkMostRecentPairOfMoves();
		}
		
		if (gameType == EnumGameType.COMPLEMENT) {
			checkMostRecentPairOfMovesComplement();
		}
		
		if (gameType == EnumGameType.NOTRIPLES) {
			checkMostRecentServerMoveForTriple();
		}
		
		if (gameType == EnumGameType.NOQUADRUPLES) {
			checkMostRecentServerMoveForQuadruple();
		}
		if (gameType == EnumGameType.SWITCHING) {
			checkMostRecentPlayersPairOfMoves();
		}
	}

	public void occupyLocation(int location, EnumPlayer player) {
		if (status[location] != EnumFacilityStatus.FREE) {
			System.err.println("Location " + location
					+ " is not free (status = " + status[location] + ")");
			// ignore the request
		} else {
			// occupy the location
			switch (player) {
			case PLAYER_A:
				status[location] = EnumFacilityStatus.PLAYER_A;
				break;
			case PLAYER_B:
				status[location] = EnumFacilityStatus.PLAYER_B;
				break;
			}

			// block the neighboring locations
			if (location > 0) {
				status[location - 1] = EnumFacilityStatus.BLOCKED;
			}
			if (location < n - 1) {
				status[location + 1] = EnumFacilityStatus.BLOCKED;
			}

			// update score
			switch (player) {
			case PLAYER_A:
				// score.playerA += value[location];
				score.playerA = calculateScore(player);
				break;
			case PLAYER_B:
				// score.playerB += value[location];
				score.playerB = calculateScore(player);
				break;
			default:
				// This case should never be reached
				System.err.println("Invalid player: " + player);
			}
		}
	}

	private int calculateScore(EnumPlayer player) {
		int score = 0;

		EnumFacilityStatus nodeStatus = null;
		if (player == EnumPlayer.PLAYER_A) {
			nodeStatus = EnumFacilityStatus.PLAYER_A;
		} else if (player == EnumPlayer.PLAYER_B) {
			nodeStatus = EnumFacilityStatus.PLAYER_B;
		}

		int tempBonus = 0;
		int numOfConsecutiveFacilities = 0;

		int node = 0;
		while (node < n) {
			if (status[node] == nodeStatus) {
				int nodeValue = value[node];
				score += nodeValue;

				// bonus
				tempBonus += (Finals.BONUS_FACTOR - 1) * nodeValue;
				numOfConsecutiveFacilities++;
				node += 1;
			} else if (status[node] == EnumFacilityStatus.BLOCKED) {
				node += 1;
			} else {
				if (numOfConsecutiveFacilities >= Finals.BONUS_MIN_GROUP_SIZE) {
					score += tempBonus;
				}
				tempBonus = 0;
				numOfConsecutiveFacilities = 0;
				node += 1;
			}
		}
		if (numOfConsecutiveFacilities >= Finals.BONUS_MIN_GROUP_SIZE) {
			score += tempBonus;
		}

		return score;
	}

	public void printGameInfo() {
		System.out.println("Facility Game");
		System.out.println("n: " + n);
		System.out.println("seed: " + seed);
		System.out.println("GameType: " + gameType);
		System.out.println("PlayerA: " + aboutPlayerA);
		System.out.println("PlayerB: = " + aboutPlayerB);
	}

	public void printGameGraph() {
		for (int i = 0; i < n; i++) {
			System.out.println("Location " + i + ", value:" + value[i]);
		}
	}

	public void printStatus() {
		System.out.println("FacilityGame status");
		System.out.println("Finished: " + isFinished());
		System.out.println("Score: " + getScore());
	}

	public void printStatusLong() {
		System.out.println("FacilityGame status");
		System.out.println("Finished: " + isFinished());
		System.out.println("Score: " + getScore());
		for (int i = 0; i < n; i++) {
			System.out.println("Location " + i + ", value:" + value[i]
					+ ", status:" + status[i]);
		}
	}

	public void printAllMoves() {
		for (int i = 0; i < curMoveIndex; i++) {
			int location = moveLocation.get(i);
			String strValue;
			if (location < 0) {
				// invailid location
				strValue = "NaN";
			} else {
				strValue = String.valueOf(value[location]);
			}
			 
			System.out.println("move : " + i + ", player: "
					+ moveByPlayer.get(i) + ", location: "
					+ location + ", value: " + strValue);

		}
	}

	public static void checkServerParameters(boolean isServer,
			EnumPlayerType playerType, int n, EnumGameType serverGameType, EnumGameType clientGameType,
			EnumPlayer clientRole) throws FacilityGameException {
		if (isServer && serverGameType != clientGameType) {
			// System.err
			// .println("ERROR: FacilityGame Server is not using FPLAYER_COPY and therefore not expecting game type COPY");
			throw new FacilityGameException(
					Finals.ERROR_MSG_PREFIX + ": Server and client are player different game types, server: " + serverGameType + ", client: " + clientGameType);
		}
	}
	
	public static void checkParameters(boolean isServer,
			EnumPlayerType playerType, int n, EnumGameType gameType,
			EnumPlayer clientRole) throws FacilityGameException {
		if (isServer && gameType == EnumGameType.COPY
				&& playerType != EnumPlayerType.FPLAYER_COPY) {
			// System.err
			// .println("ERROR: FacilityGame Server is not using FPLAYER_COPY and therefore not expecting game type COPY");
			throw new FacilityGameException(
					"ERROR: FacilityGame Server is not using FPLAYER_COPY and therefore not expecting game type COPY");
		}
		if (isServer && gameType != EnumGameType.COPY
				&& playerType == EnumPlayerType.FPLAYER_COPY) {
			// System.err
			// .println("ERROR: FacilityGame Server is using FPLAYER_COPY and therefore expecting game type COPY");
			throw new FacilityGameException(
					"ERROR: FacilityGame Server is using FPLAYER_COPY and therefore expecting game type COPY");
		}
		if (gameType == EnumGameType.COPY) {
			if (n < 8) {
				// System.err
				// .println("ERROR: FacilityGame COPY mode requires at least n=8 nodes (currently: n="
				// + n + ")");
				throw new FacilityGameException(
						"ERROR: FacilityGame COPY mode requires at least n=8 nodes (currently: n="
								+ n + ")");
			}
			if (clientRole != EnumPlayer.PLAYER_B) {
				// System.err
				// .println("ERROR: FacilityGame COPY mode requires the client to be player B (currently: client is "
				// + clientRole);
				throw new FacilityGameException(
						"ERROR: FacilityGame COPY mode requires the client to be player B (currently: client is "
								+ clientRole);
			}
		}
		
		// Checks for COMPLEMENT mode
		if (isServer && gameType == EnumGameType.COMPLEMENT
				&& playerType != EnumPlayerType.FPLAYER_COMPLEMENT) {
			// System.err
			// .println("ERROR: FacilityGame Server is not using FPLAYER_COPY and therefore not expecting game type COPY");
			throw new FacilityGameException(
					"ERROR: FacilityGame Server is not using FPLAYER_COMPLEMENT and therefore not expecting game type COMPLEMENT");
		}
		if (isServer && gameType != EnumGameType.COMPLEMENT
				&& playerType == EnumPlayerType.FPLAYER_COMPLEMENT) {
			// System.err
			// .println("ERROR: FacilityGame Server is using FPLAYER_COPY and therefore expecting game type COPY");
			throw new FacilityGameException(
					"ERROR: FacilityGame Server is using FPLAYER_COMPLEMENT and therefore expecting game type COMPLEMENT");
		}
		if (gameType == EnumGameType.COMPLEMENT) {
			if (n < 8) {
				// System.err
				// .println("ERROR: FacilityGame COPY mode requires at least n=8 nodes (currently: n="
				// + n + ")");
				throw new FacilityGameException(
						"ERROR: FacilityGame COMPLEMENT mode requires at least n=8 nodes (currently: n="
								+ n + ")");
			}
			if (clientRole != EnumPlayer.PLAYER_B) {
				// System.err
				// .println("ERROR: FacilityGame COPY mode requires the client to be player B (currently: client is "
				// + clientRole);
				throw new FacilityGameException(
						"ERROR: FacilityGame COMPLEMENT mode requires the client to be player B (currently: client is "
								+ clientRole);
			}
		}
		
		// Checks for NOTRIPLES mode
				if (isServer && gameType == EnumGameType.NOTRIPLES
						&& playerType != EnumPlayerType.FPLAYER_NOTRIPLES) {
					// System.err
					// .println("ERROR: FacilityGame Server is not using FPLAYER_COPY and therefore not expecting game type COPY");
					throw new FacilityGameException(
							"ERROR: FacilityGame Server is not using FPLAYER_NOTRIPLES and therefore not expecting game type NOTRIPLES");
				}
				if (isServer && gameType != EnumGameType.NOTRIPLES
						&& playerType == EnumPlayerType.FPLAYER_NOTRIPLES) {
					// System.err
					// .println("ERROR: FacilityGame Server is using FPLAYER_COPY and therefore expecting game type COPY");
					throw new FacilityGameException(
							"ERROR: FacilityGame Server is using FPLAYER_NOTRIPLES and therefore expecting game type NOTRIPLES");
				}
				if (gameType == EnumGameType.NOTRIPLES) {
					if (n < 8) {
						// System.err
						// .println("ERROR: FacilityGame COPY mode requires at least n=8 nodes (currently: n="
						// + n + ")");
						throw new FacilityGameException(
								"ERROR: FacilityGame NOTRIPLES mode requires at least n=8 nodes (currently: n="
										+ n + ")");
					}
					if (clientRole != EnumPlayer.PLAYER_B) {
						// System.err
						// .println("ERROR: FacilityGame COPY mode requires the client to be player B (currently: client is "
						// + clientRole);
						throw new FacilityGameException(
								"ERROR: FacilityGame NOTRIPLES mode requires the client to be player B (currently: client is "
										+ clientRole + ")");
					}
				}
			// Checks for NOQUADRUPLES mode
				if (isServer && gameType == EnumGameType.NOQUADRUPLES
						&& playerType != EnumPlayerType.FPLAYER_NOQUADRUPLES) {
					// System.err
					// .println("ERROR: FacilityGame Server is not using FPLAYER_COPY and therefore not expecting game type COPY");
					throw new FacilityGameException(
							"ERROR: FacilityGame Server is not using FPLAYER_NOQUADRUPLES and therefore not expecting game type NOQUADRUPLES");
				}
				if (isServer && gameType != EnumGameType.NOQUADRUPLES
						&& playerType == EnumPlayerType.FPLAYER_NOQUADRUPLES) {
					// System.err
					// .println("ERROR: FacilityGame Server is using FPLAYER_COPY and therefore expecting game type COPY");
					throw new FacilityGameException(
							"ERROR: FacilityGame Server is using FPLAYER_NOQUADRUPLES and therefore expecting game type NOQUADRUPLES");
				}
				if (gameType == EnumGameType.NOQUADRUPLES) {
					if (n < 8) {
						// System.err
						// .println("ERROR: FacilityGame COPY mode requires at least n=8 nodes (currently: n="
						// + n + ")");
						throw new FacilityGameException(
								"ERROR: FacilityGame NOQUADRUPLES mode requires at least n=8 nodes (currently: n="
										+ n + ")");
					}
					if (clientRole != EnumPlayer.PLAYER_B) {
						// System.err
						// .println("ERROR: FacilityGame COPY mode requires the client to be player B (currently: client is "
						// + clientRole);
						throw new FacilityGameException(
								"ERROR: FacilityGame NOQUADRUPLES mode requires the client to be player B (currently: client is "
										+ clientRole + ")");
					}
				}
				// Checks for SWITCHING mode
				if (isServer && gameType == EnumGameType.SWITCHING && playerType != EnumPlayerType.FPLAYER_SWITCHING) {
					// System.err
					// .println("ERROR: FacilityGame Server is not using FPLAYER_COPY and therefore not expecting game type COPY");
//					throw new FacilityGameException(
//							"ERROR: FacilityGame Server is not using FPLAYER_SWITCHING and therefore not expecting game type SWITCHING");
					System.err.println("Client requested switching game");
				}
				if (isServer && gameType != EnumGameType.SWITCHING
						&& playerType == EnumPlayerType.FPLAYER_SWITCHING) {
					// System.err
					// .println("ERROR: FacilityGame Server is using FPLAYER_COPY and therefore expecting game type COPY");
//					throw new FacilityGameException(
//							"ERROR: FacilityGame Server is using FPLAYER_SWITCHING and therefore expecting game type SWITCHING");
					System.err.println("Server expected switching player");
				}
				if (gameType == EnumGameType.SWITCHING) {
					if (n < 8) {
						// System.err
						// .println("ERROR: FacilityGame COPY mode requires at least n=8 nodes (currently: n="
						// + n + ")");
						throw new FacilityGameException(
								"ERROR: FacilityGame SWITCHING mode requires at least n=8 nodes (currently: n="
										+ n + ")");
					}
					if (clientRole != EnumPlayer.PLAYER_B) {
						// System.err
						// .println("ERROR: FacilityGame COPY mode requires the client to be player B (currently: client is "
						// + clientRole);
						throw new FacilityGameException(
								"ERROR: FacilityGame SWITCHING mode requires the client to be player B (currently: client is "
										+ clientRole + ")");
					}
				}
	}

	public boolean checkPairOfMoves(int move1, int move2) {
		int locationA = moveLocation.get(move1);
		int locationB = moveLocation.get(move2);
		boolean notTheSame = false;
		if ((locationA < 0 && locationB > 0)
				|| (locationA > 0 && locationB < 0)) {
			notTheSame = true;
		} else if (locationA < 0 && locationB < 0) {
			// notTheSame = false;
		} else {
			int valueA = getValue(locationA);
			int valueB = getValue(locationB);

			if (valueA != valueB) {
				notTheSame = true;
			}
		}
		// if (notTheSame) {
		// // print error message only at the client side
		// if (whatAmI == EnumClientServer.CLIENT) {
		// System.err.println("WARNING: In COPY moves " + move1 + " and "
		// + move2 + " have not the same value");
		// }
		// }

		return (!notTheSame);
	}

	public boolean checkMostRecentPairOfMoves() {
		boolean checkMostRecentPairOfMoves = true;
		int curIndex = getCurMoveIndex();
		int lastMove = curIndex - 1;
		boolean first = ((curIndex % 2) == 1); // True for moves 0, 2, 4, 6,
		if (first) {
			// there is no pair of moves yet
			checkMostRecentPairOfMoves = true;
		} else {
			int indexToCheck = lastMove - (lastMove % 2);
			int move1 = indexToCheck;
			int move2 = indexToCheck + 1;
			if (move2 > lastMove) {
				// the last pair is not complete yet, take the previous pair
				move1 -= 2;
				move2 -= 2;
			}
			if (move1 < 0) {
				checkMostRecentPairOfMoves = true;
			} else {
				checkMostRecentPairOfMoves = checkPairOfMoves(move1, move2);
			}

			if (!checkMostRecentPairOfMoves) {
				// print error message only at the client side
				if (whatAmI == EnumClientServer.CLIENT) {
					System.err.println("WARNING: In COPY moves " + move1
							+ " and " + move2 + " have not the same value");
				}
			}
		}

		return checkMostRecentPairOfMoves;
	}
	
	public boolean checkMostRecentPlayersPairOfMoves() {
		boolean checkMostRecentPlayersPairOfMoves = true;
		int curIndex = getCurMoveIndex();
		int lastMove = curIndex - 1;
		int moveBeforeLastMove = lastMove - 2;
		boolean first = ((curIndex % 2) == 1); // True for moves 0, 2, 4, 6,
		if (curIndex>2 && curIndex%2 == 0) {
			int locationA = moveLocation.get(lastMove);
			int locationB = moveLocation.get(moveBeforeLastMove);
			boolean validMoveExists = false;
			for (int i=0; i<getN(); i++) {
				if (status[i] == EnumFacilityStatus.FREE && Math.abs(i-locationB) > 3) {
					validMoveExists = true;
					break;
				}
			}
			if (Math.abs(locationA-locationB)<=3 && validMoveExists) {
				checkMostRecentPlayersPairOfMoves = false;
				flagForSwitching = true;
				if (whatAmI == EnumClientServer.CLIENT) {
					System.err.println("WARNING: In SWITCHING mode, moves " + locationA  + " and " + locationB + " are too close!");
				}
			}
		}
		
		return checkMostRecentPlayersPairOfMoves;
	}

	public boolean checkPairOfMovesComplement(int move1, int move2) {
		int locationA = moveLocation.get(move1);
		int locationB = moveLocation.get(move2);
		boolean notcomplementary = false;
		if ((locationA < 0 && locationB > 0)
				|| (locationA > 0 && locationB < 0)) {
			notcomplementary = true;
		} else if (locationA < 0 && locationB < 0) {
			// notTheSame = false;
		} else {
			int valueA = getValue(locationA);
			int valueB = getValue(locationB);

			if ( (valueA + valueB) != 9999) {
				notcomplementary = true;
			}
		}
		// if (notTheSame) {
		// // print error message only at the client side
		// if (whatAmI == EnumClientServer.CLIENT) {
		// System.err.println("WARNING: In COPY moves " + move1 + " and "
		// + move2 + " have not the same value");
		// }
		// }

		return (!notcomplementary);
	}

	public boolean checkMostRecentPairOfMovesComplement() {
		boolean checkMostRecentPairOfMoves = true;
		int curIndex = getCurMoveIndex();
		int lastMove = curIndex - 1;
		boolean first = ((curIndex % 2) == 1); // True for moves 0, 2, 4, 6,
		if (first) {
			// there is no pair of moves yet
			checkMostRecentPairOfMoves = true;
		} else {
			int indexToCheck = lastMove - (lastMove % 2);
			int move1 = indexToCheck;
			int move2 = indexToCheck + 1;
			if (move2 > lastMove) {
				// the last pair is not complete yet, take the previous pair
				move1 -= 2;
				move2 -= 2;
			}
			if (move1 < 0) {
				checkMostRecentPairOfMoves = true;
			} else {
				checkMostRecentPairOfMoves = checkPairOfMovesComplement(move1, move2);
			}

			if (!checkMostRecentPairOfMoves) {
				// print error message only at the client side
				if (whatAmI == EnumClientServer.CLIENT) {
					System.err.println("WARNING: In COMPLEMENT mode: moves " + move1
							+ " and " + move2 + " are not complementary to each other");
				}
			}
		}

		return checkMostRecentPairOfMoves;
	}
	
	public boolean checkMostRecentServerMoveForTriple() {
		boolean checkMostRecentServerMoveForTriple = false;
		int curIndex = getCurMoveIndex();
		int lastMove =curIndex - 1;
		boolean first = ((curIndex % 2) == 1); // True for moves 0, 2, 4, 6,
		if (first) {
			int serverMove = moveLocation.lastElement();
			int leftNodes = 0;
			for(int i=serverMove-1;i>=0;i--){
				if (getStatus(i) == EnumFacilityStatus.PLAYER_A)
					leftNodes++;
				else if (getStatus(i) == EnumFacilityStatus.PLAYER_B || getStatus(i) == EnumFacilityStatus.FREE)
					break;
			}
			int rightNodes = 0;
			for(int i=serverMove+1; i < getN(); i++){
				if (getStatus(i) == EnumFacilityStatus.PLAYER_A)
					leftNodes++;
				else if (getStatus(i) == EnumFacilityStatus.PLAYER_B || getStatus(i) == EnumFacilityStatus.FREE)
					break;
			}
			checkMostRecentServerMoveForTriple = ( (leftNodes + rightNodes + 1) >=3 );
			if (whatAmI == EnumClientServer.CLIENT && checkMostRecentServerMoveForTriple) {
				System.err.println("WARNING: In NOTRIPLES move "
						+ lastMove + " of server (node  " + serverMove
						+ ") completed a triple of nodes");
			}
		}
		return checkMostRecentServerMoveForTriple;
	}
	
	public boolean checkMostRecentServerMoveForQuadruple() {
		boolean checkMostRecentServerMoveForQuadruple = false;
		int curIndex = getCurMoveIndex();
		int lastMove =curIndex - 1;
		boolean first = ((curIndex % 2) == 1); // True for moves 0, 2, 4, 6,
		if (first) {
			int serverMove = moveLocation.lastElement();
			int leftNodes = 0;
			for(int i=serverMove-1;i>=0;i--){
				if (getStatus(i) == EnumFacilityStatus.PLAYER_A)
					leftNodes++;
				else if (getStatus(i) == EnumFacilityStatus.PLAYER_B || getStatus(i) == EnumFacilityStatus.FREE)
					break;
			}
			int rightNodes = 0;
			for(int i=serverMove+1; i < getN(); i++){
				if (getStatus(i) == EnumFacilityStatus.PLAYER_A)
					leftNodes++;
				else if (getStatus(i) == EnumFacilityStatus.PLAYER_B || getStatus(i) == EnumFacilityStatus.FREE)
					break;
			}
			checkMostRecentServerMoveForQuadruple = ( (leftNodes + rightNodes + 1) >=4 );
			if (whatAmI == EnumClientServer.CLIENT && checkMostRecentServerMoveForQuadruple) {
				System.err.println("WARNING: In NOQUADRUPLES move "
						+ lastMove + " of server (node  " + serverMove
						+ ") completed a quadruple of nodes");
			}
		}
		return checkMostRecentServerMoveForQuadruple;
	}
	
	
	public boolean checkMoves(int fromMove) {
		boolean check = true;

		if (gameType == EnumGameType.COPY)
		// Check that player B is following player A
		{
			int moveToCheck = fromMove;
			Vector<Integer> moveLocation = getMoveLocation();
			int size = moveLocation.size();
			while (moveToCheck + 2 <= size) {
				int locationA = moveLocation.get(moveToCheck);
				int locationB = moveLocation.get(moveToCheck + 1);
				boolean notTheSame = false;
				if ((locationA < 0 && locationB > 0)
						|| (locationA > 0 && locationB < 0)) {
					notTheSame = true;
				} else if (locationA < 0 && locationB < 0) {
					// notTheSame = false;
				} else {
					int valueA = getValue(locationA);
					int valueB = getValue(locationB);

					if (valueA != valueB) {
						notTheSame = true;
					}
				}
				if (notTheSame) {
					check = false;
					// print error message only at the client side
					if (whatAmI == EnumClientServer.CLIENT) {
						System.err.println("WARNING: In COPY moves "
								+ moveToCheck + " and " + (moveToCheck + 1)
								+ " have not the same value");
					}
				}
				moveToCheck += 2;
			}
		}

		return check;
	}

	public boolean checkMovesComplement(int fromMove) {
		boolean check = true;

		if (gameType == EnumGameType.COMPLEMENT)
		// Check that player B is following player A
		{
			int moveToCheck = fromMove;
			Vector<Integer> moveLocation = getMoveLocation();
			int size = moveLocation.size();
			while (moveToCheck + 2 <= size) {
				int locationA = moveLocation.get(moveToCheck);
				int locationB = moveLocation.get(moveToCheck + 1);
				boolean notComplementary = false;
				if ((locationA < 0 && locationB > 0)
						|| (locationA > 0 && locationB < 0)) {
					notComplementary = true;
				} else if (locationA < 0 && locationB < 0) {
					// notTheSame = false;
				} else {
					int valueA = getValue(locationA);
					int valueB = getValue(locationB);

					if ( (valueA + valueB) != 9999) {
						notComplementary = true;
					}
				}
				if (notComplementary) {
					check = false;
					// print error message only at the client side
					if (whatAmI == EnumClientServer.CLIENT) {
						System.err.println("WARNING: In COMPLEMENT mode: moves "
								+ moveToCheck + " and " + (moveToCheck + 1)
								+ " are not complementary to each other");
					}
				}
				moveToCheck += 2;
			}
		}

		return check;
	}

	public boolean checkForServerTriples() {
		int consecutiveLocations = 0;
		for (int i=0; i < getN(); i++) {
			if (status[i] == EnumFacilityStatus.FREE || status[i] == EnumFacilityStatus.PLAYER_B)
				consecutiveLocations = 0;
			else if (status[i] == EnumFacilityStatus.PLAYER_A)
				consecutiveLocations++;
			if (consecutiveLocations >= 3)
				break;
		}
		return !(consecutiveLocations >= 3);
	}
	
	public boolean checkForServerQuadruples() {
		int consecutiveLocations = 0;
		for (int i=0; i < getN(); i++) {
			if (status[i] == EnumFacilityStatus.FREE || status[i] == EnumFacilityStatus.PLAYER_B)
				consecutiveLocations = 0;
			else if (status[i] == EnumFacilityStatus.PLAYER_A)
				consecutiveLocations++;
			if (consecutiveLocations >= 4)
				break;
		}
		return !(consecutiveLocations >= 4) && !checkForServerTriples();
	}
	
	// check on the server if the client player succeeded
	// 0: faileure
	// 1: OK
	public int checkSuccess(boolean serverIsPlayerA) {
		boolean checkSuccess = false;

		if (gameType == EnumGameType.NORMAL) {
			int serverScore;
			int clientScore;
			if (serverIsPlayerA) {
				serverScore = getScore().playerA;
				clientScore = getScore().playerB;
			} else {
				serverScore = getScore().playerB;
				clientScore = getScore().playerA;
			}
			checkSuccess = (clientScore >= serverScore);
		} else if (gameType == EnumGameType.COPY) {
			if (serverIsPlayerA) {
				checkSuccess = checkMoves(0);
			} else {
				// In COPY games, server must be player A
				checkSuccess = false;
			}
		} else if (gameType == EnumGameType.COMPLEMENT) {
			if (serverIsPlayerA) {
				checkSuccess = checkMovesComplement(0);
			} else {
				// In COMPLEMENT games, server must be player A
				checkSuccess = false;
			}
		} else if (gameType == EnumGameType.NOTRIPLES) {
			if (serverIsPlayerA) {
				checkSuccess = checkForServerTriples();
			} else {
				// In NOTRIPLES games, server must be player A
				checkSuccess = false;
			}
		} else if (gameType == EnumGameType.NOQUADRUPLES) {
			if (serverIsPlayerA) {
				checkSuccess = checkForServerQuadruples();
			} else {
				// In NOQUADRUPLES games, server must be player A
				checkSuccess = false;
			}
		} else if (gameType == EnumGameType.SWITCHING) {
			if (serverIsPlayerA) {
				checkSuccess = !flagForSwitching;
			} else {
				// In SWITCHING games, server must be player A
				checkSuccess = false;
			}
			if (checkSuccess) {
				int serverScore = getScore().playerA;
				int clientScore = getScore().playerB;
				checkSuccess = (clientScore >= serverScore);
			}
		}


		return (checkSuccess ? 1 : 0);
	}
}
