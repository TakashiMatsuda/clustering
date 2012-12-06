package clustering;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * This class includes some methods for Lloyd way to k-means clustering.
 * @author tks
 *	だいたい書き終わった
 */
public class LloydClustering implements Clustering {
	
	int n;
	int d;
	int k;

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
	
	/**
	 * a
	 * @param a
	 * @param b
	 * @return aとbの間の距離和を計算し、閾値以下だったらfalse, そうでなければtrue
	 */
	private boolean judgeDelegation(double[][] a, double[][] b, double threshold){
		double sum = 0;
		double sumsum;
		for (int i = 0; i < a.length; i++){
			sum += distance(a[i], b[i]);
		}
		if (sum > threshold)
			return true;
		else
			return false;
	}
	
	/**
	 * 
	 * @param clusters 評価するクラスタ集合
	 * @return 新しい代表点集合
	 */
	private double[][] refresh(List<ArrayList<double[]>> clusters){
		double[][] fruit = new double[k][d];
		double sum;
		// 総距離
		double dist;
		int l;// メモリより計算量を優先
		for (int i = 0; i < k; i++){
			dist = 0;
			for(int j = 0; j < d; j++){
				/*
				 * 誤差二乗平均を計算
				 */
				sum = 0;
				l = clusters.get(i).get(j).length;
				for(int u = 0; u < l; u++){
					sum += clusters.get(i).get(j)[u];
				}
				
				
			}
			
		}
		return fruit;
	}
	
	
	
	@Override
	public List<ArrayList<double[]>> Ksplit(int khiki, LinkedList<double[]> dataSpace) {// 型変更、下流を書き直してください
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
		this.n = dataSpace.size();
		this.d = dataSpace.get(0).length;
		this.k = khiki;
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
		List<ArrayList<double[]>> clusters = new LinkedList<ArrayList<double[]>>();
		// 一度選ばれた要素はdataから消去されなければならない
		/*
		 * 各クラスタの初期化、最初は代表点を一つ入れる
		 */
		for(int i = 0; i < k; i++){
			ArrayList<double[]> w = new ArrayList<double[]>();
			w.add(delegation[i]);
			clusters.add(w);
		}
		
		double[] suggest = new double[d];
		double minDistance = 1.0 / 0.0;
		double[][] newDelegation = new double[k][d];
		int r = 0;
		
		// 今のところ無限ループでした。
		// 各メソッドを整備したい。
		while(true){
			/*
			 * Find each the nearest factor from each deletion.
			 */
			int tmpn = dataSpace.size();
			for(int i = 0; i < k; i++){
				for(int j = 0; j < tmpn; j++){
					suggest = dataSpace.get(j);
					if (distance(suggest, delegation[i]) < minDistance){
						minDistance = distance(suggest, delegation[i]);
						r = j;
					}
				}
				// ArrayListのsetで起こる挙動を調べて下さい
				clusters.get(i).add(dataSpace.get(r));// この追加法に変わるものを確認して下さい
				// 次はこれの挙動を確認します。
				
			}
			
			/*
			 * 代表点を再構成
			 */
			newDelegation = refresh(clusters);
			
			
			/*
			 * 代表点が変化しているかを確認、変化していなかったら返す
			 */
			if (judgeDelegation(delegation, newDelegation, 0.01)){
				// 何もしない
			}
			else{
				break;
			}
		}
		return clusters;
	}
	
	LloydClustering(){
		
	}
}
