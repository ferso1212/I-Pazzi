package it.polimi.ingsw.ps21.client;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps21.model.effect.EffectSet;
import it.polimi.ingsw.ps21.model.player.PersonalBonusTile;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;
import it.polimi.ingsw.ps21.view.ActionData;
import it.polimi.ingsw.ps21.view.MatchData;

public class SocketClient {
	private static final int PORT = 7777;
	private final static Logger LOGGER = Logger.getLogger(SocketClient.class.getName());
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private UserInterface ui;
	private Socket socket;

	public SocketClient(UserInterface ui, String hostaddress, boolean joinNewMatch) {
		try{
		this.ui=ui;
		ui.showInfo("\nTrying to connect to the server with TCP socket...");
		socket = new Socket(hostaddress, PORT);
		ui.showInfo("\nEstablished TCP connection to the server.");
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
			ui.showInfo("\nClient ready to receive from server.");
			NetPacket receivedPacket = (NetPacket)in.readObject();
				parseSocketInput(receivedPacket);
				while (socket.isConnected()) {
					receivedPacket = (NetPacket)in.readObject();
					parseSocketInput(receivedPacket);
				}
				socket.close();
			
			if (socket.isClosed()) {
				ui.showInfo("Connection closed");
				
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
				ImmProperties[] chosen = ui.reqPrivileges(response.getNumberOfAcquiredPrivileges(), response.getChoices());
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
				ActionRequestNetPacket req= (ActionRequestNetPacket)receivedPacket;
				ActionData chosenAction= ui.makeAction(req.getId());
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
				ui.showInfo("\nWaiting for match to start...");
				break;
			}
			case MATCH_END:
			{
				ui.matchEnded(((MatchEndedNetPacket)receivedPacket).getData());
				socket.close();
				break;
			}
			case LEADER_CARD_CHOICE:
			{
				LeaderChoiceRequestNetPacket req= (LeaderChoiceRequestNetPacket)receivedPacket;
				int chosen;
				if(req.isInitialChoice()) chosen=ui.chooseLeaderCard(req.getChoices());
				else chosen=ui.reqLorenzoIlMagnificoChoice(req.getChoices());
				out.writeObject(new LeaderChoiceResponseNetPacket(receivedPacket.getNum(), chosen));
				break;
			}
			case TILE_CHOICE:
			{
				int chosen=ui.chooseTile(((TileChoiceRequestNetPacket)receivedPacket).getPossibleChoices());
				out.writeObject(new TileChoiceResponseNetPacket(receivedPacket.getNum(), chosen));
				break;
			}
			case RULES:
			{
				boolean adv= ((RulesNetPacket)receivedPacket).isAdvanced();
				ui.setRules(adv);
				break;
			}
			case DEV_CARD_CHOICE:
			{
				int chosen= ui.reqCardChoice(((DevCardChoiceRequestNetPacket)receivedPacket).getChoices());
				out.writeObject(new DevCardChoiceResponseNetPacket(receivedPacket.getNum(), chosen));
				break;
			}
			case SERVANTS_CHOICE:
			{
				int chosen=ui.reqNumberOfServants(((ServantsNumRequestNetPacket)receivedPacket).getMax());
				out.writeObject(new ServantsNumResponseNetPacket(receivedPacket.getNum(), chosen));
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
