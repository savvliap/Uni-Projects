package facilityGame;

public class Finals {
	public static final String VERSION = "v0.94";

	public static final EnumPlayerType DEFAULT_PLAYER_TYPE = EnumPlayerType.FPLAYER_SIMPLE_1;
	public static final EnumGameType DEFAULT_GAME_TYPE = EnumGameType.NORMAL;
	public static final boolean DEFAULT_VERBOSE_MODE = false;

	public static final String DEFAULT_HOST = "localhost";
	public static final int DEFAULT_PORT = 4455;
	public static final int DEFAULT_N = 10;
	public static final long DEFAULT_SEED = 1234;
	public static final boolean CLIENT_IS_PLAYER_B = true;
	
	public static final int MAX_LOCATION_VALUE = 50;

	public static final int BONUS_MIN_GROUP_SIZE = 3;
	public static final int BONUS_FACTOR = 3;

	// monitor
	public static final int MSEC_FOR_INFO_MESSAGE = 8000; // Print a progress
															// message every
															// "ROUNDS_FOR_PROGRESS_MESSAGE"
															// rounds

	public static final int MSEC_CHECK_PERIOD = 500; // Check every
														// MSEC_CHECK_PERIOD
														// msec
	public static final int MSEC_WAITING = 10000; // MSec in the same state
	public static final int MSEC_FOR_WARNING_MESSAGE = 10000; // Print a warning not earlier than MSEC_FOR_WARNING_MESSAGE msec since the previous one
	public static final String ERROR_MSG_PREFIX = "ERROR";
}
