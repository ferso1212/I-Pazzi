package it.polimi.ingsw.ps21;

import it.polimi.ingsw.ps21.model.*;

public class MultiplierEffect extends Effect implements PermanentEffect,InstantEffect {
	private MultiplierType secondFactorType;
	private int secondFactorValue;
	
	public void addInstantBonus(Player player){
		int bonusValue;
		ImmProperties bonus;
		switch(secondFactorType){
		case BLUE_CARD:
			bonusValue = player.countBlueCards() * secondFactorValue;
			break;
		case COINS:
			bonusValue = player.getProperties().getCoins();
			break;
		case FAITH_POINTS:
			bonusValue = player.getProperties().getFaithPoints();
			break;
		case GREEN_CARD:
			bonusValue = player.countGreenCards() * secondFactorValue;
			break;
		case MILITARY_POINTS:
			bonusValue = player.getProperties().getMilitaryPoints();
			break;
		case PURPLE_CARD:
			bonusValue = player.countPurpleCards() * secondFactorValue;
			break;
		case SERVANT:
			bonusValue = player.getProperties().getServants();
			break;
		case STONE:
			bonusValue = player.getProperties().getStone();
			break;
		case VICTORY_POINTS:
			bonusValue = player.getProperties().getVictoryPoints();
			break;
		case WOOD:
			bonusValue = player.getProperties().getWood();
			break;
		case YELLOW_CARD:
			bonusValue = player.countYellowCards() * secondFactorValue;
			break;
		default: bonusValue = 0;
			break;
		}
		bonus = new ImmProperties(bonusProperties.getCoins()*bonusValue,bonusProperties.getWood()*bonusValue, bonusProperties.getStone()*bonusValue,
				 bonusProperties.getServants()*bonusValue, bonusProperties.getPrivileges()*bonusValue, bonusProperties.getMilitaryPoints()*bonusValue,
				 bonusProperties.getFaithPoints()*bonusValue, bonusProperties.getVictoryPoints()*bonusValue);
		player.getProperties().addCoins(bonus.getCoins());
		player.getProperties().addWood(bonus.getWood());
		player.getProperties().addStone(bonus.getStone());
		player.getProperties().addPrivileges(bonus.getPrivileges());
		player.getProperties().addServants(bonus.getServants());
		player.getProperties().addFaithPoints(bonus.getFaithPoints());
		player.getProperties().addMilitaryPoints(bonus.getMilitaryPoints());
		player.getProperties().addVictoryPoints(bonus.getVictoryPoints());
	}
	
}
