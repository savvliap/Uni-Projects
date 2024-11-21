package graphSearch;

import graphClient.XGraphClient;
import java.util.*;


public class GraphSearch {

	public int afm = 57403; // AFM should be in form 5XXXX
	public String firstname = "Savvas";
	public String lastname = "Liapis";

	XGraphClient xgraph;

	public GraphSearch(XGraphClient xgraph) {
		this.xgraph = xgraph;
	}


	public Result findResults() {
		Result res = null;
		
		res = new Result();
		
		//dimiourgw to hash set 
		HashSet<Long> hs=new HashSet<Long>();
		
		//dimiourgw mia stoiva 
		Stack<Long> stack=new Stack<Long>();
		
		// pairnw to prwto node apo ton grafo 
		long firstNode = xgraph.firstNode();

		// ksekinaei h diadikasia 
		System.out.println("Graph search from node : " + firstNode);
		
		//vazw to id tou fistnode sto stack
	    stack.add(firstNode);
	    
	    //arxikopoiw tis akmes 
	    int edges=0;
	    
	    //arxikopoiw tous komvous me odd degree
	    int oddeg=0;
	    
	    //arxikopoiw tous komvous me even degree
	    int evendeg=0;
	    
	    //ksekinaw mia loupa h opoia diarkei oso h stoiva exei stoixeia mesa 
	    while (stack.isEmpty()==false)
	    {    
	    	//mou petaei  to stoixeio sthn korufh ths stoivas 
	    	long node= stack.pop();
	        
	    	//ean einai idi mesa sto hash set den to epanalamvanw 
	    	if (hs.contains(node))break;
	    	//an den einai to vazw kai kanw thn diadikasia 
	    	else hs.add(node);
	    	
	    	System.out.println("current nodes id:"+node);
	    	
	    	//mou dinei tous geitones tou stoixeiou
	    	long [] neighbors=xgraph.getNeighborsOf(node);
	    
	    	
	    	//pairnw kai ton arithmo twn geitonwn
	    	int numOfNeighbors =neighbors.length;
	    	
	    	if (numOfNeighbors!=0)
	    	{
	    		if (numOfNeighbors%2==0) evendeg=evendeg+1;
	    		else oddeg=oddeg+1;
	    	}
	    	
	    	for (int i=0; i<numOfNeighbors; i++)
	    	{
	    		
	    		long k=neighbors[i];
	    		if (k!=0 && !hs.contains(k))
	    		{
		    		stack.add(k); //prosthetw sthn stoiva mou gia na to meletisw
		    		edges=edges+1;
	    		}
	    		
	    	}
	    }
	    
	    res.n=hs.size();
	    res.m=edges;
	    res.numOfNodesWithOddDegree= oddeg;
		res.numOfNodesWithEvenDegree =evendeg;
		// BONUS
		// res.pairWithMaxNumOfCommonNeighbors (type: NodePair)
		// res.maxNumOfCommonNeighbors (type: int)
		
		return res;
	}

}