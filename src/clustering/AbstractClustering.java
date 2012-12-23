package clustering;

import java.util.LinkedList;

public interface AbstractClustering {

	// このメソッドはこのクラスの外にあってもいい
	/**
	 * 実装完了
	 *  Hamerlyの方法によるクラスタリングの結果を返します。
	 * @param k : target clusters number
	 * @param data データ集合
	 * @return K-means clustering result
	 */
	public abstract byte[][] Ksplit(int k, LinkedList<double[]> dataSpace);

}