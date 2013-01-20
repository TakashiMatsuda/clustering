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
 * Complete code.
 * @author takashi
 *
 */
@SuppressWarnings("unused")
public class ImproveLloydClusteringTest {
	static final int DATASIZE =100000;
	static final int JIGEN = 2;
	static final int CLUSTERNUM = 1000;
		
	@Test
	public void testKsplit() {
		ArrayList<double[]> data = new ArrayList<double[]>();
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
				File output = new File(CLUSTERNUM + "K_" + JIGEN + "D_" + DATASIZE + "N_Lloyd_result" + i + ".tsv");
				out = new BufferedWriter(new FileWriter(output));
				
				ArrayList<double[]> mold = new ArrayList<double[]>();
				for(int u = 0; u < data.size(); u++){
					if (fruit[u][i] == 1){
						mold.add(data.get(u));
					}
				}
				
				for(int j = 0; j < mold.size(); j++){
					for(int k = 0; k < JIGEN; k++){
						out.write(mold.get(j)[k] + "   ");
					}
					out.write("\n");
				}
				out.close();
			}
		} catch (IOException e){
			e.printStackTrace();
		}
	}

}