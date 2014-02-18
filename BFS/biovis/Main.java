package BFS.biovis;

import BFS.biovis.data.Matrix;
import BFS.biovis.graphics.SimpleGraphics;
import BFS.biovis.io.AdjMatrixInput;
import BFS.biovis.io.MatrixImageIO;

public class Main
{

	public static void main(String[] args) 
	{
		for(int i = 1; i <= 5; i++)
		{
			Matrix mat11 = AdjMatrixInput.loadMatrix(i, 1);
			Matrix mat12 = AdjMatrixInput.loadMatrix(i, 2);
			Matrix mat13 = AdjMatrixInput.loadMatrix(i, 3);
			Matrix mat14 = AdjMatrixInput.loadMatrix(i, 4);
			Matrix mat1mean = Matrix.meanMatrix(mat11, mat12, mat13, mat14);
			//SimpleGraphics.displayMatrixImage(mat1mean, 4, false);
			MatrixImageIO.outputImage(SimpleGraphics.getMatrixAsImage(mat1mean, 4, false), "subjectData-mean-" + i);
		}
	}

}
