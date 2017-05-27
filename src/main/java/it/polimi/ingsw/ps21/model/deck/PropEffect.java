package it.polimi.ingsw.ps21.model.deck;

import it.polimi.ingsw.ps21.model.properties.ImmProperties;

public class PropEffect extends Effect {

	private ImmProperties bonus;
	
	public PropEffect(Requirement req, ImmProperties bonus) {
		super(req);
		this.bonus = bonus;
	}
	
	@Override
	public boolean activate(Player player) {
		return true;
	}

}
