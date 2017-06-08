package it.polimi.ingsw.ps21.view;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;
import java.util.stream.Stream;

import it.polimi.ingsw.ps21.client.ClientConnection;
import it.polimi.ingsw.ps21.client.RMIClient;
import it.polimi.ingsw.ps21.client.RMIClientInterface;
import it.polimi.ingsw.ps21.controller.BoardData;
import it.polimi.ingsw.ps21.controller.MatchData;
import it.polimi.ingsw.ps21.controller.PlayerData;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;

public class RMIConnection extends UnicastRemoteObject implements RMIConnectionInterface, Connection{

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
	public void sendMessage(String mess) {
			try {
				client.receiveMessage(mess);
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

	@Override
	public void receiveMessage(String mess) throws RemoteException {
		System.out.println(mess);
	}

	@Override
	public void disconnect() throws RemoteException {
		client=null;
	}


	@Override
	public int reqChoice(ArrayList<ImmProperties> costs) {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public boolean setVaticanChoice() {
		try {
			return client.vaticanChoice();
		} catch (RemoteException e) {
			e.printStackTrace();
			return false;
		}
	}

}
