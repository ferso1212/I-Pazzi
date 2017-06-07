package it.polimi.ingsw.ps21.view;

import java.rmi.Remote;
import java.rmi.RemoteException;

import it.polimi.ingsw.ps21.client.RMIClient;
import it.polimi.ingsw.ps21.client.RMIClientInterface;

public interface RMIConnectionInterface extends Remote {
	
	public void setClient(RMIClientInterface client) throws RemoteException;

}
