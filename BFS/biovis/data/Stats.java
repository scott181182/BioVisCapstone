package BFS.biovis.data;

import java.util.Arrays;

public class Stats
{
	public static double variance(double[] data)
	{
		double avg = 0, var = 0;;
		for(int i = 0; i < data.length; i++) { avg += data[i]; }
		avg /= data.length;
		double[] diff = new double[data.length];
		for(int i = 0; i < data.length; i++) { diff[i] = (data[i] - avg) * (data[i] - avg); var += diff[i]; }
		var /= data.length;
		return var;
	}
	public static double standardDeviation(double[] data) { return Math.sqrt(variance(data)); }
	
	public static double mean(double... data)
	{
		double ret = 0;
		for(int i = 0; i < data.length; i++) { ret += data[i]; }
		return ret / data.length;
	}
	public static double range(double... data) { return data[data.length - 1] - data[0]; }
	
	
	
	public static double median(double... data)
	{
		Arrays.sort(data);
		if(data.length % 2 == 0) { return mean(data[data.length/2], data[data.length/2 - 1]); }
		else { return data[data.length / 2]; }
	}
	public static double firstQuartile(double... data)
	{
		Arrays.sort(data);
		double[] bottom = new double[data.length / 2];
		for(int i = 0; i < bottom.length; i++) { bottom[i] = data[i]; }
		
		if(bottom.length % 2 == 0) { return mean(bottom[bottom.length/2], bottom[bottom.length/2 - 1]); }
		else { return bottom[bottom.length / 2]; }
	}
	public static double thirdQuartile(double... data)
	{
		Arrays.sort(data);
		double[] top = new double[data.length / 2];
		for(int i = 0; i < top.length; i++) { top[top.length - 1 - i] = data[data.length - 1 - i]; }

		if(top.length % 2 == 0) { return mean(top[top.length/2], top[top.length/2 - 1]); }
		else { return top[top.length / 2]; }
	}
	
	public static double[] lowerQuartile(double... data)
	{
		Arrays.sort(data);
		double[] bottom = new double[data.length / 2];
		for(int i = 0; i < bottom.length; i++) { bottom[i] = data[i]; }
		
		double[] lower = new double[bottom.length / 2];
		for(int i = 0; i < lower.length; i++) { lower[lower.length - 1 - i] = data[data.length - 1 - i]; }
		return lower;
	}
	public static double[] upperQuartile(double... data)
	{
		Arrays.sort(data);
		double[] top = new double[data.length / 2];
		for(int i = 0; i < top.length; i++) { top[i] = data[i]; }
		
		double[] upper = new double[top.length / 2];
		for(int i = 0; i < upper.length; i++) { upper[i] = top[i]; }
		return upper;
	}
}
