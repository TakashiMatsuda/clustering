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

/**
 * 
 * @author takashi
 *
 */
public class LloydClusteringTest {
	
	static final int DATASIZE =3000;
	static final int JIGEN = 10;
	static final int CLUSTERNUM = 10;
	static final boolean TWOTRUE = false;

	@Test
	public void testKsplit() {
		LinkedList<double[]> data = new LinkedList<double[]>();
		double[] tmp = new double[JIGEN];
		for(int i = 0; i < DATASIZE; i++){
			tmp = new double[JIGEN];
			for(int j = 0; j < JIGEN; j++){
				tmp[j] = Math.random();// 乱数の質を確認
			}
			data.add(tmp);
			tmp = null;
		}
		
		LloydClustering exa = new LloydClustering();
		List<ArrayList<double[]>> fruit = exa.Ksplit(CLUSTERNUM, data);
		// tsvファイルに出力します。
		// rjavaの存在
		try{
			for(int i = 0; i < CLUSTERNUM; i++){
				/*
				 * ファイルの名前を作成
				 */
				Writer out = null;
				File output = new File(CLUSTERNUM + "clusteringresult" + i + ".tsv");
				out = new BufferedWriter(new FileWriter(output));
				//out.write("x\ty\n");
				ArrayList<double[]> mold = fruit.get(i);
				for(int j = 0; j < mold.size(); j++){
					/*
					 * 2次元・多次元切り替え制御、書き込み操作
					 */
					if (TWOTRUE){
						out.write(mold.get(j)[0] + " " + mold.get(j)[1] + "\n");
					}
					else{
						for(int k = 0; k < JIGEN; k++){
						out.write(mold.get(j)[k] + "   ");
						}
						out.write("\n");
					}
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