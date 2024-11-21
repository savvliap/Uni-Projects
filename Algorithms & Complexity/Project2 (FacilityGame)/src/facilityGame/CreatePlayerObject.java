package facilityGame;

public class CreatePlayerObject {
	public static FPlayer create(EnumPlayer playerAorB,
			EnumPlayerType playerType) throws FacilityGameException {

		FPlayer player = null;

		switch (playerType) {
		case FPLAYER_SIMPLE_1:
			player = new FPlayerSimple1(playerAorB);
			break;
		case FPLAYER_SIMPLE_2:
			player = new FPlayerSimple2(playerAorB);
			break;
		case FPLAYER_SLOW:
			player = new FPlayerSlow(playerAorB);
			break;
//		case FPLAYER_COPY:
//			player = new FPlayerCopy(playerAorB);
//			break;
		case FPLAYER_MY_1:
			player = new FPlayerMy1(playerAorB);
			break;
		case SAVVAS_LIAPIS:
			player = new SavvasLiapis(playerAorB);
			break;
		case FPLAYER_MY_2:
			player = new FPlayerMy2(playerAorB);
			break;
//			System.err.println("Player not implemented yet: " + playerType);
//			throw new FacilityGameException("Player not implemented yet: " + playerType);
			// System.exit(-1);
			// break;
		default:
			System.err.println("Unexpected player type: " + playerType);
			throw new FacilityGameException("Unexpected player type: " + playerType);
			// System.exit(-1);
			// break;
		}

		return player;
	}
}
