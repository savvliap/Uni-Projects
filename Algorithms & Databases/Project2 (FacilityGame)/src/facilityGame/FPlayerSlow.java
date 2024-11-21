package facilityGame;

import java.util.Random;

public class FPlayerSlow extends FPlayer {
	static String playerName = "SlowPlayer";
	static String version = "1.0";
	// Give your personal information in Greek
	static int afm = 11111; // AFM should be in form 5XXXX
	static String firstname = "Data";
	static String lastname = "Structures";

	Random random; // Random number generator

	public FPlayerSlow(EnumPlayer player) {
		super(player, playerName, version, afm, firstname, lastname);
		random = new Random();
	}

	public int nextMove(FacilityGameAPI game) {
		int move = -1;

		// Make the player slow and check what will happen ...
		try {
			int sleepTime = 21000 + random.nextInt(2000);
			Thread.sleep(sleepTime);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println(e);
		}

		// Select which free location (move = location) to occupy

		// Trivial choice: Select the last free location
		int n = game.getN();
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