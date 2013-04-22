package com.rockpaperscissors.strategy;

import java.util.HashMap;

import android.text.TextUtils;
import android.util.Log;

/**
 * This strategy look into user old moves and decide next move
 * based of highest occurrence.
 * @author minhaz
 *
 */
public class FrequencyCount extends GameStrategy{
	public static final String TAG="FrequencyCount";
	private int nextSelectionIndex;

	public FrequencyCount() {
	}
	public char getNextMove(String moves){
		HashMap<Character, Integer> frequency=countFrequency(moves);
		return getMaxFrequency(frequency);
	}
	private HashMap<Character, Integer> countFrequency(String moves){
		HashMap<Character, Integer> frequency=new HashMap<Character, Integer>();
		if (TextUtils.isEmpty(moves)) return null;
		int length=moves.length();
		int i=0;
		while (i<length){
			char ch=moves.charAt(i);
			Log.d(TAG, ""+ ch);
			if (frequency.containsKey(ch))
				frequency.put(ch, frequency.get(ch)+1);
			else
				frequency.put(ch, 1);
			i++;
		}
		return frequency;
		
	}
	/**
	 * Iterate through hash map and return maximum frequency from
	 * @param frequency
	 * @return
	 */
	private char getMaxFrequency(HashMap<Character, Integer> frequency){
		int i=0, current_value=0;
		int value=current_value;
		while (i<3){
			if (frequency.containsKey(gameArray[i])){
				current_value=frequency.get(gameArray[i]);
			}
			if (value<current_value){
				value=current_value;
				nextSelectionIndex=i;
			}
			i++;
		}
		return gameArray[nextSelectionIndex];
	}

}
