package it.polimi.ingsw.ps21.view;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;

public class SocketConnectionAdder extends Thread {
	private Socket socket;
	private ConcurrentLinkedQueue<Connection> connectionsQueue;
	private ConcurrentLinkedQueue<Connection> advConnectionsQueue;

	public SocketConnectionAdder(Socket socket, ConcurrentLinkedQueue<Connection> connectionsQueue,
			ConcurrentLinkedQueue<Connection> advConnectionsQueue) {
		super();
		this.socket = socket;
		this.connectionsQueue = connectionsQueue;
		this.advConnectionsQueue = advConnectionsQueue;
	}

	public void run() {
		try {
		ObjectOutputStream out=new ObjectOutputStream(socket.getOutputStream());
		out.flush();
		ObjectInputStream in=new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
		
		
			String newName = (String)in.readObject();
			
			int chosenRules = (int)in.readObject(); // 1 for standard
																// rules, 2 for
																// advanced
																// rules

			SocketConnection newConnection = new SocketConnection(newName, socket);
			addConnectionToQueue(chosenRules, newConnection);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void addConnectionToQueue(int chosenRules, SocketConnection newConnection) {
		if (chosenRules == 1) {
			synchronized (connectionsQueue) {
				this.connectionsQueue.add(newConnection);
			
			System.out.println("\n" + newConnection.getName()
					+ "'s inbound connection added to the standard lobby in position " + connectionsQueue.size());
			}
		}

		else if (chosenRules == 2) {
			synchronized (advConnectionsQueue) {
				this.advConnectionsQueue.add(newConnection);
			
			System.out.println("\n" + newConnection.getName()
					+ "'s inbound connection added to the advanced lobby in position " + advConnectionsQueue.size());
			}
		} else
			return;
	}

}
