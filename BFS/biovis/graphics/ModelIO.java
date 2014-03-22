package BFS.biovis.graphics;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import BFS.biovis.data.Matrix;

public class ModelIO
{
	
	
	public static Model readOBJ(String filename)
	{
		Model model = new Model();
		
		try
		{
			Scanner scan = new Scanner(new File(filename));
			
			while(scan.hasNext())
			{
				String temp = scan.nextLine();
				
				if(temp.startsWith("v "))
				{
					String[] v = temp.split(" ");
					model.vertices.add(new double[]{ Double.parseDouble(v[1]), Double.parseDouble(v[2]), Double.parseDouble(v[3]) });
				}
				else if(temp.startsWith("f "))
				{
					
				}
			}
			scan.close();
		} catch(IOException ioe) { ioe.printStackTrace(); }
		
		return model;
	}
}
