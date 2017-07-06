package it.polimi.ingsw.ps21.controller;

import java.util.TimerTask;
import java.util.concurrent.Semaphore;

public class LobbyTimeoutTask extends TimerTask {
	private boolean timeoutExpired;
	private String lobbyType;
	private Semaphore sem;
	
	public LobbyTimeoutTask(String lobbyType, Semaphore sem){
		
		this.timeoutExpired=false;
		this.lobbyType=lobbyType;
		this.sem=sem;
	}

	@Override
	public void run() {
		  timeoutExpired= true;
		  if(sem.availablePermits()==0) sem.release();
		 System.out.println("\n" + lobbyType + " lobby timeout expired." );
		}
	
	public boolean isExpired()
	{
		return timeoutExpired;
	}
}

