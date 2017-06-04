package it.polimi.ingsw.ps21.view;

import java.util.concurrent.ConcurrentLinkedQueue;

import it.polimi.ingsw.ps21.controller.ConnectionsAcceptor;

public class RMIConnectionsAcceptor extends ConnectionsAcceptor implements Runnable{

	public RMIConnectionsAcceptor(ConcurrentLinkedQueue<Connection> connectionsQueue) {
		super(connectionsQueue);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
