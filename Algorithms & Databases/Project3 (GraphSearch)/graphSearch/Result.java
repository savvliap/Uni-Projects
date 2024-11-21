package graphSearch;

import graphClient.Finals;
import graphGame.PlayerMove;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;

public class Result implements Serializable{

	private static final long serialVersionUID = -4053476480114048838L;
	
	public static final int BIT_DEGREE_ARRAY = 0b1;
	public static final int BIT_MAX_DISTANCE_NODE_ID = 0b1<<1;
	public static final int BIT_MAX_DISTANCE = 0b1<<2;
	public static final int BIT_MAX_DEGREE_NODE_ID = 0b1<<3;
	public static final int BIT_MAX_DEGREE = 0b1<<4;
	public static final int BIT_M = 0b1<<5;
	public static final int BIT_N = 0b1<<6;
	public static final int BIT_MAX_NUM_OF_COMMON_NEIGHBORS = 0b1<<7;
	public static final int BIT_PAIR_WITH_MAX_NUM_OF_COMMON_NEIGHBORS = 0b1<<8;
	public static final int BIT_NUM_NODES_WITH_ODD_DEGREE = 0b1<<9;
	public static final int BIT_NUM_NODES_WITH_EVEN_DEGREE = 0b1<<10;
	
	public static final int BIT_MASK_GRAPH_SEARCH_2018_COMPULSORY = BIT_N | BIT_M | BIT_NUM_NODES_WITH_ODD_DEGREE | BIT_NUM_NODES_WITH_EVEN_DEGREE;
	public static final int BIT_MASK_GRAPH_SEARCH_2018_BONUS = BIT_PAIR_WITH_MAX_NUM_OF_COMMON_NEIGHBORS | BIT_MAX_NUM_OF_COMMON_NEIGHBORS;
	
	public static boolean assessmentCompulsoryQuestions(int res) {
		// A bit value of 1 indicates failure
		boolean assessmentCompulsory = false;
		
		int compRes = res & Result.BIT_MASK_GRAPH_SEARCH_2018_COMPULSORY;
		assessmentCompulsory = (compRes == 0);
		
		return assessmentCompulsory;
	}
	
	public static boolean assessmentBonusQuestions(int res) {
		// A bit value of 1 indicates failure 
		boolean assessmentBonus = false;
		
		int bonusRes = res & Result.BIT_MASK_GRAPH_SEARCH_2018_BONUS;
		assessmentBonus = (bonusRes == 0);
		
		return assessmentBonus;
	}
	
	long maxDegreeNodeID; // The id of a max degree node
	int maxDegree; // The max degree (number of edges attached to the node)
	int n; // The total number of nodes in the graph
	int m; // The total number of edges in the graph
	int numOfNodesWithOddDegree; // Number of nodes with odd degree
	int numOfNodesWithEvenDegree; // Number of nodes with even degree
	
	long maxDistanceNodeID; // The id of a max distance node
	int maxDistance; // The max distance (number of edges from the start node)
	
	int[] degreeArray; // The degrees of the nodes in decreasing order
	
	int maxNumOfCommonNeighbors; // The max number of common neighbors between two nodes
	NodePair pairWithMaxNumOfCommonNeighbors; 
	
	// These two fields are ignored in the equals() method
	PlayerMove myMove; // My Moves: which nodes do I bribe.   
	PlayerMove opponentMove; // Optional field, used only for testing. Which nodes does the opponent player bribe.
	
	public Result(){
		// Used for the DeGroot game
		myMove = new PlayerMove(Finals.DEFAULT_MAX_NUM_OF_NODE_BRIBES, Finals.DEFAULT_MAX_TOTAL_BRIBE);
		// Optional. Used only for testing in the DeGroot game.
		opponentMove = new PlayerMove(Finals.DEFAULT_MAX_NUM_OF_NODE_BRIBES, Finals.DEFAULT_MAX_TOTAL_BRIBE);
	}
	
	public Result(long nodeID, int degree, int n, int m){
		this.maxDegreeNodeID = nodeID;
		this.maxDegree = degree;
		this.n = n;
		this.m = m;
		
		
		// Used for the DeGroot game
		myMove = new PlayerMove(Finals.DEFAULT_MAX_NUM_OF_NODE_BRIBES, Finals.DEFAULT_MAX_TOTAL_BRIBE);
		// Optional. Used only for testing in the DeGroot game.
		opponentMove = new PlayerMove(Finals.DEFAULT_MAX_NUM_OF_NODE_BRIBES, Finals.DEFAULT_MAX_TOTAL_BRIBE);
	}
	
	public String toString() {
		String str = "Result: nodeID:=" + maxDegreeNodeID + ", degree:=" + maxDegree + ", n:=" + n + ", m:=" + m;
		return str;
	}
	
	public boolean equals(Object obj){
		boolean ret = false;
		Result tmp = (Result)obj;
		if(tmp.maxDegreeNodeID == this.maxDegreeNodeID && tmp.maxDegree == this.maxDegree && tmp.n == this.n && tmp.m == this.m){
			ret = true;
		}
		return ret;
	}
	
	public PlayerMove getMyMove() {
		return myMove;
	}
	
	public PlayerMove getOpponentMove() {
		return opponentMove;
	}
	
	public long getMaxDegreeNodeID() {
		return maxDegreeNodeID;
	}; 

	public int getMaxDegree() {
		return maxDegree;
	}; 

	public int getN() {
		return n;
	}; 

	public int getM() {
		return m;
	}; 

	public int getNumOfNodesWithOddDegree() {
		return numOfNodesWithOddDegree;
	}

	public int getNumOfNodesWithEvenDegree() {
		return numOfNodesWithEvenDegree;
	}

	public long getMaxDistanceNodeID() {
		return maxDistanceNodeID;
	}; 

	public int getMaxDistance() {
		return maxDistance;
	}; 
	
	public int[] getDegreeArray() {
		return degreeArray;
	}; 

	public int getMaxNumOfCommonNeighbors() {
		return maxNumOfCommonNeighbors;
	}; 

	public NodePair getPairWithMaxNumOfCommonNeighbors() {
		return pairWithMaxNumOfCommonNeighbors;
	}; 
	
	public String assessmentInfo(int assessment) {
		String info = "Number of nodes (" + n + ") -> "+ (((assessment & BIT_N) == 0)? "OK": "Wrong answer");
		info += "\nNumber of edges (" + m + ") -> " + (((assessment & BIT_M) == 0)? "OK": "Wrong answer");
		info += "\nNumber of nodes with odd degree (" + numOfNodesWithOddDegree + ") -> "+ (((assessment & BIT_NUM_NODES_WITH_ODD_DEGREE) == 0)? "OK": "Wrong answer");
		info += "\nNumber of nodes with even degree (" + numOfNodesWithEvenDegree + ") -> "+ (((assessment & BIT_NUM_NODES_WITH_EVEN_DEGREE) == 0)? "OK": "Wrong answer");
		info += "\n(bonus) Pair with Max Num of Common Pairs -> "+ (((assessment & BIT_PAIR_WITH_MAX_NUM_OF_COMMON_NEIGHBORS) == 0)? "OK": "Wrong answer");
		info += "\n(bonus) Max Num Of Common Neighbors (" + maxNumOfCommonNeighbors + ") -> "+ (((assessment & BIT_MAX_NUM_OF_COMMON_NEIGHBORS) == 0)? "OK": "Wrong answer");
		info += "\n(optional)Max degree (" + maxDegree + ") -> "+ (((assessment & BIT_MAX_DEGREE) == 0)? "OK": "Wrong answer");
		info += "\n(optional)Max degree node ("+ maxDegreeNodeID + ") -> " + (((assessment & BIT_MAX_DEGREE_NODE_ID) == 0)? "OK": "Wrong answer");
		info += "\n(optional)Max distance (" + maxDistance + ") -> " + (((assessment & BIT_MAX_DISTANCE) == 0)? "OK": "Wrong answer");
		info += "\n(optional)Max distance node (" + maxDistanceNodeID + ") -> " + (((assessment & BIT_MAX_DISTANCE_NODE_ID) == 0)? "OK": "Wrong answer");
		info += "\n(optional)Sorted degree array -> " + (((assessment & BIT_DEGREE_ARRAY) == 0)? "OK": "Wrong answer");
		return info;
	}
	
}
