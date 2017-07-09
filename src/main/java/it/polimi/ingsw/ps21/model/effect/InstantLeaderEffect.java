package it.polimi.ingsw.ps21.model.effect;


import it.polimi.ingsw.ps21.model.deck.Requirement;


public abstract class InstantLeaderEffect extends LeaderEffect{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1248622228845456305L;

	public InstantLeaderEffect(Requirement[] reqs) {
		super(reqs);
	}
	
	@Override
	public void resetActivation(){
		activated = false;
	}
	
}
