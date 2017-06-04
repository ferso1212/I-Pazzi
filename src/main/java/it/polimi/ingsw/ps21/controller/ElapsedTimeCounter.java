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
			if(enabled) elapsedTime += System.currentTimeMillis() - tStart;
		}
	}
	
	public void resetCounter()
	{
		this.elapsedTime=0L;
		this.tStart= System.currentTimeMillis();
		
	}
	
	public ElapsedTimeCounter()
	{
		this.elapsedTime=0L;
		this.enabled=false;
		
	}
	
	public void stopCounter()
	{
		this.enabled=false;
	}
	
	public void startCounter()
	{
		this.tStart= System.currentTimeMillis();
		this.enabled=true;
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
