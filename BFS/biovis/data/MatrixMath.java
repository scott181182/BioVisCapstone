package BFS.biovis.data;

public final class MatrixMath
{
	
	/**
	 * Takes the input array of Matrices, all of which must be the same size,<br>
	 * and returns a Matrix of the same size with each index having the average<br>
	 * of each of the values from the input matrices at that index
	 * @param matrices An array of matrices of equal dimensions
	 */
	public static Matrix meanMatrix(Matrix... matrices)
	{
		Matrix ret = new Matrix(matrices[0].getCols(), matrices[0].getRows());
		for(int i = 0; i < matrices[0].getLength(); i++)
		{
			double mean = 0;
			for(int j = 0; j < matrices.length; j++)
			{
				mean += matrices[j].getRaw(i);
			}
			mean /= matrices.length;
			ret.setRaw(mean, i);
		}
		return ret;
	}
	
	public static Matrix diffMatrix(Matrix fromMatrix, Matrix toMatrix)
	{
		if(fromMatrix.getCols() != toMatrix.getCols()) { throw new IllegalArgumentException("Matrix widths don't match"); }
		if(fromMatrix.getRows() != toMatrix.getRows()) { throw new IllegalArgumentException("Matrix heights don't match"); }
		Matrix ret = new Matrix(fromMatrix.getRows(), fromMatrix.getCols());
		for(int i = 0; i < fromMatrix.getLength(); i++)
		{
			ret.setRaw((fromMatrix.getRaw(i) - toMatrix.getRaw(i)), i);
		}
		return ret;
	}
	
	public static Matrix identityMatrix(int n)
	{
		Matrix ret = new Matrix(n, n);
		for(int i = 0; i < n; i++)
		{
			for(int j = 0; j < n; j++)
			{
				ret.set(i == j ? 1 : 0, i, j);
			}
		}
		return ret;
	}

	public static Matrix addMatrix(Matrix fromMatrix, Matrix toMatrix)
	{
		if(fromMatrix.getCols() != toMatrix.getCols()) { throw new IllegalArgumentException("Matrix widths don't match"); }
		if(fromMatrix.getRows() != toMatrix.getRows()) { throw new IllegalArgumentException("Matrix heights don't match"); }
		Matrix ret = new Matrix(fromMatrix.getCols(), fromMatrix.getRows());
		for(int i = 0; i < fromMatrix.getLength(); i++)
		{
			ret.setRaw((fromMatrix.getRaw(i) + toMatrix.getRaw(i)), i);
		}
		return ret;
	}
	
	public static Matrix matrixProduct(Matrix mat1, Matrix mat2)
	{
		if(mat1.getCols() != mat2.getRows()) { throw new IllegalArgumentException("Matrix dimensions aren't correct"); }
		Matrix ret = new Matrix(mat1.getRows(), mat2.getCols());
		for(int i = 0; i < mat1.getRows(); i++)
		{
			for(int j = 0; j < mat2.getCols(); j++)
			{
				Matrix row = mat1.getRow(i);
				Matrix col = mat2.getColumn(j);
				double value = 0;
				for(int k = 0; k < row.getLength(); k++)
				{
					value += row.getRaw(k) * col.getRaw(k);
				}
				ret.set(value, i, j);
			}
		}
		return ret;
	}
	public static double dotProduct(Matrix mat1, Matrix mat2)
	{
		if(mat1.getCols() != mat2.getCols()) { throw new IllegalArgumentException("Matrix columns aren't equal"); }
		if(mat1.getRows() != mat2.getRows()) { throw new IllegalArgumentException("Matrix rows aren't equal"); }
		double ret = 0;
		for(int i = 0; i < mat1.getLength(); i++) { ret += mat1.getRaw(i) * mat2.getRaw(i); }
		return ret;
	}
	public static Matrix crossProduct(Matrix mat1, Matrix mat2)
	{
		Matrix ret = new Matrix(mat1.getRows(), mat1.getCols());
		
		if(mat1.getLength() == 3 && mat2.getLength() == 3)
		{
			ret.setRaw(mat1.getRaw(1) * mat2.getRaw(2) - mat1.getRaw(2) * mat2.getRaw(1), 0);
			ret.setRaw(mat1.getRaw(2) * mat2.getRaw(0) - mat1.getRaw(0) * mat2.getRaw(2), 1);
			ret.setRaw(mat1.getRaw(0) * mat2.getRaw(1) - mat1.getRaw(1) * mat2.getRaw(0), 2);
		}
		else
		{
			double mag1 = mat1.magnitude();
			double mag2 = mat2.magnitude();
			Matrix diff = new Matrix(mat1.getRows(), mat1.getCols(), mat1.getRaw(0) - mat2.getRaw(0), mat1.getRaw(1) - mat2.getRaw(1), mat1.getRaw(2) - mat2.getRaw(2));
			double mag3 = diff.magnitude();
			double theta = Math.acos(((mag3 * mag3) - (mag1 * mag1) - (mag2 * mag2)) / (-2 * mag1 * mag2));
		}
		return ret;
	}
	public static Matrix scalarProduct(double value, Matrix mat)
	{
		Matrix ret = (Matrix)mat.clone();
		for(int i = 0; i < mat.getLength(); i++)
		{
			ret.setRaw(ret.getRaw(i) * value, i);
		}
		return ret;
	}
	public static Matrix scalarDivide(double value, Matrix mat)
	{
		Matrix ret = (Matrix)mat.clone();
		for(int i = 0; i < mat.getLength(); i++)
		{
			ret.setRaw(ret.getRaw(i) / value, i);
		}
		return ret;
	}
	public static double innerProduct(Matrix mat1, Matrix mat2)
	{
		double ret = 0;
		for(int i = 0; i < mat1.getLength(); i++)
		{
			ret += mat1.getRaw(i) * mat2.getRaw(i);
		}
		return ret;
	}
	public static Matrix valueProduct(Matrix mat1, Matrix mat2)
	{
		if(mat1.getCols() != mat2.getCols()) { throw new IllegalArgumentException("Matrix columns aren't equal"); }
		if(mat1.getRows() != mat2.getRows()) { throw new IllegalArgumentException("Matrix rows aren't equal"); }
		Matrix ret = new Matrix(mat1.getRows(), mat1.getCols());
		for(int i = 0; i < mat1.getLength(); i++) { ret.setRaw(mat1.getRaw(i) * mat2.getRaw(i), i); }
		return ret;
	}
	
	public static Matrix valueDivide(Matrix mat1, Matrix mat2)
	{
		if(mat1.getCols() != mat2.getCols()) { throw new IllegalArgumentException("Matrix columns aren't equal"); }
		if(mat1.getRows() != mat2.getRows()) { throw new IllegalArgumentException("Matrix rows aren't equal"); }
		Matrix ret = new Matrix(mat1.getRows(), mat1.getCols());
		for(int i = 0; i < mat1.getLength(); i++)
		{
			double value = 0;
			if(mat1.getRaw(i) == 0) { value = 0; }
			else if(mat2.getRaw(i) == 0) { value = Double.POSITIVE_INFINITY; }
			else { value = mat1.getRaw(i) / mat2.getRaw(i); }
			ret.setRaw(value, i);
		}
		return ret;
	}
	
	public static Matrix scalarAdd(double value, Matrix mat)
	{
		Matrix ret = mat;
		for(int i = 0; i < mat.getLength(); i++) { ret.setRaw(value + ret.getRaw(i), i); }
		return ret;
	}
	
	public static double euclidDistance(Matrix mat1, Matrix mat2)
	{
		if(mat1.getCols() != mat2.getCols()) { throw new IllegalArgumentException("Matrix widths don't match"); }
		if(mat1.getRows() != mat2.getRows()) { throw new IllegalArgumentException("Matrix heights don't match"); }
		double ret = 0;
		for(int i = 0; i < mat1.getLength(); i++)
		{
			ret += ((mat1.getRaw(i) - mat2.getRaw(i)) * (mat1.getRaw(i) - mat2.getRaw(i)));
		}
		return Math.sqrt(ret);
	}
	
	public static Matrix matrixDeviation(Matrix... matrices)
	{
		Matrix ret = new Matrix(matrices[0].getRows(), matrices[0].getCols());
		Matrix3D matrix3d = new Matrix3D(matrices);
		for(int i = 0; i < ret.getRows(); i++)
		{
			for(int j = 0; j < ret.getCols(); j++)
			{
				double std = Stats.standardDeviation(matrix3d.getBeamAsRow(i, j).toVectorArray());
				ret.set(std, i, j);
			}
		}
		return ret;
	}
}
