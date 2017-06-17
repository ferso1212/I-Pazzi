package it.polimi.ingsw.ps21.model.effect;

import java.util.HashSet;
import java.util.Set;

import javax.print.attribute.HashAttributeSet;

import it.polimi.ingsw.ps21.model.actions.ExtraAction;
import it.polimi.ingsw.ps21.model.actions.NullAction;
import it.polimi.ingsw.ps21.model.deck.DevelopmentCardType;
import it.polimi.ingsw.ps21.model.deck.Requirement;
import it.polimi.ingsw.ps21.model.deck.TooManyArgumentException;
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

	private static final long serialVersionUID = 207667018131928919L;
	private ImmProperties discount;
	private Set<DevelopmentCardType> types;
	
	public DiscountEffect(ImmProperties discount, DevelopmentCardType... cardType) throws TooManyArgumentException {
		super(new ImmProperties(0));
		if (cardType.length > 4) throw new TooManyArgumentException();
		types = new HashSet<>();
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
	public ExtraAction activate(Player player) {
	 DiscountsSet modifier = player.getModifiers().getDiscountsMods();
	 for (DevelopmentCardType d: types) {
		modifier.getDiscount(d).getPropertiesDisc().increaseProperties(discount);
	 }
	return new NullAction(player.getId());
	}

	@Override
	public String getType() {
		return "Discount Effect";
	}

	@Override
	public String getDesc() {
		StringBuilder output= new StringBuilder("When you pick a " );
		for(DevelopmentCardType type: types)
		{output.append(type.toString() + " card ");
		if(type.ordinal()<DevelopmentCardType.values().length -1 ) output.append("or a ");
		}
		output.append(", you get a discount on its cost of " + discount.toString());
		return output.toString();
	}
	
	

}
