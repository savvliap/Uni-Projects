package facilityGame;

import java.util.Vector;

public class SavvasLiapis extends FPlayer {
	static String playerName = "savvas_liapis";
	static String version = "1.0";
	// Give your personal information in Greek
	static int afm = 57403; // AFM should be in form 5XXXX
	static String firstname = "Σάββας";
	static String lastname = "Λιάπης";

	// Member variables
	int n; // Number of nodes in the graph

	// Constructor
	public SavvasLiapis(EnumPlayer player) {
		// Call the constructor of the parent class
		super(player, playerName, version, afm, firstname, lastname);
	}

	// Initialize Player
	// This method is called before the game starts
	// Each player can override this method
	public void initialize(FacilityGameAPI game) {
		n = game.getN();
	}

	public int nextMove(FacilityGameAPI game) 
	{
		int move = 0;
		int middle=n/2; //to xreiazomai giati doulew me shmeio anafora thn mesi 
		
		int lastBmove;
		
		int curIndex=game.getCurMoveIndex();
		
		Vector<Integer> moveLocation = game.getMoveLocation();
		
		EnumFacilityStatus[] status=game.getStatusCopy();
		
		
		
		//to xreiazomai gia na vallw periorismo gia to swiching mode 
		//an o pinakas exei megethos mikrotero iso 1 egw den tha exw kanei kinhsh prohgoumenws 
		if (curIndex==1)lastBmove=0; 
		else lastBmove=moveLocation.get(curIndex-2);
		//to xreiazomai giati apo auto dialegw an tha kanw kinhsh deksia h aristera 
		int lastAmove=moveLocation.get(curIndex-1);
		//to xreiazomai giati me auto tha apofasizw pou tha kanw kinhsh 
		
		
		
		//o periorismos gia to switching mode mou 
		//an einai konta sto telos tou pinaka den thelw na kseperasei ta oria kai na vgazei sfalma 
		if (lastBmove>=n-4)
		{
			for (int i=lastBmove-3;i<n-1;i++) status[i]=EnumFacilityStatus.BLOCKED;
		}
		//an einai sthn arxi tou pinaka vazw ton periorismo gia na mhn mou vgazei pali sfalma 
		else if (lastBmove<=3)
		{
			for (int i=0;i<lastBmove+3;i++) status[i]=EnumFacilityStatus.BLOCKED;
		}
		else for(int i=lastBmove-3;i<=lastBmove+3;i++)status[i]=EnumFacilityStatus.BLOCKED;
	
		
		
		
		//h prwti kinisi mou 
		if (status[middle]==EnumFacilityStatus.FREE )move=middle;
		//stratigiki gia tis epomenes kiniseis mou 
		//eksetazw arxika an h teleutaia kinhsi tou eixe ginei aristera 
		else if (lastAmove<middle)
		{
			//eksetazw an exei duos thn seira gia na ton mplokarw
			//vazw thn for na ksekinaei apo thn arxi gt o paiktis A etsi kanei 
			for (int i=0;i<middle;i++) 
			{
				//elegxw an exei 2 sthn seira kai ta blockarw an exei .
				//thelw prwta na eleksw ola ta nodes gia block
				//kai meta na paw na valw se eleuthero node an den uparxei kati na mplokarw 
				if (status[i]==EnumFacilityStatus.PLAYER_A && status[i+2]==EnumFacilityStatus.PLAYER_A && status[i+4]==EnumFacilityStatus.FREE)
				{
					move=i+4;
					break; 
				}
			}
			
			//afou teleiwsei h loupa mpainw sthn alli loupa gia na vallw se diko m node
			//an exei vgei kati apo thn prohgoumenh thelw na mhn treksei 
			//to vazw na ksekinaei apo tin mesi kai na apomakrunetai gt me noiazei na kalupsw theseis konta sto kentro  
		     for (int j=middle;j>0;j--) 
		    	 {	
		    		 if(status[j]== EnumFacilityStatus.FREE )
						{
							move=j;
							break;
						}
		    	 }
		     
		     //an exoun kaluftei oles oi theseis apo aristera 
		     if (move==0)
		     {
		    	 for (int j=middle;j<n;j++)
		    	 {
		    		 if(status[j]== EnumFacilityStatus.FREE )
		    		 {
		    			 move=j;
		    			 break;
		    		 }
		    	 }
		     }
		
		// an h teleutaia kinhsi tou eixe ginei deksia  
		}else if (lastAmove>middle){
			//to vazw na ksekinaei apo to telos gt etsi kanei o A 
			for (int i=n-1;i>middle;i--){
				//elegxw an exei 2 sthn seira kai ta blockarw an exei .
				//thelw prwta na eleksw ola ta nodes gia block
				//kai meta na paw na valw se eleuthero node an den uparxei kati na mplokarw 
				if (status[i]==EnumFacilityStatus.PLAYER_A && status[i-2]==EnumFacilityStatus.PLAYER_A && status[i-4]==EnumFacilityStatus.FREE)
				{
					move=i-4;
					break; 
				}
			}


			
				//afou teleiwsei h loupa mpainw sthn alli loupa gia na vallw se diko m node
				//an exei vgei kati apo thn prohgoumenh thelw na mhn treksei 
				//thelw na treksei apo thn mesi  gt me endiaferoun oi mesaies theseis gia combos 
			     for (int j=middle;j<n-1;j++){
			    	 
		              if(status[j]== EnumFacilityStatus.FREE ){
			    			 move=j;
			    			 break;
			    		 }
			    		 
			    	 }
			     
			     //an exoun kalufthei oles oi theseis apo deksia 
			     if (move==0){
			    	 for (int j=middle;j>0;j--){
			    		 if(status[j]== EnumFacilityStatus.FREE ) {
			    			 move=j;
			    			 break;
			    		 }
			    	 }
			     }
			  }
		//}
			
//		EnumPlayer myid = whoAmI();
//
//		// The status of all nodes/locations
//		int[] valuesOfAllNodes = game.getValue();
//
//		// The status of all nodes/locations
//		EnumFacilityStatus[] statusOfAllNodes = game.getStatus();
//
//		// the number of moves so far
//		int curMoveIndex = game.getCurMoveIndex();
//
//		// The vector of all moves so far
//		Vector<EnumPlayer> moveByPlayer = game.getMoveByPlayer();
//		EnumPlayer[] moves = moveByPlayer.toArray(new EnumPlayer[0]);
//
//		// The vector with the location requested at each move
//		Vector<Integer> moveLocation = game.getMoveLocation();
//		Integer[] locations = moveLocation.toArray(new Integer[0]);
		
		return move;
	}
}