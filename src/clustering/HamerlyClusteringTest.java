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

public class HamerlyClusteringTest {
	static final int DATASIZE = 10000;
	static final int JIGEN = 200;
	static final int CLUSTERNUM = 10;

	// 何かいいクラスタリングサンプルはないかな
	// Adaboostの実装で用いている特徴ベクトルをクラスタリングするのはどうだろうか
	// マンハッタン距離に変えて実験

	HamerlyClustering exa;
	ArrayList<double[]> data;
	byte[][] fruit;

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
		this.exa = new HamerlyClustering();
	}

	@Test
	public void testKsplit() {

		this.fruit = exa.Ksplit(CLUSTERNUM, data);
	}

	@After
	public void aftertestKsplit() {
		try {
			for (int i = 0; i < CLUSTERNUM; i++) {
				/*
				 * ファイルの名前を作成
				 */
				Writer out = null;
				File output = new File(CLUSTERNUM + "K_" + JIGEN + "D_"
						+ DATASIZE + "N_Hamerly_result" + i + ".tsv");
				out = new BufferedWriter(new FileWriter(output));

				ArrayList<double[]> mold = new ArrayList<double[]>();
				for (int u = 0; u < data.size(); u++) {
					if (fruit[u][i] == 1) {
						mold.add(data.get(u));
					}
				}

				for (int j = 0; j < mold.size(); j++) {
					for (int k = 0; k < JIGEN; k++) {
						out.write(mold.get(j)[k] + "   ");
					}
					out.write("\n");
				}
				out.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
