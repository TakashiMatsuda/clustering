/**
 * 
 */
package scatch;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * @author takashi
 *	リストの挙動を確かめるためのクラス
 */
public class UnderstandList {
	
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args){
		/*
		 * 適当にリストを作成
		 * 
		 * 
		 */
		
		
		LinkedList<ArrayList<Integer>> tester = new LinkedList<ArrayList<Integer>>();
		/*
		 * 順番に整数を格納
		 */
		for(int i = 0; i < 5; i++){
			ArrayList<Integer> data = new ArrayList<Integer>();
			for(int  j = 0; j < 10; j++){
				data.add(j);
			}
			tester.add(data);
		}
		
		
		/*
		 * 乱数で置き換えようとする
		 */
		
	}
	
	
}
