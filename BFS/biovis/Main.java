package BFS.biovis;

import BFS.biovis.data.*;
import BFS.biovis.graphics.SimpleGraphics;
import BFS.biovis.io.MatrixInput;
import BFS.biovis.io.SubjectMatrixInput;
import BFS.biovis.io.MatrixImageIO;
import BFS.biovis.io.MatrixOutput;

@SuppressWarnings("unused")
public class Main
{

	public static void main(String[] args) 
	{	
		Timer timer = new Timer();
		timer.start();
		
		Matrix avgFile = SubjectMatrixInput.loadAdjMatrixFrom("data/output/averageMatrix.txt");
		Matrix totalDeviation = SubjectMatrixInput.loadAdjMatrixFrom("data/output/Deviation/totalSD.txt");
		Matrix totalPatientDeviation = SubjectMatrixInput.loadAdjMatrixFrom("data/output/Deviation/patientSD-SD.txt");
		Matrix averageDeviation = SubjectMatrixInput.loadAdjMatrixFrom("data/output/Deviation/averageSD.txt");
		Matrix[] patientDeviation = new Matrix[5];
		for(int i = 0; i < 5; i++) { patientDeviation[i] = SubjectMatrixInput.loadAdjMatrixFrom("data/output/Deviation/patient#" + (i+1) + "-SD.txt"); }
		
		Matrix3D matrix3d = new Matrix3D(167, 167, 20);
		Matrix[] matrices = new Matrix[5 * 4];
		Matrix[] diffMatrices = new Matrix[5 * 4];
		Matrix[] timeSeries = new Matrix[20];
		Matrix[] patientAvg = new Matrix[5];
		for(int i = 1; i <= 5; i++)
		{
			for(int j = 1; j <= 4; j++)
			{
				Matrix mat = SubjectMatrixInput.loadAdjMatrix(i, j);
				matrices[((i - 1) * 4) + (j - 1)] = mat;
				diffMatrices[((i - 1) * 4) + (j - 1)] = MatrixMath.diffMatrix(avgFile, mat);
				matrix3d.setFace(mat, ((i - 1) * 4) + (j - 1), Matrix3D.FRONT);
				timeSeries[((i - 1) * 4) + (j - 1)] = SubjectMatrixInput.loadTimeMatrix(i, j);
			}
			patientAvg[i-1] = MatrixMath.meanMatrix(matrices[((i-1)*4) + 0], matrices[((i-1)*4) + 1], matrices[((i-1)*4) + 2], matrices[((i-1)*4) + 3]);
		}
		
		
		
		Matrix[] timeSD = new Matrix[20];
		Matrix[] timeWeight = new Matrix[20];
		for(int i = 0; i < timeSeries.length; i++)
		{
			timeSD[i] = new Matrix(167, 1);
			for(int j = 0; j < timeSD[i].getRows(); j++) { timeSD[i].set(Stats.standardDeviation(timeSeries[i].getRow(j).toVectorArray()), j, 0); }
			timeWeight[i] = new Matrix(167, 167);
			for(int row = 0; row < timeWeight[0].getRows(); row++)
			{
				for(int col = 0; col < timeWeight[0].getCols(); col++)
				{
					timeWeight[i].set(timeSD[i].getRaw(row) * timeSD[i].getRaw(col), row, col);
				}
			}
		}
		Matrix[] matricesTW = new Matrix[matrices.length];
		for(int i = 0; i < matricesTW.length; i++)
		{
			matricesTW[i] =  MatrixMath.scalarProduct(1000, matrices[i]);
			matricesTW[i] = MatrixMath.valueDivide(matricesTW[i], timeWeight[0]);
		}
		/*
		SimpleGraphics.displayMatrixImageWithControls(matricesTW[0], 4, false);
		SimpleGraphics.displayMatrixImageWithControls(matricesTW[1], 4, false);
		SimpleGraphics.displayMatrixImageWithControls(matricesTW[2], 4, false);
		SimpleGraphics.displayMatrixImageWithControls(matricesTW[3], 4, false);
		SimpleGraphics.displayMatrixImageWithControls(matricesTW[4], 4, false);
		SimpleGraphics.displayMatrixImageWithControls(matricesTW[5], 4, false);
		*/
		for(int i = 1; i < matricesTW.length; i++)
		{
			//System.out.print("[" + i + "] " + (int)EigenMath.testMatrix(matricesTW[i], matricesTW[0]) + "\t");
			//Matrix avgTemp = MatrixMath.meanMatrix(matricesTW[i], matricesTW[0]);
			//System.out.println((int)EigenMath.testMatrix(matricesTW[i], avgTemp));

			//System.out.print("[" + i + "] " + (int)EigenMath.testMatrix(matrices[i], matrices[0]) + "\t");
			double dist = 0;
			Matrix avgTemp = MatrixMath.meanMatrix(matricesTW[i], matricesTW[0]);
			Matrix diff = MatrixMath.diffMatrix(avgTemp, matricesTW[i]);
			double diffVar = Stats.variance(diff.toVectorArray());
			double diffMed = Stats.median(diff.toVectorArray());
			double lower = Stats.firstQuartile(diff.toVectorArray());
			double upper = Stats.thirdQuartile(diff.toVectorArray());
			for(int j = 0; j < diff.getLength(); j++)
			{
				if(diff.getRaw(j) < upper && diff.getRaw(j) > lower) { diff.setRaw(0, j); }
			}
			dist = diff.sum();
			//SimpleGraphics.displayMatrixImageWithControls(diff, 4, false);
			//for(int j = 0;j < avgTemp.getCols(); j++) { dist += MatrixMath.diffMatrix(matricesTW[i].getRow(j), matricesTW[0].getRow(j)).sum(); }
			System.out.println((int)EigenMath.testMatrix(matricesTW[i], matricesTW[0]) + " vs. " + dist);
		}
		
		
		Matrix matWT = MatrixMath.valueDivide(matrices[0], totalDeviation);
		for(int i = 0; i < matrices.length; i++)
		{
			Matrix matW = MatrixMath.valueDivide(matrices[i], totalDeviation);
			double testD = EigenMath.testMatrix(matW, matWT);
			double testW = EigenMath.testMatrix(matW, matrices[0]);
			double testWD = EigenMath.testMatrix(matrices[i], matWT);
			double test = EigenMath.testMatrix(matrices[i], matrices[0]);
			//System.out.println((int)testD + " vs. " + (int)testWD + " vs. " + (int)testW + " vs. " + (int)test);
		}
		
		
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
