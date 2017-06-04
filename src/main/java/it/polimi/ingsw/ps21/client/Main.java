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
import java.util.Scanner;

import it.polimi.ingsw.ps21.controller.MatchData;
import it.polimi.ingsw.ps21.model.match.Match;

public class Main {

	private static boolean newMatch = true;
	private static boolean CLI = false;
	
	public static void main(String args[])
	{
		System.out.println("\nClient application started.");
    	Scanner in = new Scanner(System.in);
		while(newMatch == true){
		
			if (CLI) {
				SocketClient client = new SocketClient(); 
					MatchData match = client.start();
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
					RMIClient rmiclient = new RMIClient("localhost");
				} catch (RemoteException | NotBoundException e) {
					System.out.println("Failed to connect to server");
					newMatch = false;
				}
			}
						
		}
		}
	
	public Main()
	{
		
	}
}
