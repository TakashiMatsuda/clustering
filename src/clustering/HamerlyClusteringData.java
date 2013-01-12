package clustering;


/**
 * カプセル化されていません。
 * @author takashi
 *
 */
public class HamerlyClusteringData {
	public int n;
	public int d;
	public int k;
	public byte[][] indicator;
	/*
	 * initializeで無断で書き込みを受けます。
	 */
	public int[] room;

	public HamerlyClusteringData() {
	}
}