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
	public List<double[][]> Ksplit(int k, List<double[]> data) {// 型変更、下流を書き直してください
		// 理想的にはランダムに最初の代表点を選ぶ方がよいが、
		// 実装の手間と計算コストの点から
		// とりあえず初めのk個の点を代表点とする。
		
		
		/**
		 * Colect factors with the data.
		 */
		int n = data.size();
		int d = data.get(0).length;
		
		/**
		 * Initiation
		 * Select initial delegation.
		 */
		double[][] delegation  = new double[k][d];
		for(int i = 0; i < k; i++){
			delegation[i] = data.get(i);
		}
		
		/**
		 * Clustering and reclustering
		 */
		List<double[][]> clusters = new ArrayList<double[][]>(k);
		// 各clustersを空集合に設定したい、コンストラクタの定義を確認
			
		
		// 一度選ばれた要素はdataから消去されなければならない
		// 上の実装をどうするか考えていなかったので再考。-> Listでの実装に変更します。安全にプログラムしたい。
		double[] suggest = new double[d];
		while(true){// 終了条件を書いてください- 代表点が変化しなくなるまで
			/*
			 * Find the nearest factor from each delege.
			 */
			// この英語は怪しい、あとで調べて書きなおしておいてください
			int tmpn = data.size();
			
			// dataは参照渡し、これでいいのか？
			for(int i = 0; i < k; i++){
				for(int j = 0; j < tmpn; j++){
					suggest = data.get(j);
					
				}
				
			}	
			break;
		}
		
		
		
		/*
		 *gitどうかな
		 */
		return null;
	}
}
