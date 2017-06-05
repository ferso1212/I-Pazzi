package it.polimi.ingsw.ps21.view;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

public class RMIConnectionAcceptor implements RMIConnectionCreator, Runnable {

	Registry registry;
	RMIMessageBuffer output; // It is saved in input for the client
	RMIMessageBuffer input;
	ConcurrentLinkedQueue<Connection> connectionsQueue;
	ArrayList<RMIConnection> connections = new ArrayList<>();
	
	public RMIConnectionAcceptor(ConcurrentLinkedQueue<Connection> connectionsQueue) throws RemoteException {
		this.connectionsQueue = connectionsQueue;
		if (System.getSecurityManager() == null){
			System.setSecurityManager(new SecurityManager());
		}
		registry = LocateRegistry.getRegistry();
		input = (RMIMessageBuffer) UnicastRemoteObject.exportObject(new RMIMessageBuffer(), 0);
	}


	@Override
	public void run() {
		while(connections.size()<128);
		System.out.println("Number of connections expired");
	}
			
		


	@Override
	public RMIConnection getNewConnection(String userName) {
		try {
			if (connections.size()<128){
			RMIConnection newConnection = (RMIConnection) UnicastRemoteObject.exportObject(new RMIConnection(userName), 0);
			connectionsQueue.add(newConnection);
			connections.add(newConnection);
			return newConnection;}
			else return null;
		} catch (RemoteException e) {
			return null;
		}
	}
}

