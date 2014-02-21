package BFS.biovis.data;

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
}
