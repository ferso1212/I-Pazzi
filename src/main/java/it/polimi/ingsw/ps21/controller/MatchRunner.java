package it.polimi.ingsw.ps21.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import it.polimi.ingsw.ps21.view.UserHandler;

public class MatchRunner implements Runnable {
	private UserHandler[] playerHandlers;
	private MatchController controller;
	
	public MatchRunner(UserHandler...usersToAdd)
	{
		this.playerHandlers= usersToAdd.clone();
	}

	@Override
	public void run() {
		
		
	}
	
}
