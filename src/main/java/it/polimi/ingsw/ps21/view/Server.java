package it.polimi.ingsw.ps21.view;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.rmi.registry.Registry;

public class Server implements Runnable {

	private final static Logger LOGGER = Logger.getLogger(Server.class.getName());

	// private SocketConnectionsAcceptor socketAcceptor;
	// private RMIConnectionsAcceptor rmiAcceptor;
	private Lobby stdLobby;
	private Lobby advLobby;
	//private ArrayList<String> users;

	
	

	public Server() {
		this.stdLobby=new Lobby(false);
		this.advLobby=new Lobby(true);
		

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
