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
	public boolean[] rr;
	
	
	/**
	 * 
	 * @param n
	 * @param d
	 * @param k
	 */
	public ElkanClusteringData(int n, int d, int k){
		this.n = n;
		this.d = d;
		this.k = k;
		this.room = new int[n];
		this.roomsize = new int[k];
		this.rr = new boolean[n];
	}
	
}
