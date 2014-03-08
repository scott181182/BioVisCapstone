package BFS.biovis;

import BFS.biovis.data.EigenMath;
import BFS.biovis.data.Matrix;
import BFS.biovis.data.Matrix3D;
import BFS.biovis.data.MatrixMath;
import BFS.biovis.graphics.SimpleGraphics;
import BFS.biovis.io.AdjMatrixInput;
import BFS.biovis.io.MatrixImageIO;
import BFS.biovis.io.MatrixOutput;

@SuppressWarnings("unused")
public class Main
{

	public static void main(String[] args) 
	{	
		Timer timer = new Timer();
		timer.start();
		
		Matrix avgFile = AdjMatrixInput.loadMatrixFrom("data/output/averageMatrix.txt");
		Matrix3D matrix3d = new Matrix3D(167, 167, 20);
		Matrix[] matrices = new Matrix[5 * 4];
		Matrix[] diffMatrices = new Matrix[5 * 4];
		Matrix[] patientAvg = new Matrix[5];
		for(int i = 1; i <= 5; i++)
		{
			for(int j = 1; j <= 4; j++)
			{
				Matrix mat = AdjMatrixInput.loadMatrix(i, j);
				matrices[((i - 1) * 4) + (j - 1)] = mat;
				diffMatrices[((i - 1) * 4) + (j - 1)] = MatrixMath.diffMatrix(avgFile, mat);
				matrix3d.setFace(mat, ((i - 1) * 4) + (j - 1), Matrix3D.FRONT);
			}
			patientAvg[i-1] = MatrixMath.meanMatrix(matrices[((i-1)*4) + 0], matrices[((i-1)*4) + 1], matrices[((i-1)*4) + 2], matrices[((i-1)*4) + 3]);
		}
		
		Matrix totalDeviation = new Matrix(167, 167);
		Matrix[] patientDeviation = new Matrix[5];
		for(int i = 0; i < 5; i++)
		{
			patientDeviation[i] = new Matrix(167, 167);
			for(int row = 0; row < 167; row++)
			{
				for(int col = 0; col < 167; col++)
				{
					double diff = 0;
					diff += Math.abs(diffMatrices[(i * 4) + 0].get(row, col));
					diff += Math.abs(diffMatrices[(i * 4) + 1].get(row, col));
					diff += Math.abs(diffMatrices[(i * 4) + 2].get(row, col));
					diff += Math.abs(diffMatrices[(i * 4) + 3].get(row, col));
					patientDeviation[i].set(diff, row, col);
					totalDeviation.set(totalDeviation.get(row, col) + diff, row, col);
				}
			}
			//SimpleGraphics.displayMatrixImageWithControls(patientDeviation[i], 4, false, patientDeviation[i].min(), patientDeviation[i].max());
		}
		SimpleGraphics.displayMatrixImageWithControls(totalDeviation, 4, true, totalDeviation.min(), totalDeviation.max());
		
		//EigenMath.getEigenMatrix(matrices);
		//SimpleGraphics.displayMatrixImageWithControls(avgFile, 4, true);
		
		timer.stop();
		System.out.println("[Main] Took " + timer.getTime(Timer.MILLISECONDS));
	}

	public static class Timer
	{
		public static final byte MILLISECONDS = 0;
		public static final byte SECONDS = 1;
		
		private long startTime, endTime;
		private long time;
		
		public Timer()
		{
			
		}
		
		public void start()
		{
			startTime = System.currentTimeMillis();
		}
		public void stop()
		{
			endTime = System.currentTimeMillis();
			time = (endTime - startTime);
		}
		
		public String getTime(byte timeFormat)
		{
			String str = "";
			if(timeFormat == MILLISECONDS) { str = time + " milliseconds"; }
			else if(timeFormat == SECONDS) { str = String.valueOf((float)time / 1000f) + " seconds"; }
			return str;
		}
		public void printTime(byte timeFormat)
		{
			String str = getTime(timeFormat);
			System.out.println("[Timer] Time = " + str);
		}
	}
}
