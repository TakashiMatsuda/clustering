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

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ElkanClusteringTest {
	static final int DATASIZE = 10000;
	static final int JIGEN = 200;
	static final int CLUSTERNUM = 10;

	ElkanClustering exa;
	ArrayList<double[]> data;
	int[] fruit;

	@Before
	public void beforetestKsplit() {
		this.data = new ArrayList<double[]>();
		double[] tmp = new double[JIGEN];
		for (int i = 0; i < DATASIZE; i++) {
			tmp = new double[JIGEN];
			for (int j = 0; j < JIGEN; j++) {
				tmp[j] = Math.random();
			}
			data.add(tmp);
			tmp = null;
		}
		this.exa = new ElkanClustering();
	}

	@Test
	public void testKsplit() {
		// 返り値の型を変更しよう。
		this.fruit = exa.Ksplit(CLUSTERNUM, data);
	}

	@After
	public void aftertestKsplit() {
		try {
			ArrayList<ArrayList<Integer>> mold = new ArrayList<ArrayList<Integer>>(
					CLUSTERNUM);

			for (int i = 0; i < CLUSTERNUM; i++) {
				mold.add(new ArrayList<Integer>(DATASIZE / CLUSTERNUM));
			}
			for (int u = 0; u < data.size(); u++) {
				mold.get(fruit[u]).add(u);
			}
			for (int i = 0; i < CLUSTERNUM; i++) {
				Writer out = null;
				File output = new File(CLUSTERNUM + "K_" + JIGEN + "D_"
						+ DATASIZE + "N_Elkan_result" + i + ".tsv");
				out = new BufferedWriter(new FileWriter(output));
				for (int j = 0; j < mold.get(i).size(); j++) {
					for (int d = 0; d < JIGEN; d++) {
//						書き込みがミスっているのかもしれない。
						out.write(data.get(mold.get(i).get(j))[d] + "   ");
					}
				}
				out.write("\n");
				out.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
