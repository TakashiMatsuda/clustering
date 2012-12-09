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
	 * 	Initialize clusters in Hamerly's way
	 * 実装終了
	 * @param k
	 * @param dataSpace
	 * @return
	 */
	private byte[][] initializeClusters(LinkedList<double[]> dataSpace){
		byte[][] fruit = new byte[n][k];
		
		
		return null;
	}
	
	
	/**
	 * Return new Delegate with each cluster
	 * @param memberIndicator
	 * @param dataSpace
	 * @return
	 */
	private double[] refreshDelegation(byte[] memberIndicator, LinkedList<double[]> dataSpace){
		
		return null;
	}
	
	
	/**
	 * 
	 * @param indicator
	 * @param dataSpace
	 * @return
	 */
	private byte[][] refreshIndicator(byte[][] indicator, LinkedList<double[]> dataSpace){
		
		
		return null;
	}
	
	
	/**
	 * Refresh border in the cluster
	 * @param memberIndicator
	 * @param dataSpace
	 * @return
	 */
	private int[] refreshBorder(byte[] memberIndicator, LinkedList<double[]> dataSpace){
		
		return null;
	}
	
	
	
	/**
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
	 * @param i
	 * @param indicator
	 * @return 所属クラスター番号
	 */
	private int clusterNumber(int i, byte[][] indicator){
		int fruit = -1;
		for(int j = 0; j < k; j++){
			if (indicator[j][i] != 1){
				continue;
			}
			else{
				fruit = j;
			}
		}
		return fruit;
	}
	
	
	/**
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
		this.indicator = new byte[k][n];// 仕様を変更した、以後注意して下さい
		

		/*
		 * 各clusterとその上限と下限の対応が必要
		 * get[0]は下限の点番号
		 * get[1]は上限の点番号
		 */
		List<int[]> borderIndicator = new LinkedList<int[]>();
		
		
		/*
		 * Initialize parameters
		 */
		double[][] delegation  = initializeDelegation(dataSpace);
		indicator = initializeClusters(dataSpace);
		for(int i = 0; i < k; i++){
			borderIndicator.add(refreshBorder(indicator[i], dataSpace));
		}
		
		
		/*
		 * Update parameters
		 */
		while(MUGEN){
			for(int i = 0; i < k; i++){
				delegation[i] = refreshDelegation(indicator[i], dataSpace);
			}
			
			
			/*
			 * Hamer Algorithms
			 */
			// 数学的なバックグラウンドは大丈夫なのか？
			// Lloydの方法と原理的には同じはず、EMなので局所最適になる
			for(int i  = 0; i < n; i++){
				int clnum = clusterNumber(i, indicator);
				int nearCluster = minDelegate(clnum, delegation);
				double m = Math.max(distance(delegation[nearCluster], delegation[clnum]) / 2.0, 
						distance(delegation[nearCluster], dataSpace.get(i)));
				
			}
			
			
			// (ここまで)
			
			/*
			 * Update parameters
			 */
			indicator = refreshIndicator(indicator, dataSpace);
			for(int i = 0; i < k; i++){
				borderIndicator.set(i, refreshBorder(, dataSpace));
			}
			
			break;// 無駄なバグ表示を避ける為。あとで除去して下さい
		}
		
		
		return null;
	}
	
	
	// コンストラクタで必要な引数をすべてとってしまう方が美しいなあ
	/**
	 * 
	 */
	HamerlyClustering(){
		
	}
}