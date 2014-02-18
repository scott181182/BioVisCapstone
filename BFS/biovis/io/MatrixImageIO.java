package BFS.biovis.io;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class MatrixImageIO
{
	
	
	public static void outputImage(BufferedImage img, String filename)
	{
		File file = new File("data/output/" + filename + ".jpg");
		try { ImageIO.write(img, "PNG", file); }
		catch(IOException ioe) { ioe.printStackTrace(); }
	}
}
