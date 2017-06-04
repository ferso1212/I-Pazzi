package it.polimi.ingsw.ps21.client;

import java.net.Socket;

public class Main {
	private static String ip; 
	private int port;
	
	public static void main(String args[])
	{
		System.out.println("\nClient application started.");
		Socket socket = new Socket(ip, port); 
		System.out.println("Connection established");
	}
	
	public Main()
	{
		
	}
}
