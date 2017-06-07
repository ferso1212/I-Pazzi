package it.polimi.ingsw.ps21.view;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.stream.Stream;

import it.polimi.ingsw.ps21.client.ClientConnection;
import it.polimi.ingsw.ps21.client.RMIClient;
import it.polimi.ingsw.ps21.client.RMIClientInterface;
import it.polimi.ingsw.ps21.controller.BoardData;
import it.polimi.ingsw.ps21.controller.MatchData;
import it.polimi.ingsw.ps21.controller.PlayerData;

public class RMIConnection extends UnicastRemoteObject implements RMIConnectionInterface, Connection, Runnable {

	private String name;
	private Queue<String> input;
	private Queue<String> output;
	private RMIClientInterface client;
	public RMIConnection(String inputName) throws RemoteException{
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
				client.sendMessage(mess);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void setClient(RMIClientInterface client) {
		this.client = client;
		sendMessage("Connected");
	}

	@Override
	public void remoteUpdate(MatchData match, BoardData board, PlayerData[] players) {
		
	}

}
