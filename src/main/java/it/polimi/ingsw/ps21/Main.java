package it.polimi.ingsw.ps21;

import it.polimi.ingsw.ps21.view.Server;

public class Main {
	
	
	public static void main(String args[])
	{
		System.out.println("\nApplication started.");
		new Thread(new Server()).start(); //Starts the server thread.
	}

}
