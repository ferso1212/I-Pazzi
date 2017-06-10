package it.polimi.ingsw.ps21.view;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import it.polimi.ingsw.ps21.controller.MatchRunner;
import it.polimi.ingsw.ps21.controller.PlayerData;
import it.polimi.ingsw.ps21.controller.TimeoutTask;
import it.polimi.ingsw.ps21.model.match.Match;
import it.polimi.ingsw.ps21.model.player.PlayerColor;

public class Server implements Runnable {

	private final static Logger LOGGER = Logger.getLogger(Server.class.getName());

	// private SocketConnectionsAcceptor socketAcceptor;
	// private RMIConnectionsAcceptor rmiAcceptor;
	private Lobby stdLobby;
	private Lobby advLobby;

	
	

	public Server() {
		this.stdLobby=new Lobby("standard");
		this.advLobby=new Lobby("advanced");
		
		

		// this.socketAcceptor = new
		// SocketConnectionsAcceptor(this.connections);
		// this.rmiAcceptor = new RMIConnectionsAcceptor(this.connections);

	}

	@Override
	public void run() {
		this.stdLobby.start();
		this.advLobby.start();
		new Thread(new SocketConnectionsAcceptor(this.stdLobby.getConnections(), this.advLobby.getConnections())).start();
		try {
			Registry locRegistry = LocateRegistry.createRegistry(5000);
			RMIConnectionAcceptor rmiAcceptor = new RMIConnectionAcceptor(this.stdLobby.getConnections(), this.advLobby.getConnections());
			RMIConnectionCreator stubAcceptor = rmiAcceptor;
			locRegistry.rebind("RMIConnectionCreator", stubAcceptor);
			// new Thread(rmiAcceptor).start();
		} catch (RemoteException e) {
			LOGGER.log(Level.WARNING, "Error calling remote method", e);
		}

		System.out.println("Server started and ready to receive connections.");


	}

}
