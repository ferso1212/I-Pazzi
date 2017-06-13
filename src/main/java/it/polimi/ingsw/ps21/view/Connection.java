package it.polimi.ingsw.ps21.view;

import java.util.ArrayList;

import it.polimi.ingsw.ps21.controller.BoardData;
import it.polimi.ingsw.ps21.controller.MatchData;
import it.polimi.ingsw.ps21.controller.PlayerData;
import it.polimi.ingsw.ps21.model.player.PlayerColor;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;

public abstract interface Connection{

	public void matchStarted();
	
	public void sendMessage(String mess);
	
	public String getName();
	
	public void remoteUpdate(MatchData match, BoardData board, PlayerData players[]);
	
	public int reqCostChoice(ArrayList<ImmProperties> costs);

	public boolean reqVaticanChoice();
	
	public ImmProperties[] reqPrivilegesChoice(int number);
	
	public void setID(PlayerColor player);
	
	public int reqExtraActionChoice(ActionData[] actions);
	
	public ActionData reqAction() throws InvalidActionException;

}
