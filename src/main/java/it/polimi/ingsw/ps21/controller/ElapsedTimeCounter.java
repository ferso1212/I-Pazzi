package it.polimi.ingsw.ps21.controller;

public class ElapsedTimeCounter extends Thread{
	private long elapsedTime;
	private long tStart;
	private boolean enabled;

	@Override
	public void run() {
		this.tStart= System.currentTimeMillis();
		while(true)
		{
			 if(enabled) elapsedTime = System.currentTimeMillis() - this.tStart;
		}
	}
	
	public synchronized void resetCounter()
	{
		this.elapsedTime=0L;
	}
	
	public ElapsedTimeCounter()
	{
		this.elapsedTime=0L;
		this.enabled=false;
		
	}
	
	public synchronized void stopCounter()
	{
		this.enabled=false;
	}
	
	public synchronized void startCounter()
	{
		
		this.enabled=true;
		this.elapsedTime=0L;
		this.tStart=System.currentTimeMillis();

	}
	
	public long getElapsedTime()
	{
		return this.elapsedTime;
	}
	
	public boolean isEnabled()
	{
		return this.enabled;
	}
	
}
