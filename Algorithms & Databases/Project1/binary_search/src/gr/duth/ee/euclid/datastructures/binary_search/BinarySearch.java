package gr.duth.ee.euclid.datastructures.binary_search;

public class BinarySearch 
{

	public static int binarySearch(IntegerArray a, int valueToFind) 
	{
		int first=0; 
		int last=a.length()-1;
		
		while (first<=last) 
			{
				int middlepos=(last +first)/2;
				int middleval=a.get(middlepos);
				
				if (middleval==valueToFind) return middlepos;
				else if (middleval<valueToFind)last=middlepos-1;
				else if (middleval>valueToFind) first=middlepos+1;
			}
		return -1;
	}

}
