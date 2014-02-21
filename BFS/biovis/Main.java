package BFS.biovis;

import BFS.biovis.data.EigenMath;
import BFS.biovis.data.Matrix;
import BFS.biovis.data.MatrixMath;
import BFS.biovis.graphics.SimpleGraphics;
import BFS.biovis.io.AdjMatrixInput;
import BFS.biovis.io.MatrixImageIO;
import BFS.biovis.io.MatrixOutput;

public class Main
{

	public static void main(String[] args) 
	{	
		Matrix avgFile = AdjMatrixInput.loadMatrixFrom("data/output/averageMatrix.txt");
		
		Matrix[] matrices = new Matrix[5 * 4];
		Matrix[] diffMatrices = new Matrix[5 * 4];
		for(int i = 1; i <= 5; i++)
		{
			for(int j = 1; j <= 4; j++)
			{
				Matrix mat = AdjMatrixInput.loadMatrix(i, j);
				matrices[((i - 1) * 4) + (j - 1)] = mat;
				diffMatrices[((i - 1) * 4) + (j - 1)] = MatrixMath.diffMatrix(avgFile, mat);
			}
		}

		SimpleGraphics.displayMatrixImageWithControls(avgFile, 4, false);
		
		/* Matrix covar = new Matrix(167, 167);
		for(int i = 0; i < diffMatrices.length; i++)
		{
			Matrix cross = MatrixMath.crossProduct(diffMatrices[i], diffMatrices[i].transpose());
			covar = MatrixMath.addMatrix(covar, cross);
		}
		MatrixMath.dotProduct((1d / matrices.length), covar);
		
		MatrixOutput.saveMatrix(covar, "covarianceMatrix");
		SimpleGraphics.displayMatrixImage(covar, 4, false); */
	}

}
