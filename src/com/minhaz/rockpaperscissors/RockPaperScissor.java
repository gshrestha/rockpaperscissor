package com.minhaz.rockpaperscissors;

import java.util.ArrayList;
import java.util.HashMap;

import com.rockpaperscissors.strategy.FrequencyCount;
import com.rockpaperscissors.strategy.GameStrategy;
import com.rockpaperscissors.strategy.HistoryMatchingStrategy;
import com.rockpaperscissors.strategy.RandomStrategy;
import com.rockpaperscissors.strategy.SensorStrategy;

import android.text.TextUtils;
/**
 * top level class to decide next move.
 * @author minhaz
 *
 */
public class RockPaperScissor {
	private HashMap<Character, Integer> map=null;

	public RockPaperScissor() {
		// TODO Auto-generated constructor stub
		map=new HashMap<Character, Integer>();
		map.put('R', 0);
		map.put('P', 1);
		map.put('S', 2);
	}

	/**
	 * Return next move using given strategy
	 * @param Game Strategy , use only RandomStrategy or Sensor Strategy
	 * @return index of the image in between 0 to 2
	 */
	public int getNextMove(GameStrategy gameStrategy){
		if (gameStrategy instanceof RandomStrategy)
			return ((RandomStrategy) gameStrategy).getNextMove();
		if (gameStrategy instanceof SensorStrategy){
			return  ((SensorStrategy) gameStrategy).getNextMove();
		}
			

		return -1;

	}
	/**
	 * Return next move based on given strategy and previous moves
	 * @param moves gamers old moves
	 * @param gameStrategy strategy to selct
	 * @return return index from 0 to 2 and -1 for failed
	 */
	public int getNextMove(String moves, GameStrategy gameStrategy){
		if ((gameStrategy==null)||(TextUtils.isEmpty(moves))) return -1;
		if (gameStrategy instanceof FrequencyCount)
			return ((FrequencyCount) gameStrategy).getNextMove(moves);
		if (gameStrategy instanceof HistoryMatchingStrategy){
			ArrayList<Character> list=((HistoryMatchingStrategy) gameStrategy).getNextMoves();
			if ((list==null)||(list.size()==0)) 
				return new RandomStrategy().getNextMove();
			if (list.size() ==1) return map.get(list.get(0));
			else{
				//seems like number of possible moves are too many
				//lets do a frequency count to decide further
				int i=0;
				FrequencyCount strategy=new FrequencyCount();
				StringBuilder builder=new StringBuilder();
				while (i<list.size()){
					builder.append(list.get(i));
					i++;
				}
				return map.get(strategy.getNextMove(new String(builder.toString())));
			}
		}
		return -1;
	}

}
