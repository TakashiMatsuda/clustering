package clustering;

import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

public class LloydClusteringTest {
	
	static int DATASIZE = 1000;
	static int JIGEN = 2;
	static int CLUSTERNUM = 10;

	@Test
	public void testKsplit() {
		// データ要素数1000, 次元数2のテスト
		LinkedList<double[]> data = new LinkedList<double[]>();
		double[] tmp = new double[JIGEN];
		for(int i = 0; i < DATASIZE; i++){
			for(int j = 0; j < JIGEN; j++){
				tmp[j] = Math.random();
			}
			data.add(tmp);
		}
		
		LloydClustering exa = new LloydClustering();
		List<ArrayList<double[]>> fruit = exa.Ksplit(CLUSTERNUM, data);
		// fruitを描画したい
		// tsvファイルに出力します。
		
		try{
			for(int i = 0; i < CLUSTERNUM; i++){
				/*
				 * ファイルの名前を作成
				 */
				Writer out = null;
				File output = new File("clusteringresult" + i + ".tsv");
				out = new BufferedWriter(new FileWriter(output));
				out.write("x	y\n");
				ArrayList<double[]> mold = fruit.get(i);
				
				for(int j = 0; j < DATASIZE; j++){
					/*
					 * 2次元のみに対応。3次元以降を作るときはこの下を変えてください。
					 */
					out.write(mold.get(j)[0] + "	" + mold.get(j)[1]);
				}
				out.close();
			}
		} catch (IOException e){
			e.printStackTrace();
		}
		
		
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
