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
		
		}


	@Override
	public void run() {
		while(connections.size()<128);
		System.out.println("Number of connections expired");
	}
			
		


	@Override
	public Connection getNewConnection(String userName) throws RemoteException {
			if (connections.size()<128){
			RMIConnection newConnection = new RMIConnection(userName);
			connectionsQueue.add(newConnection);
			connections.add(newConnection);
			return (Connection) newConnection;}
			else return null;
	}
}

