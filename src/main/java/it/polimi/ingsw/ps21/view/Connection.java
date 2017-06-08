package it.polimi.ingsw.ps21.view;

import java.net.Socket;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import it.polimi.ingsw.ps21.client.ClientConnection;
import it.polimi.ingsw.ps21.client.RMIClient;
import it.polimi.ingsw.ps21.controller.BoardData;
import it.polimi.ingsw.ps21.controller.MatchData;
import it.polimi.ingsw.ps21.controller.PlayerData;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;

public abstract interface Connection{

	
	public abstract void sendMessage(String mess);
	
	public abstract String getName();
	
	public abstract void remoteUpdate(MatchData match, BoardData board, PlayerData players[]);
	
	public abstract int reqChoice(ArrayList<ImmProperties> costs);

	public abstract boolean setVaticanChoice();

}
