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

	public SocketConnectionAdder(Socket socket, ConcurrentLinkedQueue<Connection> connectionsQueue) {
		super();
		this.socket = socket;
		this.connectionsQueue=connectionsQueue;
	}
	
	public void run()
	{
		BufferedReader in;
		try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			String newName=in.readLine(); 
			int chosenRules=Integer.parseInt(in.readLine()); //1 for standard rules, 2 for advanced rules
			
			synchronized (connectionsQueue) {
				
				SocketConnection newConnection= new SocketConnection(newName, socket);
				if(chosenRules==1) connectionsQueue.add(newConnection);
				else if(chosenRules==2) advConnectionsQueue.add(newConnection);
				else return; //error
				System.out.println("\n" + newName + "'s inbound connection added to the queue in position " + connectionsQueue.size());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
