package it.polimi.ingsw.ps21.view;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

import it.polimi.ingsw.ps21.client.ClientConnection;
import it.polimi.ingsw.ps21.client.RMIClient;
import it.polimi.ingsw.ps21.client.RMIClientInterface;
import it.polimi.ingsw.ps21.controller.BoardData;
import it.polimi.ingsw.ps21.controller.MatchData;
import it.polimi.ingsw.ps21.controller.PlayerData;
import it.polimi.ingsw.ps21.model.player.PlayerColor;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;

public class RMIConnection extends UnicastRemoteObject implements RMIConnectionInterface, Connection{
	private final static Logger LOGGER = Logger.getLogger(RMIConnection.class.getName());

	private String name;
	// Unused private Queue<String> input;
	// Unused private Queue<String> output;
	private transient RMIClientInterface client;
	public RMIConnection(String inputName) throws RemoteException{
		name = inputName;
	}


	@Override
	public void sendMessage(String mess) {
			try {
				client.receiveMessage(mess);
			} catch (RemoteException e) {
				LOGGER.log(Level.WARNING, "Error calling remote Method receiveMessage", e);			
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
		try {
			return client.setCost(costs);
		} catch (RemoteException e) {
			LOGGER.log(Level.WARNING, "Error calling remote method setCosts()", e);
			return 0;
		}
	}


	@Override
	public boolean setVaticanChoice() {
		try {
			return client.vaticanChoice();
		} catch (RemoteException e) {
			LOGGER.log(Level.WARNING, "Error calling remote method vaticanChoice", e);
			return false;
		}
	}


	@Override
	public ImmProperties[] reqPrivileges(int number) {
		try {
			return client.reqPrivileges(number);
		} catch (RemoteException e) {
			LOGGER.log(Level.WARNING, "Error calling remote method reqPrivileges", e);
			return new ImmProperties[0];
		}
	}


	@Override
	public void setID(PlayerColor player) {
		try {
			client.setId(player);
		} catch (RemoteException e) {
			LOGGER.log(Level.SEVERE, "Error setting id on remote conneciton", e);
		}
	}

}
