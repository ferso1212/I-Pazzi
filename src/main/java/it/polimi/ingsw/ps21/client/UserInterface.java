package it.polimi.ingsw.ps21.client;

import java.util.Observable;
import java.util.Observer;

import it.polimi.ingsw.ps21.controller.MatchData;

public abstract class UserInterface implements Observer {
	
	@Override
	public void update(Observable arg0, Object arg1) {
		if (arg1 instanceof MatchData) 
         show((MatchData) arg1); 
		
	}

	public abstract void show(MatchData matchInfo);



}
