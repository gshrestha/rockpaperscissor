package com.rockpaperscissors.strategy;

import android.hardware.SensorEvent;


/**
 * uses device sensor data to get next move
 * @author minhaz
 *
 */
public class SensorStrategy extends GameStrategy {
	//Store previous x coordinate information
	private float lastX;
	//Store previous Y coordinate information
	private float lastY;
	//Store previous z coordinate information
	private float lastZ;
	//Store previous time information.
	private long lastTime;
	/**
	 * Added thrshold value to make the move little
	 * stable
	 */
	private static final int THRESHOLD = 3000;
	private SensorEvent event;
	
	public SensorStrategy(){
		
	}
	public void setEvent(SensorEvent event){
		this.event=event;
	}
	
	/**
	 * calculate next move from android accelometer event
	 * @param event Sensor event 
	 * @return index of the image in between 0 to 2
	 */
	public int getNextMove(){
		long current_time=System.currentTimeMillis();
		float x = event.values[0];
	    float y = event.values[1];
	    float z = event.values[2];
	    long timeDiff=current_time-lastTime;
	    if (timeDiff<100) return -1;
	    float currentShake=Math.abs(x+y+z-lastX-lastY-lastZ)/timeDiff*10000;
	    
	    lastX=x;
	    lastY=y;
	    lastZ=z;
	    lastTime=current_time;
	    if (currentShake>THRESHOLD) return (int)currentShake%3;
	    return -1;
	}



}
