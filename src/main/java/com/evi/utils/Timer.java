package com.evi.utils;
/*
 * This is the timer class - for each testcase a Timer is created to time each test
 */
public class Timer {
	private long start;
	private long end;
	public Timer() {
		reset();
	}
	public void start() {
		start = System.currentTimeMillis();
	}
	public void stop() {
		
		end = System.currentTimeMillis();
	}
	public long getDuration() {
		return (end - start);
	}
	public void reset() {
		start = 0L;
		end = 0L;
	}
	public boolean withinTime(long timeOut){
		if(this.getDuration() > timeOut){
			return false;
		}else{
			return true;
		}
	}
}