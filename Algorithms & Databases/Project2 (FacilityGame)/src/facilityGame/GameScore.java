package facilityGame;

public class GameScore {
	public int playerA;
	public int playerB;

	public GameScore() {
		playerA = 0;
		playerB = 0;
	}
	
	public String toString() {
		String str = "Game score -- Player A: " + playerA + ", Player B: " + playerB;
		return str;
	}
}
