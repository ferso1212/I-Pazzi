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
	private ConcurrentLinkedQueue<Connection> connectionsQueue;
	private ConcurrentLinkedQueue<Connection> advConnectionsQueue;
	ArrayList<RMIConnection> connections = new ArrayList<>();
	
	public RMIConnectionAcceptor(ConcurrentLinkedQueue<Connection> connectionsQueue, ConcurrentLinkedQueue<Connection> advConnectionsQueue) throws RemoteException {
		this.connectionsQueue = connectionsQueue;
		this.advConnectionsQueue=advConnectionsQueue;
		}


	@Override
	public void run() {
		while(connections.size()<128);
		System.out.println("Number of connections expired");
	}
			
		


	@Override
	public RMIConnectionInterface getNewConnection(String userName, int chosenRules) throws RemoteException {
			if (connections.size()<128){
			RMIConnection newConnection = new RMIConnection(userName);
			addConnectionToQueue(chosenRules, newConnection);
			new Thread(newConnection).start();
			return (RMIConnectionInterface) newConnection;}
			else return null;
	}
	
	private void addConnectionToQueue(int chosenRules, RMIConnection newConnection)
	{
		if(chosenRules==1)
		{	synchronized(connectionsQueue)
			{
			this.connectionsQueue.add(newConnection);
			connections.add(newConnection);
			System.out.println("\n" + newConnection.getName() + "'s inbound connection added to the standard lobby in position " + connectionsQueue.size());
			}
		}
		
		else if(chosenRules==2)
		{	synchronized(advConnectionsQueue)
			{
			this.advConnectionsQueue.add(newConnection);
			connections.add(newConnection);
			System.out.println("\n" + newConnection.getName() + "'s inbound connection added to the advanced lobby in position " + advConnectionsQueue.size());
			}
		}
		else return;
	}
}

