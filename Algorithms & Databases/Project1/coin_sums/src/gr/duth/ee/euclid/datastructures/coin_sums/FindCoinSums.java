package gr.duth.ee.euclid.datastructures.coin_sums;

public class FindCoinSums 
{

	public static long findCoinSums(int euros) 
	{
		int[] coins=new int[] {1,2,5,10,20,50,100,200};
		int size=coins.length;
		int price=euros*100;
	
        long[] combinations = new long[price+1]; 
        combinations[0] = 1; 
        
        for (int i=0; i<size; i++)
        {
            for (int j=coins[i]; j<=price; j++) combinations[j] += combinations[j-coins[i]]; 
        }        
        return combinations[price];
	}
}