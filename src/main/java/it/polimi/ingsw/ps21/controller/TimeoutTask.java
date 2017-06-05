package it.polimi.ingsw.ps21.controller;

import java.util.TimerTask;

public class TimeoutTask extends TimerTask {
	private boolean timeoutExpired;
	
	public TimeoutTask(){
		
		this.timeoutExpired=false;
	}

	@Override
	public void run() {
		  timeoutExpired= true;
		}
	
	public boolean isExpired()
	{
		return timeoutExpired;
	}
}

