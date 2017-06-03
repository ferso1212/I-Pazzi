package it.polimi.ingsw.ps21.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import it.polimi.ingsw.ps21.view.UserHandler;

public class MatchRunner implements Runnable {
	private final static int TIMEOUT=60; //the seconds that the server will wait once 2 players joined
	private final static int MAX_PLAYERS_NUM=4; //The match is created if the max number of players is reached or if the timeout expires.
	
	private List<UserHandler> players = Collections.synchronizedList(new ArrayList<UserHandler>());

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	
}
