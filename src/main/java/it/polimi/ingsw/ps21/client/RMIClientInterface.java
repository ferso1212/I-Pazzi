package it.polimi.ingsw.ps21.client;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import it.polimi.ingsw.ps21.model.properties.ImmProperties;

public interface RMIClientInterface extends Remote {

	public void receiveMessage(String message) throws RemoteException;
	
	public int setCost(ArrayList<ImmProperties> costs) throws RemoteException;
	
	public boolean vaticanChoice()throws RemoteException;
	
}
