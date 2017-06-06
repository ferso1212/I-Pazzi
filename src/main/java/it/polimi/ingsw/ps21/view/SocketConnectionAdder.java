package it.polimi.ingsw.ps21.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;

public class SocketConnectionAdder extends Thread{
	private Socket socket;
	private ConcurrentLinkedQueue<Connection> connectionsQueue;
	private ConcurrentLinkedQueue<Connection> advConnectionsQueue;

	public SocketConnectionAdder(Socket socket, ConcurrentLinkedQueue<Connection> connectionsQueue, ConcurrentLinkedQueue<Connection> advConnectionsQueue) {
		super();
		this.socket = socket;
		this.connectionsQueue=connectionsQueue;
		this.advConnectionsQueue= advConnectionsQueue;
	}
	
	public void run()
	{
		BufferedReader in;
		try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			String newName=in.readLine(); 
			int chosenRules=Integer.parseInt(in.readLine()); //1 for standard rules, 2 for advanced rules

				SocketConnection newConnection= new SocketConnection(newName, socket);
				addConnectionToQueue(chosenRules, newConnection);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void addConnectionToQueue(int chosenRules, SocketConnection newConnection)
	{
		if(chosenRules==1)
		{
			this.connectionsQueue.add(newConnection);
			System.out.println("\n" + newConnection.getName() + "'s inbound connection added to the standard lobby in position " + connectionsQueue.size());
		}
		
		else if(chosenRules==2)
		{
			this.advConnectionsQueue.add(newConnection);
			System.out.println("\n" + newConnection.getName() + "'s inbound connection added to the advanced lobby in position " + connectionsQueue.size());
		}
		else return;
	}
	
}
