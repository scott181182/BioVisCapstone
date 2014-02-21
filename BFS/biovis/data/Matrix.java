package BFS.biovis.data;

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
	
	public int getRows() { return rows; }
	public int getCols() { return cols; }
	public int getLength() { return rows * cols; }
	
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
	public Matrix getColumn(int colNum)
	{
		Matrix ret = new Matrix(this.rows, 1);
		for(int i = 0; i < ret.rows; i++)
		{
			ret.setRaw(this.get(i, colNum), i);
		}
		return ret;
	}
	
	public Matrix transpose()
	{
		Matrix ret = new Matrix(this.rows, this.cols);
		for(int y = 0; y < this.rows; y++)
		{
			for(int x = 0; x < this.cols; x++)
			{
				ret.set(this.get(x, y), y, x);
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
		String ret = "Matrix[";
		for(int y = 0; y < rows; y++)
		{
			for(int x = 0; x < cols; x++)
			{
				if(x != 0) { ret += ", "; }
				ret += this.get(y, x);
			}
			ret += ";   ";
		}
		return ret += "]";
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
}
