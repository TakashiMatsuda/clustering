package clustering;

import java.util.ArrayList;
import java.util.List;

/**
 * This class includes some methods for Lloyd way to k-means clustering.
 * @author tks
 *
 */
public class LloydClustering implements Clustering {

	/**
	 * xとyの次元数が一致していることを確認して下さい!
	 * 
	 * @param x
	 * @param y
	 * @return Euclid distance between x and y
	 */
	private double distance(double[] x, double[] y){
		double sum = 0;
		/*
		 * イテレータを用いて書いてみたいですね
		 * x, yどちらもに必要なパラメータだから使えないのでは？
		 */
		for (int i = 0; i < x.length; i++){
			sum += Math.pow((x[i] - y[i]), 2.0);
		}
		return Math.sqrt(sum);
	}
	
	
	@Override
	public List<double[][]> Ksplit(int k, List<double[]> dataSpace) {// 型変更、下流を書き直してください
		/**
		 * data: データ集合
		 * k: 目標クラスタリングの数
		 */
		// 実装の手間と計算コストの点から
		// とりあえず初めのk個の点を代表点とする。
		
		
		/**
		 * Colect factors with the data.
		 * n: dataの要素数
		 * d: dataの次元数
		 */
		int n = dataSpace.size();
		int d = dataSpace.get(0).length;
		
		/**
		 * Initiation
		 * Select initial delegation.
		 */
		double[][] delegation  = new double[k][d];
		for(int i = 0; i < k; i++){
			delegation[i] = dataSpace.get(i);
		}
		
		
		/*
		 * Clustering and reclustering
		 * clustersを空集合として初期化
		 */
		List<ArrayList<double[]>> clusters = new ArrayList<ArrayList<double[]>>(k);
		
		// 一度選ばれた要素はdataから消去されなければならない
		// 上の実装をどうするか考えていなかったので再考。-> Listでの実装に変更します。安全にプログラムしたい。
		double[] suggest = new double[d];
		double minDistance = 1.0 / 0.0;
		while(true){// 終了条件を書いてください- 代表点が変化しなくなるまで
			/*
			 * Find each the nearest factor from each deletion.
			 */
			int tmpn = dataSpace.size();
			for(int i = 0; i < k; i++){
				for(int j = 0; j < tmpn; j++){
					suggest = dataSpace.get(j);
					if (distance(suggest, delegation[i]) < minDistance){
						minDistance = distance(suggest, delegation[i]);
					}
				}
			}
			
			/*
			 * 代表点が変化しているかを確認、変化していなかったら返す
			 */
			break;
		}
		return null;
	}
	
	LloydClustering(){
		
	}
}
