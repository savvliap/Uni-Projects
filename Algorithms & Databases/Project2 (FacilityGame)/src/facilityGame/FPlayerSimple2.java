package facilityGame;

import java.util.Random;

public class FPlayerSimple2 extends FPlayer {
	static String playerName = "SimpleFPlayer2";
	static String version = "1.4";
	// Give your personal information in Greek
	static int afm = 11111; // AFM should be in form 5XXXX
	static String firstname = "Data";
	static String lastname = "Structures";

	// Member variables
	int n; // Number of nodes in the graph
	Random random; // Random number generator
	int startNode; // Node to start from
	boolean leftToRight; // Direction from moving in the graph

	// Constructor
	public FPlayerSimple2(EnumPlayer player) {
		// Call the constructor of the parent class
		super(player, playerName, version, afm, firstname, lastname);
	}

	// Initialize Player
	// This method is called before the game starts
	// Each player can override this method
	public void initialize(FacilityGameAPI game) {
		n = game.getN();

		if (n > 0) {
			// use the value of node 0 as a seed to initialize the random number
			// generator
			long seed = game.getValue(0);
			random = new Random(seed);
		} else {
			random = new Random(0);
		}

		// Choose randomly a starting node
		startNode = random.nextInt(n);

		// Choose randomly the direction
		leftToRight = random.nextBoolean();
	}

	public int nextMove(FacilityGameAPI game) {
		int move = -1;

		// Select which free location (move = location) to occupy
		for (int i = 0; i < n; i++) {
			int node;
			if (leftToRight) {
				node = (startNode + i) % n;
			} else {
				node = (startNode - i) % n;
				// if (startNode < i) then the modulo operator "%"
				// returns a negative value between -(n-1) and -1
				if (node < 0) {
					node += n;
				}
			}

			if (game.getStatus(node) == EnumFacilityStatus.FREE) {
				// Select location/node i
				move = node;
				// The values of the locations are stored in the array
				// game.value
				// int locationValue = game.value[i];

				break; // Abandon the loop
			}

		}

		return move;
	}
}
