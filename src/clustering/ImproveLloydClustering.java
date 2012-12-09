package clustering;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
/**
 * Complete code.
 * @author takashi
 *
 */
public class ImproveLloydClustering implements Clustering {
		private static final boolean DEBUG = false;
		
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
				//dist = distance(fruit[i]);
				//for(int j = 0; j < d; j++){
					//fruit[i][j] = fruit[i][j] / dist;
				//}
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
			this.indicator = new byte[n][k];
			
			
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
			while(true){
				newDelegation = new double[k][d];
				
				/*
				 * 進捗状況を表示
				 */
				if ((c % 1) == 0){
					System.out.println("クラスタリング実行中・・・・" + c + "回目のクラスタリング");
				}
				
				
				/*
				 * Find each the nearest factor from each deletion.
				 */
				// すべてクラスタ0に格納されている
				//for(int i = 0; i < k; i++){
					//System.out.println(i +"番クラスタの代表点" + delegation[i][0] + "　" + delegation[i][1]);
			//	}
				for(int j = 0; j < dataSpace.size(); j++){
					//if ((j % 1000) == 0){
						//System.out.println("点" + j);
					//}
					/*
					 *  一番近い代表点を選択する
					 */
					for(int i = 0; i < k; i++){
					//	System.out.println(i);
						suggest = delegation[i];
						// minDistanceのスコープに注目
						//System.out.println("代表点" + i + "番との距離" + distance(suggest, dataSpace.get(j)) + "記録距離　" + minDistance);
						if (distance(suggest, dataSpace.get(j)) < minDistance){
						//	System.out.println("クラスタリング割ふり入った");
							minDistance = distance(suggest, dataSpace.get(j));
							r = i;
						}
						suggest = null;
						
					}
					indicator[j][r] = 1; 
					// System.out.println(j + "は　"  +  r  + "　番クラスタです　　" + dataSpace.get(j)[0] + "　" + dataSpace.get(j)[1] + "　<->　"  + 
					// delegation[r][0] + "　" + delegation[r][1]);
					r = 0;
					minDistance = 1.0 / 0.0;
				}
				
				
				/*
				 * 代表点を再構成
				 */
				System.out.println("REFRESHING DELEGATION......");// 一周しか回らない、かってにbreakしている
				//System.out.println(delegation[0][0]);
				newDelegation = refresh(dataSpace);
				// refreshが動いてない
				//System.out.println(newDelegation[0][0]);
				
				
				/*
				 * 代表点が変化しているかを確認、更新、変化していなかったら返す
				 */
				if (judgeDelegation(delegation, newDelegation, 0.000001)){
					delegation = newDelegation.clone();
					newDelegation = null;
				}
				else{
					System.out.println("終了します");
					break;
				}
				c++;
			}
			return indicator;
		}
		
		ImproveLloydClustering(){
			
		}

}
