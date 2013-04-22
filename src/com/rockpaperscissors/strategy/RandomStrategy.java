package com.rockpaperscissors.strategy;

import static java.lang.Math.random;

/**
 * Random Strategy depend on pusudo random numbers
 * @author minhaz
 *
 */
public class RandomStrategy extends GameStrategy {
	public int getNextMove() {
		// TODO Auto-generated method stub
		//mutilpy by 10 as seems some of psudo random numbers are is 
		//decimal number
		return (int)(random()*10)%3;
	}


}
