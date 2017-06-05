package it.polimi.ingsw.ps21.client;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

import it.polimi.ingsw.ps21.view.RMIConnection;
import it.polimi.ingsw.ps21.view.RMIConnectionCreator;
import it.polimi.ingsw.ps21.view.RMIMessageBuffer;

public class RMIClient implements Remote{
	
	Registry serverRegistry;
	RMIConnection connection = null;
	public RMIClient(String username) throws RemoteException, NotBoundException{
		serverRegistry = LocateRegistry.getRegistry("localhost");
		RMIConnectionCreator connectionService = (RMIConnectionCreator) serverRegistry.lookup("RMIConnectionCreator");
		connection = connectionService.getNewConnection(username);
	}
	
	public void start(){ 
		Scanner inputScanner = new Scanner(System.in);
		while(true){
			System.out.println("Cosa vuoi fare?\n1-Leggere i messaggi dal server\n2-Mandare un messaggio al server");
			int inputChoice = inputScanner.nextInt();
			if (inputChoice == 1) {
				System.out.println(connection.getMessageFromServer());
				
			}
			else 
				if (inputChoice == 2){
					String message = inputScanner.nextLine();
					connection.sendMessage(message);
				}
				else
					System.out.println("Rincoglionito hai inserito un'opzione sbagliata");
		}
		
	}
	
	public boolean matchEnded(){
		return true;
	}

}
