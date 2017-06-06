package it.polimi.ingsw.ps21.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Logger;

import it.polimi.ingsw.ps21.controller.MatchData;
import it.polimi.ingsw.ps21.model.match.Match;
import it.polimi.ingsw.ps21.model.match.MatchFactory;

public class ClientMain {

	private static boolean newMatch = true;
	private static boolean CLI = true;
	private final static Logger LOGGER = Logger.getLogger(ClientMain.class.getName());
	
	public static void main(String args[])
	{
		System.out.println("\nClient application started.");
    	Scanner in = new Scanner(System.in);
    	
    	int chosenConnection=0;
    	while(chosenConnection!=1 && chosenConnection!=2)
    	{
    	System.out.println("\nChoose the connection method to use: \n1 Socket \n2 RMI");
    	try {
			chosenConnection= in.nextInt();
		} catch (InputMismatchException e) {
			chosenConnection=0;
		}
    	in.nextLine();
    	if((chosenConnection!=1 && chosenConnection!=2)) System.out.println("\nInvalid choice.");
    	}
    	System.out.println("\nInsert your name: ");
    	
    	String name= in.nextLine();
    	//--    	
    	int chosenRules=0;
    	while(chosenRules!=1 && chosenRules!=2)
    	{
    	System.out.println("\nChoose the rules that you want to use in the game: \n1 Standard \n 2 Advanced");
    	try {
			chosenRules= in.nextInt();
		} catch (InputMismatchException e) {
			chosenRules=0;
		}
    	if(chosenRules!=1 && chosenRules!=2) System.out.println("\nInvalid choice.");
    	}
    	
    	
    	
		while(newMatch == true){
		
			if (chosenConnection==1) {
				SocketClient client = new SocketClient(); 
					MatchData match = client.start(chosenRules, name);
					if (match != null){
								CLInterface CLImatch = new CLInterface();
									while (CLImatch.isEnded());
										System.out.println("Do you want to play another match, fucking looser?\n(Y)es\n(N)o");
											String response = in.nextLine();			
							}
								else {System.out.println("Failed to connect to server");
								newMatch = false;
								}
			}
			else{
				try {
					RMIClient rmiclient = new RMIClient(name);
		
				} catch (RemoteException | NotBoundException e) {
					System.out.println("Failed to connect to server");
					newMatch = false;
				}
			}
						
		}
		}
	
	public ClientMain()
	{
		
	}
}
