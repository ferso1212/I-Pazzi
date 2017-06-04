package it.polimi.ingsw.ps21.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import it.polimi.ingsw.ps21.controller.MatchData;
import it.polimi.ingsw.ps21.model.match.Match;

public class Main {

	private static boolean newMatch = true;
	private static boolean CLI = true;
	
	public static void main(String args[])
	{
<<<<<<< HEAD
		System.out.println("\nClient application started.");
		try {
			Socket socket = new Socket(SERVER_IP, PORT);
			System.out.println("Connection established");
			BufferedReader socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream())); 
			PrintWriter socketOut = new PrintWriter(socket.getOutputStream()); 
			BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
			while(true){

			String socketLine= socketIn.readLine(); 
			System.out.println(socketLine);
			String userInputLine = stdin.readLine(); 
			socketOut.println(userInputLine); 
			socketOut.flush(); }
=======
		Scanner in = new Scanner(System.in);
		while(newMatch == true){
		SocketClient client = new SocketClient(); 
		MatchData match = client.start();
		if (match != null){
			CLInterface CLImatch = new CLInterface();
			while (CLImatch.isEnded());
			System.out.println("Do you want to play another match, fucking looser?\n(Y)es\n(N)o");
			String response = in.nextLine();
>>>>>>> 2b8c6b268b0d69c0d30ab4940102b5132adfc151
			
		}
		else {System.out.println("Failed to connect to server");
			newMatch = false;
		}
		}
	}
	
	public Main()
	{
		
	}
}
