package it.polimi.ingsw.ps21.model.deck;

import java.util.Set;

import it.polimi.ingsw.ps21.model.player.DiscountsSet;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;

/**
 * This class implements effect that can set bonus modifiers of requirements and costs
 *  on player when he picks card from the board
 * @author gullit
 *
 */
public class DiscountEffect extends Effect {

	private ImmProperties discount;
	private Set<DevelopmentCardType> types;
	
	public DiscountEffect(Requirement reqs[], ImmProperties discount, DevelopmentCardType... cardType) throws TooManyArgumentException {
		super(reqs);
		if (cardType.length > 4) throw new TooManyArgumentException();
		this.discount = discount;
		
		//If no cardType parameters passed, by default discount go on every card
		if (cardType.length == 0) {
			types.add(DevelopmentCardType.BUILDING);
			types.add(DevelopmentCardType.CHARACTER);
			types.add(DevelopmentCardType.VENTURE);
			types.add(DevelopmentCardType.TERRITORY);
		}
		else	
			for(DevelopmentCardType t: cardType){
				types.add(t);
				}
	}
	
	public DiscountEffect(Requirement requirement, ImmProperties discount, DevelopmentCardType...cardType) throws TooManyArgumentException {
		super(requirement);
		if (cardType.length > 4) throw new TooManyArgumentException();
		this.discount = discount;
		
		//If no cardType parameters passed, by default discount go on every card
		if (cardType.length == 0) {
			types.add(DevelopmentCardType.BUILDING);
			types.add(DevelopmentCardType.CHARACTER);
			types.add(DevelopmentCardType.VENTURE);
			types.add(DevelopmentCardType.TERRITORY);
		}
		else	
			for(DevelopmentCardType t: cardType){
				types.add(t);
				}
	}

	@Override
	public void activate(Player player) {
		//DiscountsSet modifier = player.getModifiers().getDiscountsMods();
		//TODO for (DevelopmentCardType d: types) {
		//	modifier.getDiscount(d).getPropertiesDisc().increaseProperties(discount);
		//}
	}

	@Override
	public String getType() {
		return new String(this.getClass().getName());
	}

	@Override
	public String getDesc() {
		String output= new String("When you pick a " );
		for(DevelopmentCardType type: types)
		{output.concat(type.toString() + " card ");
		if(type.ordinal()<type.values().length -1 ) output.concat("or a ");
		}
		output.concat(", you get a discount on its cost of " + discount.toString());
		return output;
	}
	
	

}
