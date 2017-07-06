package it.polimi.ingsw.ps21.client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ssl.HostnameVerifier;

import it.polimi.ingsw.ps21.client.GUI.GUInterface;

public class ClientMain {

	private static boolean newMatch = true;
	private static boolean CLI = true;
	private final static Logger LOGGER = Logger.getLogger(ClientMain.class.getName());
	private final static int RMI_PORT = 5000;
	private static UserInterface ui;
	private static String hostaddress;
	
	public static void main(String [] args)
	{
		hostaddress =  System.getProperty("server.hostname");
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
			LOGGER.log(Level.INFO, "Invalid input", e);
		}
    	in.nextLine();
    	if((chosenConnection!=1 && chosenConnection!=2)) System.out.println("\nInvalid choice.");
    	}
    	
    	int chosenInterface=0;
    	while(chosenInterface!=1 && chosenInterface!=2)
    	{
    		
    	System.out.println("\nChoose the interface that you want to use in the game: \n1 Command Line Interface \n 2 Graphical User Interface");
        try {
			chosenInterface= in.nextInt();
    	} catch (InputMismatchException e) {
			 LOGGER.log(Level.INFO, "Invalid input", e);
			}
    	}
    	int chosenJoin=0;
    	while(chosenJoin!=1 && chosenJoin!=2)
    	{
    		
    	System.out.println("\nWhat do you want to do? \n1 Join a new match \n 2 Join an existing match");
        try {
			chosenJoin= in.nextInt();
    	} catch (InputMismatchException e) {
			 LOGGER.log(Level.INFO, "Invalid input", e);
			}
        if (chosenInterface==1) ui = new CLInterface();
    	else {
    		ui = new GUInterface();
		}
    	}
    	
			if (chosenConnection==1) {
				SocketClient client = new SocketClient(ui, hostaddress, parseChoice(chosenJoin)); 
				client.start();
					
			}
			else{
				try {
					RMIClient rmiclient = new RMIClient(ui, hostaddress, RMI_PORT, parseChoice(chosenJoin));
					rmiclient.start();
				} catch (RemoteException | NotBoundException e) {
					System.out.println("Failed to connect to server through RMI.");
					LOGGER.log(Level.WARNING, "RMI Connection failed", e);
					newMatch = false;
				}
			}
		}
	
	/**
	 * Nobody else must create this class
	 */
	private ClientMain()
	{
		
	}
	
	private static boolean parseChoice(int input)
	{
		if(input==1) return true;
		else return false;
	}
}
