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
	 * @param tmp
	 * @return absolute value
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
	 * Initiation method for delegation in Hamer's way
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
	 * 	Initialize clusters in Hamer's way
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
	 *  Hamerの方法によるクラスタリングの結果を返します。
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
			
			
			
			// (ここまで)
			
			/*
			 * Update parameters
			 */
			indicator = refreshIndicator(indicator, dataSpace);
			for(int i = 0; i < k; i++){
				borderIndicator.set(i, refreshBorder(indicator[i], dataSpace));
			}
		}
		
		
		return null;
	}
	
	
	HamerlyClustering(){
		
	}
}