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

public class ImproveLloydClusteringTest {
	static final int DATASIZE =50;
	static final int JIGEN = 5;
	static final int CLUSTERNUM = 2;
	static final boolean TWOTRUE = true;
	
	@Test
	public void testKsplit() {
		LinkedList<double[]> data = new LinkedList<double[]>();
		double[] tmp = new double[JIGEN];
		for(int i = 0; i < DATASIZE; i++){
			tmp = new double[JIGEN];
			for(int j = 0; j < JIGEN; j++){
				tmp[j] = Math.random();
			}
			data.add(tmp);
			tmp = null;
		}
		ImproveLloydClustering exa = new ImproveLloydClustering();
		byte[][] fruit = exa.Ksplit(CLUSTERNUM, data);
		try{
			for(int i = 0; i < CLUSTERNUM; i++){
				/*
				 * ファイルの名前を作成
				 */
				Writer out = null;
				File output = new File(CLUSTERNUM + "clusteringresult" + i + ".tsv");
				out = new BufferedWriter(new FileWriter(output));
				
				
				ArrayList<double[]> mold = new ArrayList<double[]>();
				for(int u = 0; u < data.size(); u++){
					if (fruit[u][i] == 1){
						mold.add(data.get(u));
					}
				}
				
				
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

}