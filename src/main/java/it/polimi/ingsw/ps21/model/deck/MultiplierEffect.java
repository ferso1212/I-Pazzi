package it.polimi.ingsw.ps21.model.deck;

import it.polimi.ingsw.ps21.model.CardType;
import it.polimi.ingsw.ps21.model.DevelopmentCardType;
import it.polimi.ingsw.ps21.model.MultiplierType;
import it.polimi.ingsw.ps21.model.PropertiesId;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;

public class MultiplierEffect extends Effect {
	
	private ImmProperties bonusProperties;
	private MultiplierType secondFactorType;
	
	private int secondFactorValue;
	
	public MultiplierEffect(Requirement req, ImmProperties bonus, MultiplierType secondFactorType, int secondFactorValue ){
		super(req);
		bonusProperties = bonus;
		this.secondFactorType = secondFactorType;
		this.secondFactorValue = secondFactorValue;
		
	}
	private void multiplyEffect(Player player){
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
	// Questa aggiunta non tiene conto della presenza di malus o bonus nel player //
		player.getProperties().increaseProperties(bonus);
	 // gameLogic.addProperties(player, properties)
	}
	
	@Override
	public void activate(Player player){
		multiplyEffect(player);
		}
	
}
