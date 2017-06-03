package it.polimi.ingsw.ps21.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import it.polimi.ingsw.ps21.model.match.Match;
import it.polimi.ingsw.ps21.view.UserHandler;

public class Server implements Runnable{
	
	private SocketConnectionsAcceptor socketAcceptor;
	private RMIConnectionsAcceptor rmiAcceptor;
	private Match match;
	
	public Server() {
		this.socketAcceptor=new SocketConnectionsAcceptor();
		this.rmiAcceptor= new RMIConnectionsAcceptor();
		this.socketAcceptor.run();
		this.rmiAcceptor.run();
		
		
		
	}

	@Override
	public void run() {
		while(true)
		{
			this.match=new MatchRunner();
			
		}
		
	}
	
	
}
