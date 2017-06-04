package it.polimi.ingsw.ps21.client;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

import it.polimi.ingsw.ps21.view.RMIMessageBuffer;

public class RMIClient implements Remote{
	
	Registry clientRegistry;
	RMIMessageBuffer in;
	RMIMessageBuffer out;
	public RMIClient(String host) throws RemoteException, NotBoundException{
		clientRegistry = LocateRegistry.getRegistry(host);
		in = (RMIMessageBuffer) clientRegistry.lookup("ServerOutput");
		out = (RMIMessageBuffer) clientRegistry.lookup("ServerInput");
	}
	
	public void start(){
		String serveroutput = in.nextLine();
		Scanner userInput = new Scanner(System.in); 
		while(true){
			if (serveroutput.compareTo("")!=0) System.out.printf(serveroutput);
			out.write(userInput.nextLine());
		}
		
	}
	
	public boolean matchEnded(){
		return true;
	}

}
