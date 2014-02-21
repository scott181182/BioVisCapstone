package BFS.biovis.data;

public class Matrix3D
{
	/** The double array that contains all the data in the Matrix object */
	private double[] rawData;
	private int width, height, depth;
	
	public Matrix3D(int x, int y, int z)
	{
		if(x < 1) { x = 1; } width = x;
		if(y < 1) { y = 1; } height = y;
		if(z < 1) { z = 1; } depth = z;
		
		rawData = new double[x * y * z];
	}
	
	public int getWidth() { return width; }
	public int getHeight() { return height; }
	public int getDepth() { return depth; }
	public int getArea() { return width * height; }
	public int getVolume() { return width * height * depth; }
	
	/** Returns the value in {@link #rawData} at the specified index<p>
	 * Not recommended for use outside this class and its children */
	public double getRaw(int index) { return rawData[index]; }
	/** Sets the index in {@link #rawData} to the specified value<p>
	 * Not recommended for use outside this class and its children */
	public void setRaw(double value, int index) { rawData[index] = value; }
	
	/** 
	 * Returns the value in the Matrix at the specified x, y, and z coordinate 
	 * @param x Row #
	 * @param y Column #
	 * @param z Depth # 
	 */
	public double get(int x, int y, int z)
	{
		return rawData[x + (y * width) + (z * width * height)];
	}
	/** 
	 * Sets the double at the specified x, y, and z coordinate to {@code value}
	 * @param value	New double to set index to
	 * @param x Row #
	 * @param y Column #
	 * @param z Depth # 
	 */
	public void set(double value, int x, int y, int z)
	{
		rawData[x + (y * width) + (z * width * height)] = value;
	}
	/**
	 * Returns a 2-D sub-matrix at the specified z-index
	 * @param index The z-coordinate of the sub-matrix
	 */
	public Matrix getFace(int index)
	{
		if(index >= this.depth) { throw new IndexOutOfBoundsException("No such z-index in this Matrix : " + index); }
		Matrix ret = new Matrix(this.width, this.height);
		for(int i = 0; i < this.getArea(); i++)
		{
			ret.setRaw(this.getRaw(i + (this.getArea() * index)), i);
		}
		return ret;
	}
	/**
	 * Sets the specified z-index sub-matrix to the passed in Matrix
	 * @param mat Passed in Matrix
	 * @param index The z-coordinate of the sub-matrix to set to mat
	 */
	public void setFace(Matrix mat, int index)
	{
		if(index >= this.depth) { throw new IndexOutOfBoundsException("No such z-index in this Matrix : " + index); }
		if(mat.getCols() != this.width) { throw new IllegalArgumentException("Current Matrix width and passed in Matrix width are not equal : " + this.width + " != " +  mat.getCols()); }
		if(mat.getRows() != this.height) { throw new IllegalArgumentException("Current Matrix height and passed in Matrix height are not equal : " + this.height + " != " +  mat.getRows()); }
		for(int i = 0; i < this.getArea(); i++)
		{
			this.setRaw(mat.getRaw(i), i + (index * this.getArea()));
		}
	}
	
	public Matrix3D addMatrixPage(Matrix addend)
	{
		Matrix3D ret = new Matrix3D(this.width, this.height, this.depth + 1);
		for(int i = 0; i < ret.depth - 2; i++) { ret.setFace(this.getFace(i), i); }
		ret.setFace(addend, ret.depth - 1);
		return ret;
	}
	
	public Matrix getRow(int yIndex, int  zIndex)
	{
		Matrix ret = new Matrix(this.width, 1);
		for(int i = 0; i < this.width; i++)
		{
			ret.setRaw(this.getRaw(i + (yIndex * this.height + zIndex * this.depth)), i);
		}
		return ret;
	}
	public Matrix getColumn(int xIndex, int  zIndex)
	{
		Matrix ret = new Matrix(1, this.height);
		for(int i = 0; i < this.height; i++)
		{
			ret.setRaw(this.getRaw(xIndex * this.width + i * this.height + zIndex * this.depth), i);
		}
		return ret;
	}
	
	@Override public String toString()
	{
		return "Matrix[" + width + "x" + height + "x" + depth + "]";
	}
	public String toDataString()
	{
		String ret = "Matrix[";
		int index = 0;
		for(int z = 0; z < depth; z++)
		{
			for(int y = 0; y < height; y++)
			{
				for(int x = 0; x < width; x++)
				{
					if(x != 0) { ret += ", "; }
					ret += this.getRaw(index);
					index++;
				}
				ret += ";  ";
			}
			ret += "]   ";
		}
		return ret;
	}
}