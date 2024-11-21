package facilityGame;

import java.util.Vector;

public interface FacilityGameAPI {

	// Return the number of nodes in the game
	public int getN();

	// Return the seed for the pseudorandom number generator
	public long getSeed();

	// Get the value of a specific node
	public int getValue(int i);

	// Get an array with the values of all nodes
	public int[] getValue();
	
	// Get a copy of the array with the values of all nodes
	public int[] getValueCopy();

	// Get the status of a specific node
	public EnumFacilityStatus getStatus(int node);

	// Get an array with the status of all nodes
	public EnumFacilityStatus[] getStatus();

	// Get a copy of the array with the status of all nodes
	public EnumFacilityStatus[] getStatusCopy();
			
	// The number of moves so far
	public int getCurMoveIndex();

	// Which player made each move?
	public Vector<EnumPlayer> getMoveByPlayer();

	// Which location/node was chosen at each move?
	public Vector<Integer> getMoveLocation();

	// Get current score
	public GameScore getScore();

	// Are there any moves left in the game?
	public boolean isFinished();

}
