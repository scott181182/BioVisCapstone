package BFS.biovis.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import BFS.biovis.data.InvalidMatrixException;
import BFS.biovis.data.Matrix;

public class AdjMatrixInput
{
	
	
	public static Matrix loadMatrix(int patientNum, int scanNum)
	{
		return loadMatrixFrom("data/subjectData/example" + patientNum + "_" + scanNum + "_adjacency_matrix_pcc.txt");
	}
	
	public static Matrix loadMatrixFrom(String filename)
	{
		Matrix ret = new Matrix(167, 167);
		
		Scanner scan = null;
		try
		{
			scan = new Scanner(new File(filename));
			int index = 0;
			while(scan.hasNext())
			{
				String[] temp = scan.nextLine().split("\t");
				if(temp.length != 167) 
				{
					String err = "Data File read is not valid\nNumber of Rows = " + temp.length;
					throw new InvalidMatrixException(err);
				}
				for(String str : temp) 
				{ 
					double value = Double.parseDouble(str);
					ret.setRaw(value, index); 
					index++; 
				}
			}
		}
		catch(FileNotFoundException fnfe) { fnfe.printStackTrace(); System.exit(0); }
		catch(InvalidMatrixException ime) { ime.printStackTrace(); }
		catch(NumberFormatException nfe) { nfe.printStackTrace(); }
		finally { if(scan != null) { scan.close(); } }
		
		return ret;
	}
}
