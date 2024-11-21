package facilityGame;

public class FClientArgs implements Cloneable {
	public EnumPlayerType playerType;
	public boolean verbose;
	public String serverHost; // "mycomputer.ee.duth.gr" or "127.0.0.1" or
	// "localhost" ...
	public int serverPort;
	public boolean clientIsPlayerB;
	
	// Game parameters
	public int n;
	public long seed;
	public EnumGameType gameType = Finals.DEFAULT_GAME_TYPE;
	
	// Default Constructor
	public FClientArgs() {
		
	}
	
	// Copy Constructor
	public FClientArgs(FClientArgs ca) {
		this.playerType = ca.playerType;
		this.verbose = ca.verbose;
		this.serverHost = ca.serverHost;
		this.serverPort = ca.serverPort;
		this.clientIsPlayerB = ca.clientIsPlayerB;
		this.n = ca.n;
		this.seed = ca.seed;
		this.gameType = ca.gameType;
	}
}
