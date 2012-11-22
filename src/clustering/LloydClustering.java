package clustering;

import java.util.List;

/**
 * This class includes some methods for Lloyd way to k-means clustering.
 * @author tks
 *
 */
public class LloydClustering implements Clustering {

	@Override
	public List<double[][]> Ksplit(int k, double[][] data) {
		// 理想的にはランダムに最初の代表点を選ぶ方がよいが、
		// 実装の手間と計算コストの点から
		// とりあえず初めのk個の点を代表点とする。
		
		/**
		 * Colect factors with the data.
		 */
		int n = data.length;
		int d = data[0].length;
		
		/**
		 * Initiation
		 * Select initial delegation.
		 */
		double[][] delegation  = new double[k][d];
		for(int i = 0; i < k; i++){
			delegation[i] = data[i];
		}
		
		/**
		 * Clustering and reclustering
		 */
		while(true){// 終了条件を書いてください
			
			
			
			
		}
		
		
		
		
		return null;
	}

}
