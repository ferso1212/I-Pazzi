package it.polimi.ingsw.ps21.client;

import java.rmi.Remote;
import java.rmi.RemoteException;

import it.polimi.ingsw.ps21.model.properties.ImmProperties;

public interface RMIClientInterface extends Remote {

	public void sendMessage(String message) throws RemoteException;
	
	public void setCost(ImmProperties costschoices[]) throws RemoteException;
	
}
