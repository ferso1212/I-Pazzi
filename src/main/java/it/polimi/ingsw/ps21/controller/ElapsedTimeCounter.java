package it.polimi.ingsw.ps21.controller;

public class ElapsedTimeCounter extends Thread{
	private Integer elapsedTime;
	private long tStart;
	private boolean enabled;

	@Override
	public void run() {
		while(true)
		{
			 if(enabled) 
			 {elapsedTime = (int) ( System.currentTimeMillis() - this.tStart) / 1000;
			 
			 }
		}
	}
	
	public synchronized void resetCounter()
	{
		this.tStart = System.currentTimeMillis();
	}
	
	public ElapsedTimeCounter()
	{
		this.tStart = System.currentTimeMillis();
		this.elapsedTime=0;
		this.enabled=false;
		
	}
	
	public synchronized void stopCounter()
	{
		this.enabled=false;
	}
	
	public synchronized void startCounter()
	{
		
		this.enabled=true;

	}
	
	public int getElapsedTime()
	{
		return this.elapsedTime;
	}
	
	public boolean isEnabled()
	{
		return this.enabled;
	}
	
}
