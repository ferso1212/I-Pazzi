package it.polimi.ingsw.ps21.model.effect;


import it.polimi.ingsw.ps21.model.actions.ExtraAction;
import it.polimi.ingsw.ps21.model.actions.NullAction;
import it.polimi.ingsw.ps21.model.deck.DevelopmentCardType;
import it.polimi.ingsw.ps21.model.deck.MultiplierType;
import it.polimi.ingsw.ps21.model.deck.Requirement;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;
import it.polimi.ingsw.ps21.model.properties.PropertiesId;

public class MultiplierEffect extends Effect {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1220248292513614400L;
	private ImmProperties bonusProperties;
	private MultiplierType secondFactorType;
	
	private int secondFactorValue;
	
	public MultiplierEffect(ImmProperties cost, ImmProperties bonus, MultiplierType secondFactorType, int secondFactorValue ){
		super(cost);
		bonusProperties = bonus;
		this.secondFactorType = secondFactorType;
		this.secondFactorValue = secondFactorValue;
		
	}
	private ImmProperties calculateBonus(Player player){
		int bonusValue;
		ImmProperties bonus;
		switch(secondFactorType){
		case BLUE_CARD:
			bonusValue = player.getDeck().countCards(DevelopmentCardType.CHARACTER) * secondFactorValue;
			break;
		case COINS:
			bonusValue = player.getProperties().getProperty(PropertiesId.COINS).getValue() * secondFactorValue;
			break;
		case FAITH_POINTS:
			bonusValue = player.getProperties().getProperty(PropertiesId.FAITHPOINTS).getValue() * secondFactorValue;
			break;
		case GREEN_CARD:
			bonusValue = player.getDeck().countCards(DevelopmentCardType.TERRITORY) * secondFactorValue;
			break;
		case MILITARY_POINTS:
			bonusValue = player.getProperties().getProperty(PropertiesId.MILITARYPOINTS).getValue() * secondFactorValue;
			break;
		case PURPLE_CARD:
			bonusValue = player.getDeck().countCards(DevelopmentCardType.VENTURE) * secondFactorValue;
			break;
		case SERVANT:
			bonusValue = player.getProperties().getProperty(PropertiesId.SERVANTS).getValue() * secondFactorValue;
			break;
		case STONE:
			bonusValue = player.getProperties().getProperty(PropertiesId.STONES).getValue() * secondFactorValue;
			break;
		case VICTORY_POINTS:
			bonusValue = player.getProperties().getProperty(PropertiesId.VICTORYPOINTS).getValue() * secondFactorValue;
			break;
		case WOOD:
			bonusValue = player.getProperties().getProperty(PropertiesId.WOOD).getValue() * secondFactorValue;
			break;
		case YELLOW_CARD:
			bonusValue = player.getDeck().countCards(DevelopmentCardType.BUILDING) * secondFactorValue;
			break;
		default: bonusValue = 0;
			break;
		}
		bonus = new ImmProperties(bonusProperties.getPropertyValue(PropertiesId.COINS) *bonusValue,bonusProperties.getPropertyValue(PropertiesId.WOOD)*bonusValue,
				bonusProperties.getPropertyValue(PropertiesId.STONES)*bonusValue,
				 bonusProperties.getPropertyValue(PropertiesId.SERVANTS)*bonusValue, bonusProperties.getPropertyValue(PropertiesId.MILITARYPOINTS)*bonusValue,
				 bonusProperties.getPropertyValue(PropertiesId.FAITHPOINTS)*bonusValue, bonusProperties.getPropertyValue(PropertiesId.VICTORYPOINTS)*bonusValue);
		return bonus;
	}
	
	@Override
	public ExtraAction activate(Player player){
		ImmProperties bonus = calculateBonus(player);
		player.getProperties().increaseProperties(bonus);
		return new NullAction(player.getId());
	}
	@Override
	public String getType() {
		return "Multipier Effect";
	}
	@Override
	public String getDesc() {
		return "For every " + secondFactorValue + " of " + secondFactorType + " you have " + bonusProperties.toString();
		
	}
	
}
