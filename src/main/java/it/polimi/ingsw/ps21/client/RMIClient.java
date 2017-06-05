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
	RMIMessageBuffer in;
	RMIMessageBuffer out;
	RMIConnection connection = null;
	public RMIClient(String host) throws RemoteException, NotBoundException{
		serverRegistry = LocateRegistry.getRegistry(host);
		in = (RMIMessageBuffer) serverRegistry.lookup("ServerOutput");
		out = (RMIMessageBuffer) serverRegistry.lookup("ServerInput");
		RMIConnectionCreator connectionService = (RMIConnectionCreator) serverRegistry.lookup("RMIConnectionCreator");
	}
	
	public void start(){
		String serveroutput = in.nextLine();
		Scanner userInput = new Scanner(System.in); 
		while((serveroutput.compareTo("Match Started") != 0)){
			if (serveroutput.compareTo("")!=0) System.out.printf(serveroutput);
			out.write(userInput.nextLine());
		}
		try {
			serverRegistry.lookup(userInput + " connection");
		} catch (RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public boolean matchEnded(){
		return true;
	}

}
