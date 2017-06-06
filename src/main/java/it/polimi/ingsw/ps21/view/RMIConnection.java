package it.polimi.ingsw.ps21.view;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.rmi.RemoteException;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.stream.Stream;

import it.polimi.ingsw.ps21.client.ClientConnection;
import it.polimi.ingsw.ps21.client.RMIClient;

public class RMIConnection implements Connection, Runnable {

	private String name;
	private Queue<String> input;
	private Queue<String> output;
	private ClientConnection client;
	public RMIConnection(String inputName) {
		name = inputName;
		input = new ArrayDeque<>();
		output = new ArrayDeque<>();
	}

	@Override
	public void run() {
		
		
	}

	@Override
	public void sendMessage(String mess) {
		try {
			client.send(mess);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	@Override
	public String getName() {
		try {
			return client.getString();
		} catch (RemoteException e) {
			return "Error";
		}
	}

	@Override
	public void setClient(ClientConnection client) {
		this.client = client;
	}

}
