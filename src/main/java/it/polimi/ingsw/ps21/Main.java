package it.polimi.ingsw.ps21;

import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps21.view.Server;

public class Main {
	
	private final static Logger LOGGER = Logger.getLogger(Main.class.getName());

	/**
	 * Nobody must create this class
	 */
	private Main(){
		
	}
	
	public static void main(String args[])
	{
		LOGGER.log(Level.INFO, "Application on Server started.");
		new Thread(new Server()).start(); //Starts the server thread.
	}

}
