package BFS.biovis.io;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import BFS.biovis.data.Matrix;
import BFS.biovis.graphics.SimpleGraphics;

public class MatrixImageIO
{
	
	
	public static void outputImage(BufferedImage img, String filename)
	{
		File file = new File("data/output/" + filename + ".png");
		try { ImageIO.write(img, "PNG", file); }
		catch(IOException ioe) { ioe.printStackTrace(); }
	}
	
	public static void outputImage(Matrix mat, String filename, double min, double max)
	{
		outputImage(SimpleGraphics.getMatrixAsImage(mat, 4, false, min, max), filename);
	}
}
