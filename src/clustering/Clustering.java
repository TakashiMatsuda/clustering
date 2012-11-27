package clustering;

import java.util.List;

public interface Clustering {
	public List<double[][]> Ksplit(int k, List<double[]> data);
}
