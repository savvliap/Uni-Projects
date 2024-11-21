package gr.duth.ee.euclid.datastructures.geometric_mean;

public class FindGeometricMean 
{

	public static double findGeometricMean(int[] values) 
	{
		double gm=1;
		for (int i = 0; i< values.length; i++) gm=gm*values[i];
		gm= (double) Math.pow(gm, 1/(double)values.length);
		return 	gm;
	}

}