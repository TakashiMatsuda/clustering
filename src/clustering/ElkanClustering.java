package clustering;

import java.util.ArrayList;

public class ElkanClustering {
//	FIXME lowerBoundsの変形
//	FIXME 条件分岐をElkanに合わせる
	public ElkanClustering() {
	}

	// private static final boolean DEBUG = false;
	private static final boolean MUGEN = true;
	private static final double threshold = 0.0001;

	private ElkanClusteringData data;

	
	/**
	 * xとyの次元数が一致していることを確認して下さい! 実装終了
	 * 
	 * @param x
	 * @param y
	 * @return Euclid distance between x and y
	 */
	private double distance(double[] x, double[] y) {
		double sum = 0;

		// ユークリッド距離
		for (int i = 0; i < x.length; i++) {
			sum += Math.pow((x[i] - y[i]), 2.0);
		}
		return Math.sqrt(sum);

		// マンハッタン距離に変えて実験
		// for(int i = 0; i < x.length; i++){
		// sum += Math.abs(x[i] - y[i]);
		// }
		// return sum;
	}

	/**
	 * Initiation method for delegation in Hamerly's way 実装終了
	 * 
	 * @param data
	 *            .k
	 * @param dataSpace
	 * @return Initialized delegation for clusters.
	 */
	private double[][] initializeDelegation(ArrayList<double[]> dataSpace) {
		double[][] fruit = new double[data.k][data.d];
		for (int i = 0; i < data.k; i++) {
			fruit[i] = dataSpace.get(i);
		}
		return fruit;
	}

	/**
	 * Lloyd way for k-means clustering Initialization of the cluster, make
	 * "indicator" data.roomにも書き込みをしています。 実装完了しています・・・・
	 * 
	 * @param data
	 *            .k
	 * @param dataSpace
	 * @return the result of k-means clustering by Lloyd algoithm.
	 */
	private void initializeClusters(double[][] delegation,
			ArrayList<double[]> dataSpace) {
		// byte[][] fruit = new byte[data.n][data.k];
		

		data.lowerBorders = new double[data.n][data.k];
		for (int i = 0; i < data.n; i++) {
			/* 一番近い代表点を選択する */
			int r = 0;
			double minDistance = 1.0 / 0.0;
			double dis = 0;
			for (int j = 0; j < data.k; j++) {
				data.lowerBorders[i][j] =  distance(delegation[j], dataSpace.get(i));
				if (data.lowerBorders[i][j] < minDistance) {
					minDistance = dis;
					r = j;
				}
			}
			data.upperBorders[i] = minDistance;
			data.room[i] = r;
			r = 0;
		}

	}


	/**
	 * roomへの完全移行が終了しました。高速で重心の更新を行える関数です。。 O(nd) + O(nk)
	 * 
	 * @param dataSpace
	 * @return
	 */
	private double[][] refreshDelegation(ArrayList<double[]> dataSpace) {
		double[][] sums = new double[data.k][data.d];
		int[] point = new int[data.k];

		for (int i = 0; i < data.n; i++) {
			int r = data.room[i];
			point[r]++;
			for (int x = 0; x < data.d; x++) {
				sums[r][x] += dataSpace.get(i)[x];
			}
		}
		for (int j = 0; j < data.k; j++) {
			for (int x = 0; x < data.d; x++) {
				sums[j][x] = sums[j][x] / (double) point[j];
			}
		}

		return sums;
	}

	/**
	 * 実装完了しています。 Refresh upperBorder
	 * 
	 * @param memberIndicator
	 * @param dataSpace
	 * @return
	 */
	private double initializeUpperBorder(int i, double[][] delegation,
			ArrayList<double[]> dataSpace) {

		return distance(dataSpace.get(i), delegation[data.room[i]]);
	}

	/**
	 * 実装完了しています・・・・
	 * 
	 * @param i
	 * @param data
	 *            .indicator
	 * @param delegation
	 * @param dataSpace
	 * @return
	 */
	private double[] initializeLowerBorder(int i, double[][] delegation,
			ArrayList<double[]> dataSpace) {
		double[] l = new double[data.k];
		double min = 1.0 / 0.0;
//		double d = 0;
		for (int j = 0; j < data.k; j++) {
			l[j] = distance(dataSpace.get(i), delegation[j]);
//			if (data.room[i] != j) {
//				if (d < min) {
//					min = d;
//				}
//			}
		}
		return l;
	}

	/**
	 * 実装完了しています・・・・ The least distance between delegations
	 * 
	 * @param j
	 * @param delegation
	 * @return The nearest delegate number from delegation[j]
	 */
	private double minDelegate(int j, double[][] delegation) {
		double suggest = 0;
		double min = 1.0 / 0.0;
		int mintag = 0;
		for (int jj = 0; jj < data.k; jj++) {
			// jを抜いたもの
			// 0の場合を除けばよい
			if (jj != j) {
				suggest = distance(delegation[j], delegation[jj]);
				if (suggest < min) {
					min = suggest;
					mintag = jj;
				}
			}
		}
		return min;
	}

		
	/**
	 * 実装完了 argmax(i){v}
	 * 
	 * @param v
	 * @return argmax{v}
	 */
	private int argMax(double[] v) {
		double max = 0;
		int arg = 0;
		for (int i = 0; i < v.length; i++) {
			if (v[i] > max) {
				max = v[i];
				arg = i;
			}
		}
		return arg;
	}

	/**
	 * pointからpointに最も近い重心を持つクラスタ番号へと変換します。
	 * 
	 * @param point
	 * @return pointに最も近い重心を持つクラスタ番号
	 */
	private int nearestCenter(double[] point, double[][] delegation) {
		int minnum = 0;
		double dis = 0;
		double mindis = 1.0 / 0.0;
		for (int i = 0; i < data.k; i++) {
			dis = distance(point, delegation[i]);
			if (dis < mindis) {
				minnum = i;
				mindis = dis;
			}
		}
		return minnum;
	}

	
	/**
	 * 
	 * @param k
	 * @param dataSpace
	 * @return
	 */
	public int[] Ksplit(int k, ArrayList<double[]> dataSpace) {
		System.out.println("クラスタリング開始しました・・・・");
		this.data = new ElkanClusteringData();
		this.data.n = dataSpace.size();
		this.data.d = dataSpace.get(0).length;
		this.data.k = k;
		
		this.data.room = new int[data.n];
		this.data.roomsize = new int[data.k];
		
		/*
		 * Initialize parameters 綺麗なやり方ではないが、roomも変更する。
		 */
		System.out.println("クラスタの初期化中・・・・");// 時間がかかっている
		double[][] delegation = initializeDelegation(dataSpace);
		data.upperBorders = new double[data.n];
		data.lowerBorders = new double[data.n][data.k];
		initializeClusters(delegation, dataSpace);

		System.out.println("繰り返しクラスタリングを開始しました・・・・");
		int count = 0;
		/*
		 * Update parameters
		 */
		while (MUGEN) {
			count++;
			double[] minClusterDistance = new double[k];
			int[] nearestClusterNumber = new int[k];
			for (int j = 0; j < data.k; j++) {
				minClusterDistance[j] = minDelegate(j, delegation);
			}

			System.out.println("Hamerly");
			/*
			 * Elkan Algorithms Elkanの命題の条件分岐を行いながら、クラスタを更新します
			 */
			int rc = 0;// 何個中に入ってしまったか数えています。
			for (int i = 0; i < data.n; i++) {
				double m = Math.max(minClusterDistance[data.room[i]] / 2.0,
						data.lowerBorders[i]);
				if (data.upperBorders[i] <= m) {
					continue;
				} else {
					data.upperBorders[i] = initializeUpperBorder(i, delegation,
							dataSpace);
					if (data.upperBorders[i] <= m) {
						continue;
					} else {/* 依然として条件を満たさない */
						rc++;// どのくらいここに入ってきてしまうのかをカウントしている

						int oldCluster = data.room[i];
						/*
						 * 所属クラスタ番号を更新する O(nkd)
						 */
						data.room[i] = nearestCenter(dataSpace.get(i),
								delegation);
						if (oldCluster != data.room[i]) {/* 変化していたら関連値を更新 */
							data.upperBorders[i] = initializeUpperBorder(i,
									delegation, dataSpace);
							data.lowerBorders[i] = initializeLowerBorder(i,
									delegation, dataSpace);
						}

					}
				}

			}
			System.out.println("中に入った回数" + rc + "/" + data.n);

			/* クラスタ番号を主語とする記号の値を更新します(重心の再計算) */
			System.out.println("重心の計算");
			double[] movingDistance = new double[k];
			double[][] oldDelegate = delegation.clone();
			double sumMove = 0;
			delegation = refreshDelegation(dataSpace);
			for (int j = 0; j < k; j++) {
				movingDistance[j] = distance(delegation[j], oldDelegate[j]);
				sumMove += movingDistance[j];
			}

			/* 各点の上界、下界を更新します */

			System.out.println("上界、下界の更新");
			int r = argMax(movingDistance);
			for (int i = 0; i < data.n; i++) {
				data.upperBorders[i] = data.upperBorders[i]
						+ movingDistance[data.room[i]];
				for(int j = 0; j < data.k; j++){
					data.lowerBorders[i][j] = Math.max(data.lowerBorders[i][j] - movingDistance[j], 0.0);
				}
			}

			/* 終了判定 */
			if (sumMove >= threshold) {
				// if (count % 10 == 0) {
				System.err.println(count + "回目のクラスタリングでした・・・・現在のずれ:" + sumMove);
				// }
				continue;
			} else {
				break;
			}
		}
		System.out.println("クラスタリングが終了しました");
		
		return data.room;// こちらにこれから切り替える。
//		return data.indicator;
	}

}
