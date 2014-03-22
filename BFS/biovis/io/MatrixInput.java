package BFS.biovis.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import BFS.biovis.data.Matrix;

public class MatrixInput
{
	
	
	public static Matrix readMatrixTab(String filename)
	{
		ArrayList<String[]> rawData = new ArrayList<>();
		Scanner scan = null;
		try { scan = new Scanner(new File(filename)); }
		catch(FileNotFoundException fnfe) { fnfe.printStackTrace(); return null; }
		
		while(scan.hasNext())
		{
			String temp = scan.nextLine();
			rawData.add(temp.split("\t"));
		}
		scan.close();
		
		Matrix ret = new Matrix(rawData.size(), rawData.get(0).length);
		for(int row = 0; row < ret.getRows(); row++)
		{
			for(int col = 0; col < ret.getCols(); col++)
			{
				String[] temp = rawData.get(row);
				ret.set(Double.parseDouble(temp[col]), row, col);
			}
		}
		return ret;
	}
}
