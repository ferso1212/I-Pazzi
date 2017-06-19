package it.polimi.ingsw.ps21.view;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class SocketConnectionAdder extends Thread {
	private Socket socket;
	private ConcurrentLinkedQueue<Connection> connectionsQueue;
	private ConcurrentLinkedQueue<Connection> advConnectionsQueue;
	private ArrayList<String> names;
	private ConcurrentHashMap<String, UserHandler> playingUsers;

	public SocketConnectionAdder(Socket socket, ConcurrentLinkedQueue<Connection> connectionsQueue,
			ConcurrentLinkedQueue<Connection> advConnectionsQueue, ArrayList<String> names, ConcurrentHashMap<String, UserHandler> playingUsers) {
		super();
		this.socket = socket;
		this.connectionsQueue = connectionsQueue;
		this.advConnectionsQueue = advConnectionsQueue;
		this.playingUsers=playingUsers;
	}

	public void run() {
		SocketConnection newInboundConnection = new SocketConnection(socket);

		String name;
			if(newInboundConnection.wantsNewMatch())
			{	
			boolean alreadyExists;
				do
				{
					name = newInboundConnection.reqName();
					synchronized (names) {
						alreadyExists = names.contains(name);
						if (!alreadyExists) names.add(name);
					}
					if(alreadyExists) newInboundConnection.sendMessage("This name already exists. Please choose another name. ");
					else addConnectionToQueue(newInboundConnection);
				}while(alreadyExists && newInboundConnection.isConnected());
				
			}
			else
			{
				boolean existsInMatches;
				do
				{
					name = newInboundConnection.reqName();
					synchronized(playingUsers)
					{
						existsInMatches=playingUsers.containsKey(name);
					}
					if(!existsInMatches) newInboundConnection.sendMessage("Name not found.");
				}while(!existsInMatches && newInboundConnection.isConnected());
				if(existsInMatches) rejoin(newInboundConnection);
			}
		

		

	}

	private void addConnectionToQueue(SocketConnection newConnection) {
		if (!newConnection.isAdvanced()) {
			synchronized (connectionsQueue) {
				this.connectionsQueue.add(newConnection);

				System.out.println("\n" + newConnection.getName()
						+ "'s inbound connection added to the standard lobby in position " + connectionsQueue.size());
			}
		}

		else {
			synchronized (advConnectionsQueue) {
				this.advConnectionsQueue.add(newConnection);

				System.out.println("\n" + newConnection.getName()
						+ "'s inbound connection added to the advanced lobby in position "
						+ advConnectionsQueue.size());
			}
		}
	}
	
	private void rejoin(SocketConnection newConnection)
	{
		(playingUsers.get(newConnection.getName())).setConnection(newConnection);
	}

}
