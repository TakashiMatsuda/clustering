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
	static final int DATASIZE =5000;
	static final int JIGEN = 5;
	static final int CLUSTERNUM = 100;
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
		byte[][] fruit = exa.Ksplit(CLUSTERNUM, data);// 
		// tsvファイルに出力します。
		// rjavaの存在
	}

}
