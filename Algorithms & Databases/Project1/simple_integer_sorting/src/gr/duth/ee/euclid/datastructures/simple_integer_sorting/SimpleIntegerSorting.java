package gr.duth.ee.euclid.datastructures.simple_integer_sorting;

public class SimpleIntegerSorting 
{

	public static int[] simpleIntegerSorting(int[] values) 
	{
		for (int i=0; i<values.length;i++)
		{
			int temp= values[i];
			int j;
			for (j=i;j>0 && temp>values[j-1];j--) values[j]=values[j-1];
			
			values[j]=temp;
		}
		return values ;
	}

}
