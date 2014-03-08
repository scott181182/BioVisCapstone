package BFS.biovis.data;

public class Matrix3D
{
	public static final int ROW = 1, TOP = 4;
	public static final int COLUMN = 2, SIDE = 5;
	public static final int BEAM = 3, FRONT = 6;
	
	/** The double array that contains all the data in the Matrix object */
	private Matrix[] pages;
	private int rows, cols, beams;
	
	public Matrix3D(int rows, int cols, int beams)
	{
		if(rows < 1) { rows = 1; } this.rows = rows;
		if(cols < 1) { cols = 1; } this.cols = cols;
		if(beams < 1) { beams = 1; } this.beams = beams;
		
		pages = new Matrix[beams];
		for(int i = 0; i < pages.length; i++) { pages[i] = new Matrix(rows, cols); }
	}
	
	public int getRows() { return rows; }
	public int getCols() { return cols; }
	public int getBeams() { return beams; }
	public int getAreaFront() { return rows * cols; }
	public int getAreaSide() { return rows * beams; }
	public int getAreaTop() { return cols * beams; }
	public int getVolume() { return rows * cols * beams; }

//	/** Returns the value in {@link #rawData} at the specified index<p>
//	 * Not recommended for use outside this class and its children */
//	public double getRaw(int index) { return rawData[index]; }
//	/** Sets the index in {@link #rawData} to the specified value<p>
//	 * Not recommended for use outside this class and its children */
//	public void setRaw(double value, int index) { rawData[index] = value; }
	
	/** 
	 * Returns the value in the Matrix at the specified x, y, and z coordinate 
	 * @param row Row #
	 * @param col Column #
	 * @param beam Beam # 
	 */
	public double get(int row, int col, int beam)
	{
		return pages[beam].get(row, col);
	}
	/** 
	 * Sets the double at the specified x, y, and z coordinate to {@code value}
	 * @param value	New double to set index to 
	 * @param row Row #
	 * @param col Column #
	 * @param beam Beam # 
	 */
	public void set(double value, int row, int col, int beam)
	{
		pages[beam].set(value, row, col);
	}
	/**
	 * Returns a 2-D sub-matrix at the specified z-index
	 * @param index The z-coordinate of the sub-matrix
	 * @param face The face index, either FRONT, SIDE, or TOP 
	 */
	public Matrix getFace(int index, int face)
	{
		int r = face == TOP ? this.beams : this.rows;
		int c = face == SIDE ? this.beams : this.cols;
		Matrix ret = new Matrix(r, c);
		for(int i = 0; i < r; i++)
		{
			for(int j = 0; j < c; j++)
			{
				double value = this.get(face == TOP ? index : i, face == SIDE ? index : j, face == FRONT ? index : face == SIDE ? j : i);
				ret.set(value, i, j);
			}
		}
		return ret;
	}
	/**
	 * Sets the specified z-index sub-matrix to the passed in Matrix
	 * @param mat Passed in Matrix
	 * @param index The z-coordinate of the sub-matrix to set to mat
	 */
	public void setFace(Matrix mat, int index, int face)
	{
		int r = face == TOP ? this.beams : this.rows;
		int c = face == SIDE ? this.beams : this.cols;
		for(int i = 0; i < r; i++)
		{
			for(int j = 0; j < c; j++)
			{
				this.set(mat.get(i, j), face == TOP ? index : i, face == SIDE ? index : j, face == FRONT ? index : face == SIDE ? j : i);
			}
		}
	}
	
	public Matrix3D addPage(Matrix addend)
	{
		Matrix3D ret = new Matrix3D(this.rows, this.cols, this.beams + 1);
		for(int i = 0; i < ret.beams - 2; i++) { ret.setFace(this.getFace(i, Matrix3D.FRONT), i, Matrix3D.FRONT); }
		ret.setFace(addend, ret.beams - 1, Matrix3D.FRONT);
		return ret;
	}
	
	public Matrix getRow(int row, int  beam)
	{
		Matrix ret = new Matrix(this.cols, 1);
		for(int i = 0; i < ret.getCols(); i++)
		{
			ret.setRaw(this.get(row, i, beam), i);
		}
		return ret;
	}
	public Matrix getColumn(int col, int  beam)
	{
		Matrix ret = new Matrix(1, this.rows);
		for(int i = 0; i < ret.getRows(); i++)
		{
			ret.setRaw(this.get(i, col, beam), i);
		}
		return ret;
	}
	
	@Override public String toString()
	{
		return "Matrix[" + rows + "x" + cols + "x" + beams + "]";
	}
}