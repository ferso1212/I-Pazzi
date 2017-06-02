package it.polimi.ingsw.ps21.view;

import it.polimi.ingsw.ps21.controller.AcceptedAction;
import it.polimi.ingsw.ps21.controller.CostChoice;
import it.polimi.ingsw.ps21.controller.CouncilChoice;
import it.polimi.ingsw.ps21.controller.EffectChoice;
import it.polimi.ingsw.ps21.controller.RefusedAction;
import it.polimi.ingsw.ps21.controller.VaticanChoice;
import it.polimi.ingsw.ps21.controller.WorkMessage;

public abstract interface Visitor {
	
	public abstract void visit(VaticanChoice choice);
	public abstract void visit(CostChoice choice);
	public abstract void visit(CouncilChoice choice);
	public abstract void visit(EffectChoice choice);
	public abstract void visit(WorkMessage message);
	public abstract void visit(AcceptedAction message);
	public abstract void visit(RefusedAction message);


}
