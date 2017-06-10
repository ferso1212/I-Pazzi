package it.polimi.ingsw.ps21.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps21.controller.CostChoice;
import it.polimi.ingsw.ps21.controller.MatchData;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;

public class SocketClient{
	private static final String SERVER_IP = "127.0.0.1";
	private static final int PORT = 7777;
	private final static Logger LOGGER = Logger.getLogger(SocketClient.class.getName());
	private BufferedReader socketIn;
	private PrintWriter socketOut;
	private ObjectInputStream objIn;
	private ObjectOutputStream objOut;
	private UserInterface ui;
	
	public SocketClient() {

	}

	public MatchData start(int chosenRules, String name) {
		System.out.println("\nTrying to connect to the server with TCP socket...");
		try {
			Socket socket = new Socket(SERVER_IP, PORT);
			System.out.println("\nEstablished TCP connection to the server.");
			this.socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			this.socketOut = new PrintWriter(socket.getOutputStream(), true);
			Scanner stdin = new Scanner(System.in);
			socketOut.println(name);
			socketOut.println(chosenRules);
			String socketLine = socketIn.readLine();
			if (socketLine.compareTo("Match Started") == 0) {

				while (socket.isConnected()) {
					socketLine = socketIn.readLine();
					while (socketLine != null) {
						System.out.println(socketLine);
						socketLine = socketIn.readLine();
					}

					String userInputLine = stdin.nextLine();
					socketOut.println(userInputLine);

				}
			}
			if (socket.isClosed()) {
				System.out.println("Connection closed");
				return null;
			} else {
				return null;
			}
		} catch (UnknownHostException e) {
			LOGGER.log(Level.INFO, "Unable to reach host.", e);
			System.out.println("\nUnable to reach host.");
			return null;
		} catch (IOException e) {
			LOGGER.log(Level.INFO, "Input-Output exception.", e);
			return null;
		}
	}

	private void parseAndPrintSocketInput(String input){
		String[] structuredInput=input.split("_");
		int messageNum=Integer.parseInt(structuredInput[0]);
		String command=structuredInput[1];
		switch (command) {
		case "printString": 
		{
			try {
				ui.showInfo(socketIn.readLine());
			} catch (IOException e) {
				LOGGER.log(Level.INFO, "Input-Output exception on reading a string from the socket.", e);
			}
			finally{return;}
		}

		case "costChoice": {
			try {
				int chosen = ui.reqCostChoice((ArrayList<ImmProperties>)objIn.readObject());
				socketOut.println(messageNum + "_" + chosen);
			} catch (ClassNotFoundException e) {
				LOGGER.log(Level.INFO, "CostChoice class not found, unable to read message from socket", e);
			} catch (IOException e) {
				LOGGER.log(Level.INFO, "Input-Output exception on reading an object from the socket.", e);
			}
			finally{return;}
		}
		
		case "privilegesChoice" :
		{
			int number=0;
			try {
				number=Integer.parseInt(socketIn.readLine());
			} catch (NumberFormatException n) {
				LOGGER.log(Level.WARNING, "Unable to parse the received string to integer.", n);
				
			} catch (IOException e) {
				LOGGER.log(Level.WARNING, "Input-Output exception on reading a line from the socket.", e);
				number=0;
			}
			ImmProperties[] chosenPrivileges= ui.reqPrivileges(number);
			try {
				objOut.writeObject(chosenPrivileges);
			} catch (IOException e) {
				LOGGER.log(Level.INFO, "Input-Output exception on sending the chosen privileges on the socket.", e);
			}
		}
		
		}
	}
}
