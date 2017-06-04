package it.polimi.ingsw.ps21.client;

import java.io.IOException;
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
			Scanner socketIn = new Scanner(socket.getInputStream()); 
			PrintWriter socketOut = new PrintWriter(socket.getOutputStream()); 
			Scanner stdin = new Scanner(System.in);
			while(true){
				while(!socketIn.hasNextLine()){}
			String socketLine= socketIn.nextLine(); 
			System.out.println(socketLine);
			String userInputLine = stdin.nextLine(); 
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
