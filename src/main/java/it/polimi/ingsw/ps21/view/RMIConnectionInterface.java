package it.polimi.ingsw.ps21.view;

import java.rmi.Remote;
import java.rmi.RemoteException;

import it.polimi.ingsw.ps21.client.RMIClientInterface;

public interface RMIConnectionInterface extends Remote {
	
	public void setClient(RMIClientInterface client) throws RemoteException;
	
	public void receiveMessage(String mess) throws RemoteException;
	
	public void disconnect()throws RemoteException;

}
