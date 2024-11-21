package facilityGame;

abstract public class FPlayer {
	private EnumPlayer player;
	private String strAbout;
	public final String PlayerName;
	public final String Version;
	public final int AFM;
	public final String FirstName;
	public final String LastName;
	
	public final EnumFacilityStatus me;
	public final EnumFacilityStatus opponent;

	// Constructor
	public FPlayer(EnumPlayer parPlayer, String playerName, String version, int afm, String firstname,
			String lastname) {
		player = parPlayer;
		PlayerName = playerName; 
		Version = version;
		AFM = afm;
		FirstName = firstname;
		LastName = lastname;

		strAbout = playerName + " v" + Version + ", by " + firstname + " " + lastname + " (AM:" + AFM + ")";
		
		if (player == EnumPlayer.PLAYER_A) {
			me = EnumFacilityStatus.PLAYER_A;
			opponent = EnumFacilityStatus.PLAYER_B;
		} else {
			me = EnumFacilityStatus.PLAYER_B;
			opponent = EnumFacilityStatus.PLAYER_A;			
		}
	}

	// Who am I: PLAYERA or PLAYERB?
	public EnumPlayer whoAmI() {
		return player;
	}
	
	// Who is my opponent? PLAYERA or PLAYERB?
	public EnumPlayer whoIsMyOpponent() {
		if (player == EnumPlayer.PLAYER_A) {
			return EnumPlayer.PLAYER_B;
		} else {
			return EnumPlayer.PLAYER_A;
		}
	}

	// Information about this player
	public String about() {
		return strAbout;
	}

	// Initialize Player
	// This method is called before the game starts
	// Each player can override this method
	public void initialize(FacilityGameAPI game) {

	}

	// My next move
	abstract public int nextMove(FacilityGameAPI game);
}
