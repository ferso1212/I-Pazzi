package it.polimi.ingsw.ps21.client;

import java.util.ArrayList;

import it.polimi.ingsw.ps21.controller.AcceptedAction;
import it.polimi.ingsw.ps21.controller.BoardData;
import it.polimi.ingsw.ps21.controller.MatchData;
import it.polimi.ingsw.ps21.controller.PlayerData;
import it.polimi.ingsw.ps21.controller.RefusedAction;
import it.polimi.ingsw.ps21.model.actions.ExtraAction;
import it.polimi.ingsw.ps21.model.deck.DevelopmentCard;
import it.polimi.ingsw.ps21.model.deck.LeaderCard;
import it.polimi.ingsw.ps21.model.effect.EffectSet;
import it.polimi.ingsw.ps21.model.player.PlayerColor;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;
import it.polimi.ingsw.ps21.view.ActionData;
import it.polimi.ingsw.ps21.view.ExtraActionData;

public abstract interface UserInterface {
	
	public void playMatch();
	
	public abstract void updateView(MatchData match);
	
	public ActionData makeAction();
	
	public abstract void showInfo(String name);
	
	public boolean reqVaticanChoice();
	
	public  int reqCostChoice(ArrayList<ImmProperties> costChoices);
		
	public  int reqEffectChoice(EffectSet[] effectChoice);
	
	public abstract int chooseLeaderCard(LeaderCard[] possibleChoices);
	
	// public abstract void reqChoice(WorkChoice choice);
	
	public abstract void showMessage(AcceptedAction mess);
	
	public abstract void showMessage(RefusedAction mess);
	
	public abstract void setID(PlayerColor id);

	public abstract String nextInput();

	public abstract ImmProperties[] reqPrivileges(int number, ImmProperties[] privilegesValues);
	
	public abstract void matchEnded();

	public abstract int reqExtraActionChoice(ExtraActionData[] actions);

	public int reqWorkChoice(DevelopmentCard workCard);



}
