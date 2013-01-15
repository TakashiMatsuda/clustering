package clustering;

import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class ClusteringTest {
	static final int DATASIZE =10000;
	static final int JIGEN = 100;
	static final int CLUSTERNUM = 100;
	static final boolean TWOTRUE = false;// ここを変化させてテスト;
	
	// 何かいいクラスタリングサンプルはないかな
	// Adaboostの実装で用いている特徴ベクトルをクラスタリングするのはどうだろうか
	// twitter APIで、投稿時刻とツイート数のクラスタリングなどでもいい
	// 相関を求めるのとどう違うんだろう
	// 画像解析に使うとか
	// 書き込みを抜いた純粋テストを制作して、CPU時間を計測して下さい。
	
	// マンハッタン距離に変えて実験
	
	ImproveLloydClustering exa;
	ArrayList<double[]> data;
	byte[][] fruit;
	
	@Before
	public void beforetestKsplit() {
		this.data = new ArrayList<double[]>();
		double[] tmp = new double[JIGEN];
		for(int i = 0; i < DATASIZE; i++){
			tmp = new double[JIGEN];
			for(int j = 0; j < JIGEN; j++){
				tmp[j] = Math.random();
			}
			data.add(tmp);
			tmp = null;
		}
		this.exa = new ImproveLloydClustering();
	}
	
	@Test
	public void testKsplit() {
		this.fruit = exa.Ksplit(CLUSTERNUM, data);
	}
	
	@After
	public void aftertestKsplit(){
		try{
			for(int i = 0; i < CLUSTERNUM; i++){
				/*
				 * ファイルの名前を作成
				 */
				Writer out = null;
				File output = new File(CLUSTERNUM + "_" + JIGEN + "_" + DATASIZE + "Lloyd_result" + i + ".tsv");
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
