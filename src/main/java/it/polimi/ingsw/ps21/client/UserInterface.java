package it.polimi.ingsw.ps21.client;

import java.util.ArrayList;

import it.polimi.ingsw.ps21.controller.AcceptedAction;
import it.polimi.ingsw.ps21.controller.RefusedAction;
import it.polimi.ingsw.ps21.model.deck.DevelopmentCard;
import it.polimi.ingsw.ps21.model.deck.LeaderCard;
import it.polimi.ingsw.ps21.model.effect.EffectSet;
import it.polimi.ingsw.ps21.model.player.PersonalBonusTile;
import it.polimi.ingsw.ps21.model.player.PlayerColor;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;
import it.polimi.ingsw.ps21.view.ActionData;
import it.polimi.ingsw.ps21.view.EndData;
import it.polimi.ingsw.ps21.view.ExtraActionData;
import it.polimi.ingsw.ps21.view.MatchData;

public abstract interface UserInterface {
	
	public void playMatch();
	
	public abstract void updateView(MatchData match);
	
	public ActionData makeAction(int id);
	
	public abstract void showInfo(String name);
	
	public boolean reqVaticanChoice();
	
	public  int reqCostChoice(ArrayList<ImmProperties> costChoices);
		
	public  int reqEffectChoice(EffectSet[] effectChoice);
	
	public abstract int chooseLeaderCard(LeaderCard[] possibleChoices);
	
	// public abstract void reqChoice(WorkChoice choice);
	
	public abstract void showMessage(AcceptedAction mess);
	
	public abstract void showMessage(RefusedAction mess);
	
	public abstract void setID(PlayerColor id);

	public abstract ImmProperties[] reqPrivileges(int number, ImmProperties[] privilegesValues);
	
	public abstract void matchEnded(EndData data);

	public abstract int reqExtraActionChoice(ExtraActionData[] actions);

	public int reqWorkChoice(DevelopmentCard workCard, boolean costs);

	public String reqName();
	
	public boolean reqIfWantsAdvancedRules();
	
	public int chooseTile(PersonalBonusTile[] possibilities);

	public void setRules(boolean isAdvanced);
	
	public int reqCardChoice(DevelopmentCard[] possibleChoices);
	
	public int reqNumberOfServants(int max); //max is the player's number of servants
	
	public int reqLorenzoIlMagnificoChoice(LeaderCard[] possibilities);
}
