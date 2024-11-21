package facilityGame;

public class FPlayerMy2 extends FPlayer {
	static String playerName = "MyFPlayer2";
	static String version = "1.0";
	// Give your personal information in Greek
	static int afm = 11111; // AFM should be in form 5XXXX
	static String firstname = "Data";
	static String lastname = "Structures";

	// Member variables
	int n; // Number of nodes in the graph

	// Constructor
	public FPlayerMy2(EnumPlayer player) {
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

		// ...
		// ...

		return move;
	}
}
