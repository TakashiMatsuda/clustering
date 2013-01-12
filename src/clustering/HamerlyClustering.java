package clustering;

import java.util.LinkedList;
import java.util.List;

/**
 * Fast k-means clustering by Hamer.
 * Hamerの方法によるクラスタリングの実装
 * リファクタリング中。もっと高速化したい。今のままだとアルゴリズムの変更による恩恵が小さい。
 * @author takashi
 *
 */
public class HamerlyClustering implements Clustering, AbstractClustering{
	
	// コンストラクタで必要な引数をすべてとってしまう方が美しいなあ
	/**
	 * 
	 */
	HamerlyClustering(){
		
	}
	
	private static final boolean DEBUG = false;
	private static final boolean MUGEN = true;
	private static final double threshold = 0.00001;
	
	private HamerlyClusteringData data = new HamerlyClusteringData();

	/**
	 * 
	 * ベクトルのノルム
	 * 実装終了
	 * @param tmp
	 * @return norm
	 */
	private double norm(double[] x) {
		double sum = 0;
		for (int i = 0; i < x.length; i++){
			sum += Math.pow(x[i], 2.0);
		}
		return Math.sqrt(sum);
	}
	
	/**
	 * xとyの次元数が一致していることを確認して下さい!
	 * 実装終了
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
		
		// マンハッタン距離に変えて実験
//		for(int i = 0; i < x.length; i++){
//			sum += Math.abs(x[i] - y[i]);
//		}
//		return sum;
	}
	
	/**
	 * Initiation method for delegation in Hamerly's way
	 * 実装終了
	 * @param data.k
	 * @param dataSpace
	 * @return Initialized delegation for clusters.
	 */
	private double[][] initializeDelegation(LinkedList<double[]> dataSpace){
		double[][] fruit = new double[data.k][data.d];
		for(int i = 0; i < data.k; i++){
			fruit[i] = dataSpace.get(i);
		}
		return fruit;
	}
	
	/**
	 * Lloyd way for k-means clustering
	 * Initialization of the cluster,
	 * make "indicator"
	 * data.roomにも書き込みをしています。
	 * 実装完了しています・・・・
	 * @param data.k
	 * @param dataSpace
	 * @return the result of k-means clustering by Lloyd algoithm.
	 */
	private byte[][] initializeClusters(double[][] delegation, LinkedList<double[]> dataSpace){
		byte[][] fruit = new byte[data.n][data.k];
		double[] suggest = new double[data.d];
		for(int i = 0; i < data.n; i++){
			/*  一番近い代表点を選択する*/
			int r = 0;
			double minDistance = 1.0 / 0.0;
			for(int j = 0; j < data.k; j++){
				suggest = delegation[j];
				if (distance(suggest, dataSpace.get(i)) < minDistance){
					minDistance = distance(suggest, dataSpace.get(i));
					r = j;
				}
			}
			fruit[i][r] = 1; 
			data.room[i] = r;
			r = 0;
		}
		
		return fruit;
	}
	

	/**
	 * Return new Delegate with each cluster
	 * クラスタの重心を再計算して返します
	 * indicatorの形を要求します
	 * 実装完了しています・・・・
	 * @param memberIndicator
	 * @param dataSpace
	 * @return ... (重心) of the cluster
	 */
	private double[] refreshDelegation(int j, byte[][] indicator, LinkedList<double[]> dataSpace){
		double[] sum = new double[data.d];
		int num = 0;
		for(int i = 0; i < data.n; i++){
			if (indicator[i][j] == 1){
				num++;
			}
			for(int x = 0; x < data.d; x++){
				sum[x] += indicator[i][j] * dataSpace.get(i)[x];
			}
		}
		
		for(int i = 0; i < data.n; i++){
			if (indicator[i][j] == 1){
				num++;
			}
		}
		for(int x = 0; x < data.d; x++){
			for(int i = 0 ; i < data.n; i++){
				sum[x] += indicator[i][j] * dataSpace.get(i)[x];
			}
			sum[x] = sum[x] / (double)num;
		}
		
		
		return sum;
	}
	
	
	/**
	 * 実装完了しています。
	 * Refresh upperBorder
	 * @param memberIndicator
	 * @param dataSpace
	 * @return 
	 */
	private double initializeUpperBorder(int i,
			double[][] delegation, LinkedList<double[]> dataSpace){
		
		return distance(dataSpace.get(i), delegation[data.room[i]]);
	}
	
	
	
	/**
	 * 実装完了しています・・・・
	 * @param i
	 * @param indicator
	 * @param delegation
	 * @param dataSpace
	 * @return
	 */
	private double initializeLowerBorder(int i,
			double[][] delegation, LinkedList<double[]> dataSpace) {
		
		double min = 1.0 / 0.0;
		double d = 0;
		for(int j = 0; j < data.k; j++){
			d = distance(dataSpace.get(i), delegation[j]);
			if (data.room[i] != j){
				if (d < min){
					min = d;
				}
			}
		}
		return min;// あとでこれをほとんどコピーした2番目に近いクラスター番号を返す関数を定義する可能性が高い
	}
	
	
	/**
	 * 実装完了しています・・・・
	 * The least distance between delegations 
	 * @param j
	 * @param delegation
	 * @return The nearest delegate number from delegation[j]
	 */
	private int minDelegate(int j, double[][] delegation){
		double suggest = 0;
		double min = 1.0 / 0.0;
		int mintag = 0;
		for(int jj = 0; jj < data.k; jj++){
			// jを抜いたもの
			// 0の場合を除けばよい
			if (jj != j){
				suggest = distance(delegation[j], delegation[jj]);
				if (suggest < min){
					min = suggest;
					mintag  =jj;
				}
			}
		}
		return mintag;
		// この計算のコストはO(k), 大きくないので再計算
	}
	
	
	
//	O(k), これはO(1)にできる。するべきですね。
//	メモリは足りているのだから、indicatorとは別に便利なindicator2みたいのを作ればよい。
	/**
	 * ある点に対してその属するクラスター番号を返す
	 * roomで完全に置き換えられました。
	 * 実装完了
	 * @param i
	 * @param indicator
	 * @return 所属クラスター番号
	 */
	private int clusterNumber(int i, byte[][] indicator){
		int fruit = -1;
		for(int j = 0; j < data.k; j++){
			if (indicator[i][j] != 1){
				continue;
			}
			else{
				fruit = j;
			}
		}
		return fruit;
	}
	
	
	/**
	 * 実装完了
	 * @param a
	 * @param b
	 * @return aとbの間の距離和を計算し、閾値以下だったらfalse, そうでなければtrue
	 */
	private boolean judgeDelegation(double[] movingDistance,  double threshold){
		double sum = 0;
		for (int j = 0; j < data.k; j++){
			sum += movingDistance[j];
		}
		if (sum >= threshold)
			return true;
		else
			return false;
	}
	
	
	/**
	 * 実装完了
	 * argmax(i){v}
	 * @param v
	 * @return argmax{v}
	 */
	private int argMax(double[] v){
		double max = 0;
		int arg = 0;
		for(int i = 0; i < v.length; i++){
			if (v[i] > max){
				max = v[i];
				arg = i;
			}
		}
		return arg;
	}
	
	
	/**
	 * pointからpointに最も近い重心を持つクラスタ番号へと変換します。
	 * @param point
	 * @return pointに最も近い重心を持つクラスタ番号
	 */
	private int nearestCenter(double[] point, double[][] delegation){
		int minnum = 0;
		double dis = 0;
		double mindis = 1.0 / 0.0;
		for(int i = 0; i < data.k; i++){
			dis = distance(point, delegation[i]);
			if (dis < mindis){
				minnum = i;
				mindis = dis;
			}
		}
		return minnum;
	}
	
	
	// このメソッドはこのクラスの外にあってもいい
	/* (非 Javadoc)
	 * @see clustering.AbstractClustering#Ksplit(int, java.util.LinkedList)
	 */
	@Override
	public byte[][] Ksplit(int k, LinkedList<double[]> dataSpace) {
		System.out.println("クラスタリング中・・・");
		this.data.n = dataSpace.size();
		this.data.d = dataSpace.get(0).length;
		this.data.k = k;
		
		this.data.indicator = new byte[data.n][k];
		this.data.room = new int[data.n];
		
		/*
		 * Initialize parameters
		 * 綺麗なやり方ではないが、roomも変更する。
		 */
		double[][] delegation  = initializeDelegation(dataSpace);
		data.indicator = initializeClusters(delegation, dataSpace);
		
		
		/*
		 * Initialize the upperBorder and lowerBorder of the distance between a point and its cluster.
		 */
		double[] upperBorder = new double[data.n];
		double[] lowerBorder = new double[data.n];
		for(int i = 0; i < k; i++){
			upperBorder[i] = initializeUpperBorder(i, delegation, dataSpace);
			lowerBorder[i] = initializeLowerBorder(i, delegation, dataSpace);
		}
		
		
		int count = 0;
		/*
		 * Update parameters
		 */
		while(MUGEN){
			count++;
			
			// 未実装部分計画領域
			// O(k)
			double[] minClusterDistance = new double[k];
			int[] nearestClusterNumber = new int[k];
			for(int j = 0; j < k; j++){
				nearestClusterNumber[j] = minDelegate(j, delegation);
				minClusterDistance[j] = distance(delegation[nearestClusterNumber[j]], delegation[j]);
			}
			
			
//			O(n)
			/*
			 * Hamerly Algorithms
			 * Hamerlyの命題の条件分岐を行いながら、クラスタを更新します
			 */
			for(int i  = 0; i < data.n; i++){
				int clnum = data.room[i];
				double m = Math.max(minClusterDistance[clnum] / 2.0, lowerBorder[i]);
				if (upperBorder[i] > m){
					upperBorder[i] = initializeUpperBorder(i, delegation, dataSpace);
					if (upperBorder[i] > m){/* 依然として条件を満たさない */
//						FIXME 最隣クラスタが変化しているかの条件分岐を書き上げる -> 完了した？
//						テスト中
						
						
						int oldCluster = data.room[i];
						/*
						 * 所属クラスタ番号を更新する
						 */
						data.room[i] = nearestCenter(dataSpace.get(i), delegation);// ここ、この場で（更新）計算して最隣クラスタ番号を求める<---未実装
						if (oldCluster != data.room[i]){/* 変化していたら関連値を更新 */
							// indicatorの更新
							data.indicator[i][oldCluster] = 0;
							data.indicator[i][data.room[i]] = 1;
							upperBorder[i] = initializeUpperBorder(i, delegation, dataSpace);
							lowerBorder[i] = initializeLowerBorder(i, delegation, dataSpace);
						}
					}
				}
			}
			
			/* クラスタ番号を主語とする記号の値を更新します*/
			double[] movingDistance = new double[k];
			for(int j = 0; j < k; j++){
				double[] oldDelegate = delegation[j];
				delegation[j] = refreshDelegation(j, data.indicator, dataSpace);
				movingDistance[j] = distance(delegation[j], oldDelegate);
			}
			
			 /* 各点の上界、下界を更新します*/
			int r = argMax(movingDistance);
			// 直打ち、推奨されないけどもとりあえず書きます
			for(int i = 0; i < data.n; i++){
				upperBorder[i] = upperBorder[i] + movingDistance[data.room[i]];
				lowerBorder[i] = lowerBorder[i] - movingDistance[r];
			}
			
			
			/* 終了判定 */
			if (judgeDelegation(movingDistance, threshold)){
				if (count % 10 == 0){
					System.err.println(count + "回目のクラスタリングです・・・・ 現在のずれ: " + norm(movingDistance));
				}
				continue;
			}
			else{
				break;
			}
		}
		
		
		return data.indicator;
	}
	
}