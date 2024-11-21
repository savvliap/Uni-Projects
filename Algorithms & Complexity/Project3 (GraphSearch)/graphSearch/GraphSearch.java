package graphSearch;

import graphClient.XGraphClient;
import java.util.*;


public class GraphSearch {

	public int afm = 57292; // AFM should be in form 5XXXX
	public String firstname = "giwrgos";
	public String lastname = "Loukopoulos";

	XGraphClient xgraph;

	public GraphSearch(XGraphClient xgraph) {
		this.xgraph = xgraph;
	}

	public Result findResults() {
		Result res = null;
		
		res = new Result();
		
        int edg=0;
	    int odgr=0;
	    int evndg=0;
	
		HashSet<Long> table=new HashSet<Long>();
		Stack<Long> stk=new Stack<Long>();
	
		long firstNode = xgraph.firstNode();
        System.out.println("Graph search from node : " + firstNode);
	    stk.add(firstNode);
	    
	    while (stk.isEmpty()!=true){    
	    	long node= stk.pop();
	    	
	    	if (table.contains(node))break;
	    	else table.add(node);
	    	
	    	System.out.println("current nodes id:"+node);
	    	long [] ngbrs=xgraph.getNeighborsOf(node);
	    	int numOfNeighbors =ngbrs.length;
	    	
	    	if (numOfNeighbors!=0){
	    		if (numOfNeighbors%2 !=0) odgr=odgr+1;
	    		else evndg=evndg+1;
	    	}
	    	
	    	for (int i=0; i<numOfNeighbors; i++){
	    		long k=ngbrs[i];
	    		if (k!=0 && !table.contains(k)){
		    		stk.add(ngbrs[i]); 
		    		edg=edg+1;
	    		}	
	    	}
	    }
	    
	    res.n=table.size();
	    res.m=edg;
	    res.numOfNodesWithOddDegree= odgr;
		res.numOfNodesWithEvenDegree =evndg;
		return res;
	}

}