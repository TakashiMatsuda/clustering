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
	public List<ArrayList<double[]>> Ksplit(int k, LinkedList<double[]> data);
	
}
