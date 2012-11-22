package clustering;

import java.util.ArrayList;
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
		
		// java jdk 1.7 update 7の安全性を調べておいてください。
		
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
		List<double[][]> clusters = new ArrayList<double[][]>(k);
		// 各clusterSを空集合に設定したい、コンストラクタの定義を確認
			
		
		// 一度選ばれた要素はdataから消去されなければならない
		// 上の実装をどうするか考えていなかったので再考。
		while(true){// 終了条件を書いてください- 代表点が変化しなくなるまで
			/*
			 * Find the nearest factor from each delege.
			 */
			// この英語は怪しい、あとで調べて書きなおしておいてください
			for(int i = 0; i < k; i++){
				
			break;
		}
		
		
		
		
		return null;
	}

}
