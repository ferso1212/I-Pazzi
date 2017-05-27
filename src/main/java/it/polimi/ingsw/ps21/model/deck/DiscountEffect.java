package it.polimi.ingsw.ps21.model.deck;

import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;

public class DiscountEffect extends Effect {

	private ImmProperties discount;
	
	public DiscountEffect(Requirement req, ImmProperties discount) {
		super(req);
		this.discount = discount;
	}
	@Override
	public boolean activate(Player player) {
		ImmProperties modifier = player.getModifiers();
		modifier.setCoins(modifier.getCoins() - discount.getCoins());
		modifier.setStone(modifier.getStone() - discount.getStone());
		modifier.setWood(modifier.getWood() - discount.getWood());
		modifier.setServants(modifier.getServants() - discount.getServants());
	}

}
