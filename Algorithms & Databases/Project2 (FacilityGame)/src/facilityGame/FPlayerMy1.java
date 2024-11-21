package facilityGame;

import java.util.Vector;

public class FPlayerMy1 extends FPlayer {
	static String playerName = "MyFPlayer1";
	static String version = "1.0";
	// Give your personal information in Greek
	static int afm = 111111; // AFM should be in form 5XXXX
	static String firstname = "Data";
	static String lastname = "Structures";

	// Member variables
	int n; // Number of nodes in the graph

	// Constructor
	public FPlayerMy1(EnumPlayer player) {
		// Call the constructor of the parent class
		super(player, playerName, version, afm, firstname, lastname);
	}

	// Initialize Player
	// This method is called before the game starts
	// Each player can override this method
	public void initialize(FacilityGameAPI game) {
		n = game.getN();
	}

	public int nextMove(FacilityGameAPI game) {
		int move = -1;

		// Select which free location (move = location) to occupy

		// Trivial choice: Select the first free location
		for (int i = 0; i < n; i++) {
			if (game.getStatus(i) == EnumFacilityStatus.FREE) {
				// Select location/node i
				move = i;
				// The values of the locations are stored in the array
				// game.value
				// int locationValue = game.value[i];

				break; // Abandon the loop
			}
		}

		// EXAMPLE COMMANDS
		
		// Obtain some information ...

		// Who am I? Player A or Player B
		// ATTENTION: The player type is a variable of type EnumPlayer
		EnumPlayer myid = whoAmI();

		// Check the status of a location/node
		// The status of a specific node/location (node 0)
		// ATTENTION: The facility status is a variable of type
		// EnumFacilityStatus
		// The status of a specific node/location (node 0)
		EnumFacilityStatus status = game.getStatus(0);

		// Example: check the status of node 0
		if (status == me) {
			// do something
		} else if (status == opponent) {
			// do something
		} else if (status == EnumFacilityStatus.BLOCKED) {
			// do something
		} else {
			// status: EnumFacilityStatus.FREE
			// do something
		}

		// The value of a specific node/location (node 0)
		int value = game.getValue(0);

		// The status of all nodes/locations
		int[] valuesOfAllNodes = game.getValue();

		// The status of all nodes/locations
		EnumFacilityStatus[] statusOfAllNodes = game.getStatus();

		// the number of moves so far
		int curMoveIndex = game.getCurMoveIndex();

		// The vector of all moves so far
		Vector<EnumPlayer> moveByPlayer = game.getMoveByPlayer();
		EnumPlayer[] moves = moveByPlayer.toArray(new EnumPlayer[0]);

		// The vector with the location requested at each move
		Vector<Integer> moveLocation = game.getMoveLocation();
		Integer[] locations = moveLocation.toArray(new Integer[0]);
		
		return move;
	}
}
