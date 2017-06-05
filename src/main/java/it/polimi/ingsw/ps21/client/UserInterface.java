package it.polimi.ingsw.ps21.client;

import java.util.Observable;
import java.util.Observer;

import it.polimi.ingsw.ps21.controller.AcceptedAction;
import it.polimi.ingsw.ps21.controller.BoardData;
import it.polimi.ingsw.ps21.controller.CostChoice;
import it.polimi.ingsw.ps21.controller.CouncilChoice;
import it.polimi.ingsw.ps21.controller.EffectChoice;
import it.polimi.ingsw.ps21.controller.MatchData;
import it.polimi.ingsw.ps21.controller.Message;
import it.polimi.ingsw.ps21.controller.RefusedAction;
import it.polimi.ingsw.ps21.controller.VaticanChoice;

public abstract interface UserInterface {
	
	
	public abstract void updateView(MatchData match, BoardData board);
	
	// public abstract ActionData reqAction();
	
	public abstract void showInfo(String name);
	
	public abstract void reqChoice(VaticanChoice choice);
	
	public abstract void reqChoice(CostChoice choice);
	
	public abstract void reqChoice(CouncilChoice choice);
	
	public abstract void reqChoice(EffectChoice choice);
	
	// public abstract void reqChoice(LeaderChoice choice);
	
	// public abstract void reqChoice(WorkChoice choice);
	
	public abstract void showMessage(AcceptedAction mess);
	
	public abstract void showMessage(RefusedAction mess);



}
