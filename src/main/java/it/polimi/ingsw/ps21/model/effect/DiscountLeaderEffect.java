package it.polimi.ingsw.ps21.model.effect;

import java.util.HashSet;
import java.util.Set;

import it.polimi.ingsw.ps21.model.actions.ExtraAction;
import it.polimi.ingsw.ps21.model.actions.NullAction;
import it.polimi.ingsw.ps21.model.deck.DevelopmentCardType;
import it.polimi.ingsw.ps21.model.deck.Requirement;
import it.polimi.ingsw.ps21.model.deck.TooManyArgumentException;
import it.polimi.ingsw.ps21.model.player.AdvancedPlayer;
import it.polimi.ingsw.ps21.model.player.DiscountsSet;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;
import it.polimi.ingsw.ps21.model.properties.PropertiesId;
import it.polimi.ingsw.ps21.model.properties.Property;

public class DiscountLeaderEffect extends PermanentLeaderEffect {

	private ImmProperties discount;
	private Set<DevelopmentCardType> types;
	
	public DiscountLeaderEffect(Requirement reqs[]) {
		super(reqs);
		types = new HashSet<>();
		this.discount = new ImmProperties(new Property(PropertiesId.COINS, 3));
			types.add(DevelopmentCardType.BUILDING);
			types.add(DevelopmentCardType.CHARACTER);
			types.add(DevelopmentCardType.VENTURE);
			types.add(DevelopmentCardType.TERRITORY);
		
	}
	@Override
	public ExtraAction activate(AdvancedPlayer player) {
	 DiscountsSet modifier = player.getModifiers().getDiscountsMods();
	 for (DevelopmentCardType d: types) {
		modifier.getDiscount(d).getPropertiesDisc().increaseProperties(discount);
	 }
	return new NullAction(player.getId());
	}

	@Override
	public String getType() {
		return "Discount Leader Effect";
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
