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
import it.polimi.ingsw.ps21.model.player.PlayerColor;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;

public abstract interface Connection{

	
	public void sendMessage(String mess);
	
	public String getName();
	
	public void remoteUpdate(MatchData match, BoardData board, PlayerData players[]);
	
	public int reqChoice(ArrayList<ImmProperties> costs);

	public boolean setVaticanChoice();
	
	public ImmProperties[] reqPrivileges(int number);
	
	public void setID(PlayerColor player);

}
