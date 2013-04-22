package com.rockpaperscissors.strategy;


/**
 * Top level Game Strategy class. 
 * @author minhaz
 *
 */
public class GameStrategy {
	public static final char ROCK='R';
	public static final char PAPER='P';
	public static final char SCISSOR='S';
	public static char[] gameArray={ROCK, PAPER, SCISSOR};
	/**
	 * This is used to find the pattern. Its alwasy uses this
	 * last six value to find out next move. Primarily used
	 * by HistoryMatchingStrategy.
	 */
	public int MAX_OLD_MOVES_THRESHOLD=6;



}
