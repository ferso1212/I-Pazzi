package it.polimi.ingsw.ps21.model;

public class DiscountEffect extends Effect {

	private ImmProperties discount;
	
	public DiscountEffect(Requirement req, ImmProperties discount) {
		super(req);
		this.discount = discount;
	}
	@Override
	public boolean activate(Player player) {
		ImmProperties modifier = player.getModifierSet().getModifier(Modifierid.PROP_MODIFIER);
		modifier.setCoins(modifier.getCoins() - discount.getCoins());
		modifier.setStone(modifier.getStone() - discount.getStone());
		modifier.setWood(modifier.getWood() - discount.getWood());
		modifier.setServants(modifier.getServants() - discount.getServants());
	}

}
