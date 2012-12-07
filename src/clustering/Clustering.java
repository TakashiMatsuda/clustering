package clustering;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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
	public byte[][] Ksplit(int k, LinkedList<double[]> data);
	
}
