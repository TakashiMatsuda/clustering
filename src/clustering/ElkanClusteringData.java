package clustering;

public class ElkanClusteringData {
	public int n;
	public int d;
	public int k;
	/*
	 * initializeで無断で書き込みを受けます。
	 */
	public int[] room;
	public int[] roomsize;
	public double[] upperBorders;
	public double[][] lowerBorders;
	public byte[][] indicator;
}
