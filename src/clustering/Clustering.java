package clustering;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public interface Clustering {
	public List<ArrayList<double[]>> Ksplit(int k, LinkedList<double[]> data);
	
}
