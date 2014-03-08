package BFS.biovis.data;

import java.util.ArrayList;
import BFS.biovis.Main;

public class EigenMath
{
	
	
	public static Matrix getVectors(Matrix... matrices)
	{
		Matrix ret = new Matrix(matrices[0].getRows() * matrices[0].getCols(), matrices.length);
		for(int i = 0; i < matrices.length; i++)
		{
			for(int j = 0; j < matrices[i].getLength(); j++)
			{
				ret.set(matrices[i].getRaw(j), j, i);
			}
		}
		return ret;
	}
	public static Matrix getDiffVectors(Matrix avg, Matrix vectors)
	{
		Matrix ret = new Matrix(vectors.getRows(), vectors.getCols());
		
		for(int col = 0; col < vectors.getCols(); col++)
		{
			for(int row = 0; row < avg.getRows(); row++)
			{
				ret.set(vectors.get(row, col) - avg.get(row, 0), row, col);
			}
		}
		
		return ret;
	}
	
	public static Matrix innerProducts(Matrix vectors)
	{
		Matrix ret = new Matrix(vectors.getCols(), vectors.getCols());
		Matrix trans = vectors.transpose();
		
		for(int i = 0; i < ret.getRows(); i++)
		{
			for(int j = 0; j < ret.getCols(); j++)
			{
				ret.set(MatrixMath.innerProduct(trans.getRow(i), vectors.getColumn(j)), i, j);
			}
		}
		return ret;
	}
	
	public static Matrix vectorToMatrix(Matrix vector, boolean isSquare, int m, int n)
	{
		Matrix ret = null;
		if(isSquare)
		{
			n = (int)Math.sqrt(vector.getLength());
			ret = new Matrix(n, n);
		}
		else { ret = new Matrix(m, n); }
		
		for(int i = 0; i < ret.getLength(); i++) { ret.setRaw(vector.getRaw(i), i); }
		return ret;
	}
	
	
	
	public static Matrix getEigenMatrix(Matrix... matrices)
	{
		Main.Timer t = new Main.Timer();
		t.start();
		Matrix ret = new Matrix(matrices[0].getRows(), matrices[0].getCols());
		
		//Matrix avgVector = getVectors(MatrixMath.meanMatrix(matrices));
		Matrix vectors = getVectors(matrices);
		//Matrix diffVectors = getDiffVectors(avgVector, vectors);
		Matrix L = innerProducts(vectors);

		EigenvalueDecomposition decomp = new EigenvalueDecomposition(L);
		double[] eigenValuesRaw = decomp.getRealEigenvalues();
		Matrix eigenVectorsRaw = decomp.getV();
		
		ArrayList<Double> eigenValueList = new ArrayList<>();
		ArrayList<Matrix> eigenVectorList = new ArrayList<>();
		for(int i = 0; i < eigenValuesRaw.length; i++)
		{
			if(Math.abs(eigenValuesRaw[i]) > 0)
			{
				eigenValueList.add(eigenValuesRaw[i]);
				eigenVectorList.add(eigenVectorsRaw.getColumn(i));
			}
		}
		
		double[] eigenValues = new double[eigenValueList.size()];
		Matrix eigenVectorsM = new Matrix(eigenVectorsRaw.getRows(), eigenVectorList.size());
		for(int i = 0; i < eigenValues.length; i++)
		{
			eigenValues[i] = eigenValueList.get(i);
			for(int j = 0; j < eigenVectorsRaw.getRows(); j++)
			{
				eigenVectorsM.set(eigenVectorList.get(i).get(j, 0), j, i);
			}
		}
		
		Matrix eigenVectors = new Matrix(vectors.getRows(), eigenVectorsM.getCols());
		Matrix temp = new Matrix(eigenVectorsM.getRows(), 1);
		for(int i = 0; i < eigenVectorsM.getCols(); i++)
		{
			temp = MatrixMath.dotProduct(vectors, eigenVectorsM.getColumn(i));
			for(int j = 0; j < temp.getRows(); j++)
			{
				eigenVectors.set(temp.get(j, 0), j, i);
			}
		}
		
		t.stop();
		System.out.println("[EigenMath] Took " + t.getTime(Main.Timer.MILLISECONDS));
		return ret;
	}
}