package it.polimi.ingsw.ps21.client;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps21.controller.MatchData;
import it.polimi.ingsw.ps21.model.effect.EffectSet;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;
import it.polimi.ingsw.ps21.view.ActionData;
import it.polimi.ingsw.ps21.view.RulesChoiceResponseNetPacket;
import it.polimi.ingsw.ps21.view.WorkChoiceRequestNetPacket;
import it.polimi.ingsw.ps21.view.WorkChoiceResponseNetPacket;

public class SocketClient {
	private static final String SERVER_IP = "127.0.0.1";
	private static final int PORT = 7777;
	private final static Logger LOGGER = Logger.getLogger(SocketClient.class.getName());
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private UserInterface ui;
	private Socket socket;

	public SocketClient(UserInterface ui, boolean joinNewMatch) {
		try{
		this.ui=ui;
		System.out.println("\nTrying to connect to the server with TCP socket...");
		socket = new Socket(SERVER_IP, PORT);
		System.out.println("\nEstablished TCP connection to the server.");
		in = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
		out = new ObjectOutputStream(socket.getOutputStream());
		out.reset();
		} catch(IOException e)
		{
			LOGGER.log(Level.INFO, "Input-Output exception.", e);
		}
		
			try {
				out.writeObject(new InitNetPacket(0, joinNewMatch));
			} catch (IOException e) {
		LOGGER.log(Level.SEVERE, "Unable to send initial packet from client due to IOException", e);
			}
		
		
	}
	

	public boolean start() {
		
		try {
			
			//StartInfoNetPacket initialInfos = new StartInfoNetPacket(0, chosenRules, name);
			//out.writeObject(initialInfos);
			System.out.println("\nClient ready to receive from server.");
			NetPacket receivedPacket = (NetPacket)in.readObject();
				parseSocketInput(receivedPacket);
				while (socket.isConnected()) {
					
					receivedPacket = (NetPacket)in.readObject();
					parseSocketInput(receivedPacket);
				}
				socket.close();
			
			if (socket.isClosed()) {
				System.out.println("Connection closed");
				
			} else {
				
			}
		} catch (UnknownHostException e) {
			LOGGER.log(Level.INFO, "Unable to reach host.", e);
			System.out.println("\nUnable to reach host.");
			
		} catch (IOException e) {
			LOGGER.log(Level.INFO, "Input-Output exception.", e);
			
		} catch (ClassNotFoundException e) {
			LOGGER.log(Level.INFO, "Class not found exception.", e);
		}
			return false;
		
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
				
				int chosen = ui.reqCostChoice(((CostChoiceRequestNetPacket)receivedPacket).getCostChoices());
				out.writeObject(new CostChoiceResponseNetPacket(receivedPacket.getNum(), chosen));
				break;

			}

			case PRIVILEGES_CHOICE: {
				PrivilegesChoiceRequestNetPacket response= (PrivilegesChoiceRequestNetPacket)receivedPacket;
				ImmProperties[] chosen = ui.reqPrivileges(response.getNum(), response.getChoices());
				out.writeObject(new PrivilegesChoiceResponseNetPacket(receivedPacket.getNum(), chosen));
				break;

			}
			case VATICAN_CHOICE:{
				boolean chosen=ui.reqVaticanChoice();
				out.writeObject(new VaticanChoiceResponseNetPacket(receivedPacket.getNum(), chosen));
				break;
			}
			case VIEW_UPDATE_REQUEST: {
				MatchData match=((ViewUpdateRequestNetPacket)receivedPacket).getMatch();
				ui.updateView(match);
				break;
			}
			case GENERIC_STRING:{
				
				ui.showInfo(((GenericStringNetPacket)receivedPacket).getStr());
				break;}
			case MATCH_STARTED_NOTIFICATION: {
				ui.playMatch();
				break;
			}
			case ACTION: {
				ActionData chosenAction= ui.makeAction();
				out.writeObject(new ActionResponseNetPacket(receivedPacket.getNum(), chosenAction));
				break;
			}
			case EXTRA_ACTION_CHOICE: {
				int chosen=ui.reqExtraActionChoice(((ExtraActionChoiceRequestNetPacket)receivedPacket).getActions());
				out.writeObject(new ExtraActionChoiceResponseNetPacket(receivedPacket.getNum(), chosen));
				break;
			}
			case PLAYER_ID: {
				ui.setID(((PlayerIdNetPacket)receivedPacket).getId());
				break;
			}
			case EFFECT_CHOICE:
			{
				int chosen = ui.reqEffectChoice(((EffectChoiceRequestNetPacket)receivedPacket).getPossibleEffects());
				out.writeObject(new EffectChoiceResponseNetPacket(receivedPacket.getNum(), chosen));
				break;
			}
			case WORK_CHOICE:
			{
				int chosen= ui.reqWorkChoice(((WorkChoiceRequestNetPacket)receivedPacket).getCard());
				out.writeObject(new WorkChoiceResponseNetPacket(receivedPacket.getNum(), chosen));
				break;
			}
			case NAME:
			{
				String chosenName= ui.reqName();
				out.writeObject(new NameResponseNetPacket(receivedPacket.getNum(), chosenName));
				break;
			}
			case RULES_CHOICE:
			{
				boolean wantsAdvRules= ui.reqIfWantsAdvancedRules();
				out.writeObject(new RulesChoiceResponseNetPacket(receivedPacket.getNum(), wantsAdvRules));
				break;
			}
			default:
				break;
			}
		} catch (IOException e) {
			LOGGER.log(Level.WARNING, "IOException in parsing socket input", e);
		}
	}
}
