package gr.duth.ee.euclid.datastructures.prime;

public class Prime 
{
	
	public static Boolean isPrime(int x)
	{
		if (x==2) return true;
		else if (x%2==0) return false;
		else 
		{
			for(int i=3;i<=Math.sqrt(x);i+=2) 
		    {
		        if(x%i==0) return false;
		    }
		    return true;
		}
	}
}

