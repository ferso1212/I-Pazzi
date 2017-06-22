package it.polimi.ingsw.ps21.controller;

import java.util.TimerTask;

public class LobbyTimeoutTask extends TimerTask {
	private boolean timeoutExpired;
	private String lobbyType;
	
	public LobbyTimeoutTask(String lobbyType){
		
		this.timeoutExpired=false;
		this.lobbyType=lobbyType;
	}

	@Override
	public void run() {
		  timeoutExpired= true;
		 System.out.println("\n" + lobbyType+" lobby timeout expired." );
		}
	
	public boolean isExpired()
	{
		return timeoutExpired;
	}
}

