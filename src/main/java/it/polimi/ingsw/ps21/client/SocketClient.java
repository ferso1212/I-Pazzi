package it.polimi.ingsw.ps21.client;

import java.io.BufferedInputStream;
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

import it.polimi.ingsw.ps21.controller.BoardData;
import it.polimi.ingsw.ps21.controller.CostChoice;
import it.polimi.ingsw.ps21.controller.MatchData;
import it.polimi.ingsw.ps21.controller.PlayerData;
import it.polimi.ingsw.ps21.model.player.PlayerColor;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;

public class SocketClient {
	private static final String SERVER_IP = "127.0.0.1";
	private static final int PORT = 7777;
	private final static Logger LOGGER = Logger.getLogger(SocketClient.class.getName());
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private UserInterface ui;

	public SocketClient() {

	}

	public MatchData start(int chosenRules, String name) {
		System.out.println("\nTrying to connect to the server with TCP socket...");
		try {
			Socket socket = new Socket(SERVER_IP, PORT);
			System.out.println("\nEstablished TCP connection to the server.");
			in = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
			out = new ObjectOutputStream(socket.getOutputStream());
			out.flush();

			out.writeObject(name);
			out.writeObject(chosenRules);
			String receivedString = (String)in.readObject();
			while(receivedString.compareTo("Match Started") != 0);
			NetPacket receivedPacket = (NetPacket)in.readObject();
				while (socket.isConnected()) {
					
					parseSocketInput(receivedPacket);
					receivedPacket = (NetPacket)in.readObject();

					

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
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private void parseSocketInput(NetPacket receivedPacket) {
		try {
			switch (receivedPacket.getType()) {
			case CHAT_MESSAGE: {
				// TODO use correct ui method
				// ui.printChatMess((String)input.getObject);
				break;
			}


			case COST_CHOICE: {
				int chosen = ui.reqCostChoice((ArrayList<ImmProperties>) receivedPacket.getObject());
				out.writeObject(new NetPacket(receivedPacket.getType(), chosen, receivedPacket.getNum()));
				break;

			}

			case PRIVILEGES_CHOICE: {
				ImmProperties[] chosen = ui.reqPrivileges((int) receivedPacket.getObject());
				out.writeObject(new NetPacket(receivedPacket.getType(), chosen, receivedPacket.getNum()));
				break;

			}
			case ID: {
				ui.setID((PlayerColor)receivedPacket.getObject());
				break;
			}
			case VATICAN_CHOICE:{
				boolean chosen=ui.reqVaticanChoice();
				out.writeObject(new NetPacket(receivedPacket.getType(), chosen, receivedPacket.getNum()));
				break;
			}
			case VIEW_UPDATE_REQUEST: {
				MatchData match=(MatchData)receivedPacket.getObject();
				BoardData board=null;
				PlayerData[] players= new PlayerData[receivedPacket.getAdditionalObjects().length-1];
				int i=0;
				for(Object o: receivedPacket.getAdditionalObjects())
				{
					if(i==0) board=(BoardData)o;
					else {
						players[i-1]=(PlayerData)o;
					}
					i++;
				}
				ui.updateView(match, board, players);
				break;
			}
			case GENERIC_STRING:
				//TODO method needed in UI to show a generic message ui.showMessage((String)receivedPacket.getObject());
				break;
			default:
				break;
			}
		} catch (IOException e) {
			LOGGER.log(Level.WARNING, "IOException in parsing socket input", e);
		}
	}
}
