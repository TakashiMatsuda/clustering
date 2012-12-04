package clustering;

import java.util.LinkedList;
import java.util.List;

public interface Clustering {
	public List<double[][]> Ksplit(int k, LinkedList<double[]> data);
}
