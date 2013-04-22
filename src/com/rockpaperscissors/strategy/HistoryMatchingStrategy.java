package com.rockpaperscissors.strategy;

import java.util.ArrayList;

import android.text.TextUtils;
import android.util.Log;

/**
 * This strategy keep track of opponent move and return what user played last time.
 * This strategy works well for long data
 * @author minhaz
 *
 */
public class HistoryMatchingStrategy extends GameStrategy {
	/**
	 * user old moves
	 */
	private String moves;

	public HistoryMatchingStrategy(String oldMoves) {
		// TODO Auto-generated constructor stub
		this.moves=oldMoves;
	}
	public HistoryMatchingStrategy(){
		
	}
	public void setMoves(String moves){
		this.moves=moves;
	}
	/**
	 * Return possible number of next moves from 
	 * previous moves
	 * @return List of moves in Array list object
	 */
	public ArrayList<Character> getNextMoves(){
		int length=moves.length();
		if (length<10)  return null;
		String oldMoves=moves.substring(0, length-MAX_OLD_MOVES_THRESHOLD);
		String pattern=moves.substring(length-MAX_OLD_MOVES_THRESHOLD, length);
		return getPreviousFrequentMoves(oldMoves, pattern);
	}
	/**
	 * Return next moves. If will return number 
	 * moves after pattern search
	 * @param moves User old moves
	 * @param match pattern to match.
	 * @return Array list object.
	 */
	private ArrayList<Character> getPreviousFrequentMoves(String moves, String match){
		int[] indexes =indexOf(moves, match);
		int movesLength=moves.length();
		int textLength=match.length();
		ArrayList<Character> list=new ArrayList<Character>();
		for (int i=0;i<indexes.length;i++){
			if (indexes[i]==-1) break;
			int j=indexes[i]+textLength;
			if (j<movesLength)
				list.add(moves.charAt(j));
				
		}
		return list;
		
	}

	/**
	 * Return first indexs after pattern searching.
	 * Using java index of which uses brute force 
	 * method. I consider Boyer Moore String search
	 * Algorithm which has better run time for large 
	 * String but as this is a mobile game and user is 
	 * expected to play for longer period use of such algorithm
	 * is not necessary.
	 * @param moves
	 * @param match
	 * @return
	 */
	public int[] indexOf(String moves, String match){
		if (TextUtils.isEmpty(moves)||TextUtils.isEmpty(match) )
			return null;
		int[] indexes=new int[MAX_OLD_MOVES_THRESHOLD];
		int i=1;
		int index=moves.indexOf(match);
		indexes[0]=index;
		while (index+1<moves.length()){
			index=moves.indexOf(match, index+match.length());
			indexes[i]=index;
			i++;
			if (i>=MAX_OLD_MOVES_THRESHOLD) break;
		}
		return indexes;
	}

}
