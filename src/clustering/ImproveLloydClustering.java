package clustering;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
/**
 * 
 * @author takashi
 *
 */
public class ImproveLloydClustering implements Clustering {
		private int n;
		private int d;
		private int k;
		private byte[][] indicator;

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
			// 書き換えます
			// 問題ないと思う
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
		private double[][] refresh(LinkedList<double[]> dataSpace){
			double[][] fruit = new double[k][d];
			double dist;
			for (int i = 0; i < k; i++){
				dist = 0;
				for(int j = 0; j < d; j++){
					double mother = 0;
					double child = 0;
					fruit[i][j] = 0;
					for(int l = 0; l < n; l++){
						mother += indicator[l][i];
						child += indicator[l][i] * dataSpace.get(l)[j];
					}
					fruit[i][j] = child / mother;
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
		
		public byte[][] Ksplit(int khiki, LinkedList<double[]> dataSpace) {
			/**
			 * Collect factors with the data.
			 * n: dataの要素数
			 * d: dataの次元数
			 */
			
			
			this.n = dataSpace.size();
			this.d = dataSpace.get(0).length;
			this.k = khiki;
			indicator = new byte[n][k];
			
			
			/**
			 * Initiation
			 * Select initial delegation.
			 */
			double[][] delegation  = new double[k][d];
			for(int i = 0; i < k; i++){
				delegation[i] = dataSpace.get(i);
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
					System.out.println("クラスタリング実行中・・・・" + c + "回目のクラスタリング");
				}
				// ハッカーはバグの一糸混入しないコードを初めから書く
				/*
				 * Find each the nearest factor from each deletion.
				 */
				// 書き換え終了しました・・・・
				for(int j = 0; j < dataSpace.size(); j++){
					if ((j % 10) == 0){
						System.out.println("点" + j);
					}
					// 一番近い代表点を選択する
					for(int i = 0; i < k; i++){
						indicator[j][i] = 0;
						suggest = delegation[i];
						if (distance(suggest, dataSpace.get(j)) < minDistance){
							minDistance = distance(suggest, delegation[i]);
							r = i;
						}
					}
					indicator[j][r] = 1; 
					r = 0;
					minDistance = 1.0 / 0.0;
				}
				
				/*
				 * 代表点を再構成
				 */
				System.out.println("REFRESHING DELEGATION......");
				newDelegation = refresh(dataSpace);
				/*
				 * 代表点が変化しているかを確認、更新、変化していなかったら返す
				 */
				if (judgeDelegation(delegation, newDelegation, 0.00000000001)){
					delegation = newDelegation;
				}
				else{
					System.out.println(distance(delegation[0], newDelegation[0]));
					break;
				}
				c++;
			}
			return indicator;
		}

}