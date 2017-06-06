package it.polimi.ingsw.ps21.view;

import java.net.Socket;
import java.rmi.Remote;
import java.rmi.RemoteException;

import it.polimi.ingsw.ps21.client.ClientConnection;
import it.polimi.ingsw.ps21.client.RMIClient;
import it.polimi.ingsw.ps21.controller.BoardData;
import it.polimi.ingsw.ps21.controller.MatchData;
import it.polimi.ingsw.ps21.controller.PlayerData;

public abstract interface Connection{

	
	public abstract void sendMessage(String mess);
	
	public abstract String getName();
	

	public abstract void setClient(ClientConnection client);
	
	public abstract void remoteUpdate(MatchData match, BoardData board, PlayerData players[]);


}
