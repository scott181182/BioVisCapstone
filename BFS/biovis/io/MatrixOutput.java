package BFS.biovis.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import BFS.biovis.data.Matrix;

public class MatrixOutput
{
	
	
	public static void saveMatrix(Matrix mat, String filename)
	{
		BufferedWriter writer = null;
		try
		{
			File file = new File("data/output/" + filename + ".txt");
			file.createNewFile();
			writer = new BufferedWriter(new FileWriter(file));
			
			for(int i = 0; i < mat.getRows(); i++)
			{
				for(int j = 0; j < mat.getCols(); j++)
				{
					if(j != 0) { writer.write("\t"); }
					writer.write(String.valueOf(mat.get(i, j)));
				}
				if(i != (mat.getRows() - 1)) { writer.newLine(); }
			}

			writer.close();
		}
		catch(IOException ioe) { ioe.printStackTrace(); }
	}
}
