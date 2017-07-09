package it.polimi.ingsw.ps21.view;

import java.util.ArrayList;

import it.polimi.ingsw.ps21.controller.WorkMessage;
import it.polimi.ingsw.ps21.model.actions.ExtraAction;
import it.polimi.ingsw.ps21.model.deck.DevelopmentCard;
import it.polimi.ingsw.ps21.model.deck.LeaderCard;
import it.polimi.ingsw.ps21.model.effect.EffectSet;
import it.polimi.ingsw.ps21.model.player.PersonalBonusTile;
import it.polimi.ingsw.ps21.model.player.PlayerColor;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;

public abstract interface Connection{
	
	public void matchStarted();
	
	public void sendMessage(String mess);
	
	public String getName();
	
	public void remoteUpdate(MatchData match) throws DisconnectedException;
	
	public int reqCostChoice(ArrayList<ImmProperties> costs) throws DisconnectedException;

	public boolean reqVaticanChoice() throws DisconnectedException;
	
	public ImmProperties[] reqPrivilegesChoice(int number, ImmProperties[] privilegesValue) throws DisconnectedException;
	
	public void setID(PlayerColor player);
	
	public int reqExtraActionChoice(ExtraActionData[] actions) throws DisconnectedException;
	
	public ActionData reqAction(int id) throws DisconnectedException;

	public boolean isConnected();
	
	public EffectSet reqEffectChoice(EffectSet[] possibleEffects) throws DisconnectedException;

	public int reqWorkChoice(DevelopmentCard message) throws DisconnectedException;

	public void matchEnded(EndData data);
	
	public int reqLeaderCardChoice(LeaderCard[] choices);
	
	public int reqPersonalTileChoice(PersonalBonusTile[] choices);
	
	public void setRules(boolean isAdvanced);
	
	public int reqCardChoice(DevelopmentCard[] possibleChoices) throws DisconnectedException;
	
	public int chooseNumberOfServants(int max);// max is the player's number of servants.
}
