package BFS.biovis.data;

import java.util.Arrays;

public class Matrix
{
	/** The double array that contains all the data in the Matrix object */
	private double[] rawData;
	private int rows, cols;
	
	public Matrix(int rows, int cols)
	{
		if(rows < 1) { rows = 1; } this.rows = rows;
		if(cols < 1) { cols = 1; } this.cols = cols;
		
		rawData = new double[rows * cols];
	}
	public Matrix(int rows, int cols, double... values)
	{
		this(rows, cols);
		for(int i = 0; i < values.length; i++) { this.setRaw(values[i], i); }
	}
	public Matrix(double[][] matrixArray)
	{
		rows = matrixArray.length;
		cols = matrixArray[0].length;
		rawData = new double[rows * cols];
		int index = 0;
		for(int i = 0; i < rows; i ++)
		{
			for(int j = 0; j < cols; j++)
			{
				rawData[index] = matrixArray[i][j];
				index++;
			}
		}
	}
	
	public int getRows() { return rows; }
	public int getCols() { return cols; }
	public int getLength() { return rows * cols; }
	
	public boolean isSquare() { return rows == cols; }
	public boolean isSymmetric()
	{
		for(int i = 0; i < rows; i++)
		{
			for(int j = 0; j < cols; j++)
			{
				if(get(i, j) != get(j, i)) { return false; }
			}
		}
		return true;
	}
	public boolean isTriangular()
	{
		boolean isUpper = true, isLower = true;
		for(int i = 0; i < rows; i++)
		{
			for(int j = 0; j < cols; j++)
			{
				if((i > j) && get(i, j) != 0) { isLower = false; }
				if((i < j) && get(i, j) != 0) { isUpper = false; }
			}
		}
		return isUpper || isLower;
	}
	
	/** Returns the value in {@link #rawData} at the specified index<p>
	 * Not recommended for use outside this class and its children */
	public double getRaw(int index) { return rawData[index]; }
	/** Sets the index in {@link #rawData} to the specified value<p>
	 * Not recommended for use outside this class and its children */
	public void setRaw(double value, int index) { rawData[index] = value; }
	
	/** 
	 * Returns the value in the Matrix at the specified x, y, and z coordinate 
	 * @param row Row #
	 * @param col Column #
	 */
	public double get(int row, int col)
	{
		return rawData[col + (row * cols)];
	}
	/** 
	 * Sets the double at the specified x, y, and z coordinate to {@code value}
	 * @param value	New double to set index to
	 * @param row Row #
	 * @param col Column # 
	 */
	public void set(double value, int row, int col)
	{
		rawData[col + (row * cols)] = value;
	}
	
	public Matrix getRow(int rowNum)
	{
		Matrix ret = new Matrix(1, this.cols);
		for(int i = 0; i < ret.cols; i++)
		{
			ret.setRaw(this.get(rowNum, i), i);
		}
		return ret;
	}
	public void setRow(Matrix row, int rowNum)
	{
		for(int i = 0; i < row.cols; i++) { this.set(row.getRaw(i), rowNum, i); }
	}
	public Matrix getColumn(int colNum)
	{
		Matrix ret = new Matrix(this.rows, 1);
		for(int i = 0; i < ret.rows; i++)
		{
			ret.setRaw(this.get(i, colNum), i);
		}
		return ret;
	}
	public void setColumn(Matrix col, int colNum)
	{
		for(int i = 0; i < col.rows; i++) { this.set(col.getRaw(i), i, colNum); }
	}
	
	public void makeTriangular()
	{
		for(int i = 0; i < rows; i++)
		{
			for(int j = 0; j < cols; j++)
			{
				if(i < j) { set(0, i, j); }
			}
		}
	}
	public void normalize()
	{
		double max = this.max();
		for(int i = 0; i < this.getLength(); i++) { this.setRaw(this.getRaw(i) / max, i); }
	}
	
	public Matrix transpose()
	{
		Matrix ret = new Matrix(this.cols, this.rows);
		for(int i = 0; i < this.rows; i++)
		{
			for(int j = 0; j < this.cols; j++)
			{
				ret.set(this.get(i, j), j, i);
			}
		}
		return ret;
	}

	public double determinant()
	{
		if(!isSquare()) { throw new IllegalArgumentException("Matrix must be square"); }
		double ret = 0;
		//System.out.println("Finding determinant...");
		
		if(rows == 2) { ret = getRaw(0) * getRaw(3) - getRaw(1) * getRaw(2); return ret; }
		if(isTriangular())
		{
			ret = 1;
			for(int i = 0; i < rows; i++)
			{
				ret *= get(i, i);
			}
			return ret;
		}
		if(rows <= 5)
		{
			for(int i = 0; i < cols; i++)
			{
				Matrix sub = new Matrix(rows - 1, cols - 1);
				int index = 0;
				for(int row = 0; row < rows; row++)
				{
					for(int col = 0; col < cols; col++)
					{
						if(row == 1 || col == i) { continue; }
						sub.setRaw(this.get(row, col), index);
						index++;
					}
				}
				index = 0;
				
				ret += sub.determinant() * this.get(1, i) * (i % 2 == 0 ? -1 : 1);
				//if(rows > 11) { System.out.println("[Rows:" + rows + "]\t[Col:" + i + "]"); }
			}
			return ret;
		}
		return new LUDecomposition(this).det();
	}
	
	public double trace()
	{
		if(!isSquare()) { throw new IllegalArgumentException("Matrix must be square"); }
		double ret = 0;
		for(int i = 0; i < rows; i++)
		{
			ret += get(i, i);
		}
		return ret;
	}
	
	public double magnitude()
	{
		double ret = 0;
		for(int i = 0; i < this.getLength(); i++)
		{
			ret += this.getRaw(i) * this.getRaw(i);
		}
		return Math.sqrt(ret);
	}
	
	public double sum()
	{
		double ret = 0;
		for(int i = 0; i < this.getLength(); i++) { ret += rawData[i]; }
		return ret;
	}
	public double mean()
	{
		double avg = this.sum();
		return avg / this.getLength();
	}
	public double min()
	{
		double ret = 0;
		for(int i = 0; i < this.getLength(); i++) { ret = rawData[i] < ret ? rawData[i] : ret; }
		return ret;
	}
	public double max()
	{
		double ret = 0;
		for(int i = 0; i < this.getLength(); i++) { ret = rawData[i] > ret ? rawData[i] : ret; }
		return ret;
	}
	
	public Matrix submatrix(int startRow, int startCol, int endRow, int endCol)
	{
		Matrix ret = new Matrix(endRow - startRow, endCol - startCol);
		for(int i = 0; i < ret.cols; i++)
		{
			for(int j = 0; j < ret.rows; j++)
			{
				ret.set(this.get(startRow + j, startCol + i), j, i);
			}
		}
		return ret;
	}
	public Matrix submatrix(int[] rows, int startCol, int endCol)
	{
		Matrix ret = new Matrix(rows.length, endCol - startCol);
		int index = 0;
		for(int i = 0; i < ret.cols; i++)
		{
			for(int row : rows)
			{
				ret.set(this.get(row, startCol + i), index, i);
				index++;
			}
		}
		return ret;
	}
	public Matrix submatrix(int startRow, int endRow, int[] cols)
	{
		Matrix ret = new Matrix(startRow - endRow, cols.length);
		int index = 0;
		for(int col : cols)
		{
			for(int j = 0; j < ret.rows; j++)
			{
				ret.set(this.get(startRow + j, col), j, index);
				index++;
			}
		}
		return ret;
	}
	public Matrix submatrix(int[] rows, int[] cols)
	{
		Matrix ret = new Matrix(rows.length, cols.length);
		int indexi = 0, indexj = 0;
		for(int row : rows)
		{
			for(int col : cols)
			{
				ret.set(this.get(row, col), indexi, indexj);
				indexi++;
				indexj++;
			}
		}
		return ret;
	}
	
	
	
	public Matrix toRowVector()
	{
		Matrix ret = new Matrix(1, this.rows * this.cols, this.rawData);
		return ret;
	}
	
	public Matrix toColVector()
	{
		Matrix ret = new Matrix(this.rows * this.cols, 1, this.rawData);
		return ret;
	}
	
	@Override public String toString()
	{
		return "Matrix[" + rows + "x" + cols + "]";
	}
	public String toDataString()
	{
		String ret = "Matrix:";
		for(int y = 0; y < rows; y++)
		{
			String temp = y == 0 ? "[" : "       [";
			for(int x = 0; x < cols; x++)
			{
				if(x != 0) { temp += ", "; }
				temp += this.get(y, x);
			}
			ret += String.format("%s%n", temp + "]");
		}
		return ret;
	}
	public double[][] toArray()
	{
		double[][] ret = new double[rows][cols];
		for(int i = 0; i < rows; i++)
		{
			for(int j = 0; j < cols; j++)
			{
				ret[i][j] = this.get(i, j);
			}
		}
		return ret;
	}
	public double[] toVectorArray() { return Arrays.copyOf(this.rawData, this.rawData.length); }
	public int[] toVectorArrayInt() 
	{ 
		int[] ret = new int[rawData.length];
		for(int i = 0; i < rawData.length; i++) { ret[i] = (int)rawData[i]; }
		return ret;
	}
	
	@Override public Object clone()
	{
		return new Matrix(rows, cols, rawData);
	}
	@Override public boolean equals(Object obj)
	{
		Matrix mat = (Matrix)obj;
		
		if(this.rows != mat.rows) { return false; }
		if(this.cols != mat.cols) { return false; }
		for(int i = 0; i < this.getLength(); i++)
		{
			if(this.rawData[i] != mat.rawData[i]) { return false; }
		}
		return true;
	}
	public boolean equalsMarginal(Object obj, double margin)
	{
		Matrix mat = (Matrix)obj;
		if(this.rows != mat.rows) { return false; }
		if(this.cols != mat.cols) { return false; }
		for(int i = 0; i < this.getLength(); i++)
		{
			double diff = this.rawData[i] - mat.rawData[i];
			if((diff > margin) || (diff < -margin)) { return false; }
		}
		return true;
	}
}
