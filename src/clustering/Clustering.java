package clustering;

import java.util.ArrayList;

/**
 * 
 * @author tks
 *
 */
public interface Clustering {
	/**
	 * 
	 * @param k
	 * @param data
	 * @return
	 */
	public byte[][] Ksplit(int k, ArrayList<double[]> data);
	
}
