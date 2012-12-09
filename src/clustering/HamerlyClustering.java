package clustering;

import java.util.LinkedList;
import java.util.List;

/**
 * Fast k-means clustering by Hamer.
 * Hamerの方法によるクラスタリングの実装
 * @author takashi
 *
 */
public class HamerlyClustering implements Clustering{
	private static final boolean DEBUG = false;
	private static final boolean MUGEN = true;
	private static final double threshold = 0.00001;
	
	private int n;
	private int d;
	private int k;
	private byte[][] indicator;
	

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
	}
	
	/**
	 * Initiation method for delegation in Hamerly's way
	 * 実装終了
	 * @param k
	 * @param dataSpace
	 * @return Initialized delegation for clusters.
	 */
	private double[][] initializeDelegation(LinkedList<double[]> dataSpace){
		double[][] fruit = new double[k][d];
		for(int i = 0; i < k; i++){
			fruit[i] = dataSpace.get(i);
		}
		return fruit;
	}
	
	/**
	 * Lloyd way for k-means clustering
	 * Initialization of the cluster,
	 * make "indicator"
	 * 実装完了しています・・・・
	 * @param k
	 * @param dataSpace
	 * @return the result of k-means clustering by Lloyd algoithm.
	 */
	private byte[][] initializeClusters(double[][] delegation, LinkedList<double[]> dataSpace){
		byte[][] fruit = new byte[n][k];
		double[] suggest = new double[d];
		for(int i = 0; i < n; i++){
			/*  一番近い代表点を選択する*/
			int r = 0;
			double minDistance = 1.0 / 0.0;
			for(int j = 0; j < k; j++){
				suggest = delegation[j];
				if (distance(suggest, dataSpace.get(i)) < minDistance){
					minDistance = distance(suggest, dataSpace.get(i));
					r = j;
				}
			}
			fruit[i][r] = 1; 
			r = 0;
		}
		
		return fruit;
	}
	
	
	/**
	 * Return new Delegate with each cluster
	 * クラスタの重心を再計算して返します
	 * 実装完了しています・・・・
	 * @param memberIndicator
	 * @param dataSpace
	 * @return ... (重心) of the cluster
	 */
	private double[] refreshDelegation(int j, byte[][] indicator, LinkedList<double[]> dataSpace){
		double[] sum = new double[d];
		int num = 0;
		for(int i = 0; i < n; i++){
			if (indicator[i][j] == 1){
				num++;
			}
			for(int x = 0; x < d; x++){
				sum[x] += indicator[i][j] * dataSpace.get(i)[x];
			}
		}
		
		for(int i = 0; i < n; i++){
			if (indicator[i][j] == 1){
				num++;
			}
		}
		for(int x = 0; x < d; x++){
			for(int i = 0 ; i < n; i++){
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
	private double initializeUpperBorder(int i, byte[][] indicator, 
			double[][] delegation, LinkedList<double[]> dataSpace){
		
		return distance(dataSpace.get(i), delegation[clusterNumber(i, indicator)]);
	}
	
	
	
	/**
	 * 実装完了しています・・・・
	 * @param i
	 * @param indicator
	 * @param delegation
	 * @param dataSpace
	 * @return
	 */
	private double initializeLowerBorder(int i, byte[][] indicator,
			double[][] delegation, LinkedList<double[]> dataSpace) {
		
		double min = 1.0 / 0.0;
		double d = 0;
		for(int j = 0; j < k; j++){
			d = distance(dataSpace.get(i), delegation[j]);
			if (clusterNumber(i, indicator) != j){
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
		for(int jj = 0; jj < k; jj++){
			// jを抜いたもの
			// oの場合を除けばよい
			suggest = distance(delegation[j], delegation[jj]);
			if (suggest != 0){
				if (suggest < min){
					min = suggest;
					mintag  =jj;
				}
			}
		}
		return mintag;// どれが一番近いかも一緒に返したほうがいい気がする。
		// クラスを作って格納するのと再計算するのどちらが速いか微妙なところ
		// この計算のコストはO(k), 大きくないので再計算
		}
	
	
	
	/**
	 * ある点に対してその属するクラスター番号を返す
	 * 実装完了
	 * @param i
	 * @param indicator
	 * @return 所属クラスター番号
	 */
	private int clusterNumber(int i, byte[][] indicator){
		int fruit = -1;
		for(int j = 0; j < k; j++){
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
		for (int j = 0; j < k; j++){
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
	
	
	// このメソッドはこのクラスの外にあってもいい
	/**
	 * 実装完了
	 *  Hamerlyの方法によるクラスタリングの結果を返します。
	 * @param k : target clusters number
	 * @param data データ集合
	 * @return K-means clustering result
	 */
	@Override
	public byte[][] Ksplit(int k, LinkedList<double[]> dataSpace) {
		this.n = dataSpace.size();
		this.d = dataSpace.get(0).length;
		this.k = k;
		
		this.indicator = new byte[n][k];
		
		/*
		 * Initialize parameters
		 */
		double[][] delegation  = initializeDelegation(dataSpace);
		indicator = initializeClusters(delegation, dataSpace);
		
		
		/*
		 * Initialize the upperBorder and lowerBorder of the distance between a point and its cluster.
		 */
		double[] upperBorder = new double[n];
		double[] lowerBorder = new double[n];
		for(int i = 0; i < k; i++){
			upperBorder[i] = initializeUpperBorder(i, indicator, delegation, dataSpace);
			lowerBorder[i] = initializeLowerBorder(i, indicator, delegation, dataSpace);
		}
		
		
		
		/*
		 * Update parameters
		 */
		while(MUGEN){
			
			// 未実装部分計画領域
			double[] minClusterDistance = new double[k];
			for(int j = 0; j < k; j++){
				minClusterDistance[j] = distance(delegation[minDelegate(j, delegation)], delegation[j]);
			}
			
			/*
			 * Hamerly Algorithms
			 * Hamerlyの命題の条件分岐を行いながら、クラスタを更新します
			 */
			for(int i  = 0; i < n; i++){
				int clnum = clusterNumber(i, indicator);
				double m = Math.max(minClusterDistance[clnum] / 2.0, lowerBorder[i]);
				// 引越しを急いだ方がいいかもしれない
				if (upperBorder[i] > m){
					upperBorder[i] = initializeUpperBorder(i, indicator, delegation, dataSpace);
					if (upperBorder[i] > m){/* 依然として条件を満たさない */
						int oldCluster = clusterNumber(i, indicator);
						int preCluster = 0;// ここ、最隣クラスタ番号を求める<---未実装
						if (oldCluster != preCluster){/* 変化していたら関連値を更新 */
							// indicatorの更新
							indicator[i][oldCluster] = 0;
							indicator[i][preCluster] = 1;
							upperBorder[i] = initializeUpperBorder(i, indicator, delegation, dataSpace);
							lowerBorder[i] = initializeLowerBorder(i, indicator, delegation, dataSpace);
						}
					}
				}
			}
			
			/* クラスタ番号を主語とする記号の値を更新します*/
			// 更新途中で更新の必要がないと判断されたら終了します。
			double[] movingDistance = new double[k];
			for(int j = 0; j < k; j++){
				double[] oldDelegate = delegation[j];
				delegation[j] = refreshDelegation(j, indicator, dataSpace);
				movingDistance[j] = distance(delegation[j], oldDelegate);
			}
			
			 /* 各点の上界、下界を更新します*/
			int r = argMax(movingDistance);
			// 直打ち、推奨されないけどもとりあえず書きます
			for(int i = 0; i < n; i++){
				upperBorder[i] = upperBorder[i] + movingDistance[clusterNumber(i, indicator)];
				lowerBorder[i] = lowerBorder[i] - movingDistance[r];
			}
			
			
			/* 終了判定 */
			if (judgeDelegation(movingDistance, threshold)){
				continue;
			}
			else{
				break;
			}
		}
		
		
		return indicator;
	}
	

	// コンストラクタで必要な引数をすべてとってしまう方が美しいなあ
	/**
	 * 
	 */
	HamerlyClustering(){
		
	}
}