package it.polimi.ingsw.ps21.view;

import it.polimi.ingsw.ps21.controller.EffectChoice;

public abstract interface Visitor {
	
	// TODO Vatican Chociepublic abstract choose(VaticanChoice vatican);
	// TODO CostChoice public abstract choose(CostChoice vatican);
	// TODO COuncilChoice public abstract choose(CouncilChoice vatican);
	public abstract void  choose(EffectChoice vatican);

}
