package facilityGame;

public class MonitorThread extends Thread {
	private EnumPlayerState playerState;
	private long timeOfLastChange; // state change
	private int gameRound;
	private long timeOfStart; // Start of the monitor Thread (and the game)
	
	private long timeOfLastInfoMessage; // info message
	private long timeOfLastWarningMessage; // info message
	
	private boolean stopReceived; // indicates if the thread has been asked to stop
	
	MonitorThread() {
		stopReceived = false;
	}
	
	synchronized void setState(int gameRound, EnumPlayerState playerState) {
		this.gameRound = gameRound; 
		if (this.playerState == playerState) {
			// nothing changed => do nothing
		} else {
			this.playerState = playerState;
			//timeOfLastInfoMessage = timeOfLastChange = System.currentTimeMillis();
			timeOfLastChange = System.currentTimeMillis();
			gameRound = -1;
		}
	}

	synchronized void checkProgress() {
		long currentTimeMillis = System.currentTimeMillis();
		long timePassed = currentTimeMillis - timeOfLastChange;
		long timePassedSinceLastWarning = currentTimeMillis - timeOfLastWarningMessage;
		if (timePassed > Finals.MSEC_WAITING && timePassedSinceLastWarning > Finals.MSEC_FOR_WARNING_MESSAGE) {
//			System.err.println("Monitor Warning in round " + gameRound + ", player in state "
//					+ playerState + " since " + timePassed + " msec!!");
			log("Monitor Warning in round " + gameRound + ", player in state "
					+ playerState + " since " + timePassed + " msec!!");
			timeOfLastWarningMessage = System.currentTimeMillis();
		}
	}
	
	synchronized void checkForInfoMessage() {
		long now = System.currentTimeMillis();
		long timePassed = now - timeOfLastInfoMessage;
		if (timePassed > Finals.MSEC_FOR_INFO_MESSAGE) {
			timeOfLastInfoMessage = now;
			log("Monitor Info: Game in round " + gameRound);
			//System.err.println("Monitor Info: Game in round " + gameRound);
		}
	}

	synchronized void requestStop() {
		stopReceived = true;
	}
	
	public void run() {
		try {
			timeOfStart = System.currentTimeMillis();
			timeOfLastWarningMessage = timeOfLastInfoMessage = timeOfStart;
			while (!stopReceived && playerState != EnumPlayerState.TERMINATING) {
				checkProgress();
				checkForInfoMessage();
				sleep(Finals.MSEC_CHECK_PERIOD);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println(e);
		}
	}
	
	private void log(String msg) {
		long timeSinceStart = System.currentTimeMillis() - timeOfStart;
		System.err.println("game time: " + timeSinceStart + ", " + msg);
	}
}
