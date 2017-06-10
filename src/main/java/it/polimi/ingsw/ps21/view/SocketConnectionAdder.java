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
		SocketConnection newInboundConnection= new SocketConnection(socket);
		addConnectionToQueue(newInboundConnection);

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
					+ "'s inbound connection added to the advanced lobby in position " + advConnectionsQueue.size());
			}
		}
	}

}
