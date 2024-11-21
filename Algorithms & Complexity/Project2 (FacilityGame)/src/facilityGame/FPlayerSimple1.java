package facilityGame;

public class FPlayerSimple1 extends FPlayer {
	static String playerName = "SimpleFPlayer1";
	static String version = "1.2";
	// Give your personal information in Greek
	static int afm = 11111; // AFM should be in form 5XXXX
	static String firstname = "Data";
	static String lastname = "Structures";

	// Member variables
	int n; // Number of nodes in the graph
	
	// Constructor
	public FPlayerSimple1(EnumPlayer player) {
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

		return move;
	}
}
