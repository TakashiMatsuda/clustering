package clustering;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


/**
 * This class includes some methods for Lloyd way to k-means clustering.
 * @author tks
 *	
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
	
	@SuppressWarnings("rawtypes")
	private double[][] refresh(List<ArrayList<double[]>> clusters){
		double[][] fruit = new double[k][d];
		double sum;
		double dist;
		int l;
		for (int i = 0; i < k; i++){
			dist = 0;
			for(int j = 0; j < d; j++){
				/*
				 * 誤差二乗平均を計算
				 */
				sum = 0;
				l = clusters.get(i).size();
				for(int u = 0; u < l; u++){
					sum += ((double[]) ((ArrayList) (clusters.get(i))).get(u))[j];
				}
				fruit[i][j] = sum;
			}
			dist = distance(fruit[i]);
			for(int j = 0; j < d; j++){
				fruit[i][j] = fruit[i][j] / dist;
			}
		}
		return fruit;
	}
	
	
	/**
	 * オーバーライドしてみました
	 * ベクトルのノルム
	 * @param tmp
	 * @return absolute value
	 */
	private double distance(double[] x) {
		double sum = 0;
		for (int i = 0; i < x.length; i++){
			sum += Math.pow(x[i], 2.0);
		}
		return Math.sqrt(sum);
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	
	public List<ArrayList<double[]>> Ksplit(int khiki, LinkedList<double[]> dataSpace) {
		/**
		 * Collect factors with the data.
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
		 * Clustering
		 * clustersを空集合として初期化
		 */
		List<ArrayList<double[]>> clusters = new LinkedList<ArrayList<double[]>>();
		
		
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
		int c = 0;
		int s = dataSpace.size();
		while(true){
			/*
			 * 進捗状況を表示
			 */
			if ((c % 1) == 0){
				System.out.println("クラスタリング実行中・・・・" + c + " / " + s);
			}
						// ハッカーはバグの一糸混入しないコードを初めから書く
			// 改良したアルゴリズムを実装
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
				minDistance = 1.0 / 0.0;// bugとりました
				((ArrayList) clusters.get(i)).add((double[]) dataSpace.get(r));
				r = 0;
			}
			/*
			 * 代表点を再構成
			 */
			newDelegation = refresh(clusters);
			/*
			 * 代表点が変化しているかを確認、変化していなかったら返す
			 */
			if (judgeDelegation(delegation, newDelegation, 0.0001)){
				/*
				 * 代表点を更新します
				 */
				delegation = newDelegation;
			}
			else{
				break;
			}
			c++;
		}
		return clusters;
	}
	
	LloydClustering(){
		
	}
}
