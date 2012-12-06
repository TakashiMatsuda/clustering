package clustering;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.LinkedList;

import org.junit.Test;

public class LloydClusteringTest {

	@Test
	public void testKsplit() {
		// 乱数発生器の使い方を確認して書く
		
		// データ要素数1000, 次元数2のテスト
		LinkedList<double[]> data = new LinkedList<double[]>();
		double[] tmp = new double[2];
		for(int i = 0; i < 1000; i++){
			// tmpが前の値を引きずらないようにすることが大事
			for(int j = 0; j < 2; j++){
				tmp[j] = Math.random();
			}
			data.add(tmp);
		}
		
		LloydClustering exa = new LloydClustering();
		exa.Ksplit(10, data);
		
	}
	
	@Test
	public void testKsplit100(){
		
	}
	
	@Test
	public void testKspilit100(){
		
	}

	@Test
	public void testLloydClustering() {
		LloydClustering exa = new LloydClustering();
	}

}
