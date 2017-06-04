package it.polimi.ingsw.ps21.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Main {
	private static final String SERVER_IP="127.0.0.1"; 
	private static final int PORT = 7777;
	
	public static void main(String args[])
	{
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
			
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		
	}
	
	public Main()
	{
		
	}
}
