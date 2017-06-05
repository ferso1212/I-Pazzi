package it.polimi.ingsw.ps21.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import it.polimi.ingsw.ps21.controller.MatchData;

public class SocketClient {
	private static final String SERVER_IP="127.0.0.1"; 
	private static final int PORT = 7777;
	
	public SocketClient(){
		
	}
	
	
	public MatchData start(int chosenRules, String name){
	System.out.println("\nTrying to connect to the server with TCP socket...");
	try {
		Socket socket = new Socket(SERVER_IP, PORT);
		System.out.println("\nEstablished TCP connection to the server.");
		BufferedReader socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream())); 
		PrintWriter socketOut = new PrintWriter(socket.getOutputStream(), true); 
		Scanner stdin = new Scanner(System.in);
		String socketLine=""; 
		socketOut.println(name);
		socketOut.println(chosenRules);
		while(socketLine.compareTo("Match Started") != 0 && socket.isConnected()){
			socketLine = socketIn.readLine();
			System.out.println(socketLine);
			String userInputLine = stdin.nextLine();
			socketOut.println(userInputLine); 
			socketLine = socketIn.readLine();
		}
		if (socket.isClosed()) {System.out.println("Connection closed"); return null;}
		else
		{
			return null;
		}
	} catch (UnknownHostException e) {
		// TODO Auto-generated catch block
		System.out.println("Unable to reach host");
		return null;
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return null;
		}
	}
}
