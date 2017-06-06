package it.polimi.ingsw.ps21.client;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

import it.polimi.ingsw.ps21.view.Connection;
import it.polimi.ingsw.ps21.view.RMIConnection;
import it.polimi.ingsw.ps21.view.RMIConnectionCreator;
import it.polimi.ingsw.ps21.view.RMIMessageBuffer;

public class RMIClient implements ClientConnection,Remote{
	
	Registry serverRegistry;
	Connection connection = null;
	RMIMessageBuffer input;
	RMIMessageBuffer output;
	UserInterface ui;
	public RMIClient(String username, UserInterface ui, int chosenRules) throws RemoteException, NotBoundException{
		serverRegistry = LocateRegistry.getRegistry("localhost", 5000);
		RMIConnectionCreator connectionService = (RMIConnectionCreator) serverRegistry.lookup("RMIConnectionCreator");
		connection = connectionService.getNewConnection(username, chosenRules);
		connection.setClient((ClientConnection)this);
		this.ui = ui;
		ui.showInfo("Estabilished Connection!");
	}
	
	public void start(){ 
		try {
			while(true){
			this.wait(30);
			ui.showInfo("Connection OK");
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			ui.showInfo("Awaked from interrupt");
		}
	}
	
	public boolean matchEnded(){
		return true;
	}

	@Override
	public void send(String string) throws RemoteException {
		ui.showInfo(string);
	}

	@Override
	public String getString() throws RemoteException {
		return "Not implemented yet";
	}

}
