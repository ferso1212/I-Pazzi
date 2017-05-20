package it.polimi.ingsw.ps21.model;

public class MultiplierEffect extends Effect implements PermanentEffect,InstantEffect {
	private MultiplierType secondFactorType;
	private int secondFactorValue;
	
	private void multiplyEffect(Player player){
		int bonusValue;
		ImmProperties bonus;
		switch(secondFactorType){
		case BLUE_CARD:
			bonusValue = player.countCards(CardType.CHARACTER) * secondFactorValue;
			break;
		case COINS:
			bonusValue = player.getProperties().getCoins();
			break;
		case FAITH_POINTS:
			bonusValue = player.getProperties().getFaithPoints();
			break;
		case GREEN_CARD:
			bonusValue = player.countCards(CardType.TERRITORY) * secondFactorValue;
			break;
		case MILITARY_POINTS:
			bonusValue = player.getProperties().getMilitaryPoints();
			break;
		case PURPLE_CARD:
			bonusValue = player.countCards(CardType.VENTURE) * secondFactorValue;
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
			bonusValue = player.countCards(CardType.BUILDING) * secondFactorValue;
			break;
		default: bonusValue = 0;
			break;
		}
		bonus = new ImmProperties(bonusProperties.getCoins()*bonusValue,bonusProperties.getWood()*bonusValue, bonusProperties.getStone()*bonusValue,
				 bonusProperties.getServants()*bonusValue, bonusProperties.getMilitaryPoints()*bonusValue,
				 bonusProperties.getFaithPoints()*bonusValue, bonusProperties.getVictoryPoints()*bonusValue);
	// Questa aggiunta non tiene conto della presenza di malus o bonus nel player //
		player.getProperties().addCoins(bonus.getCoins());
		player.getProperties().addWood(bonus.getWood());
		player.getProperties().addStone(bonus.getStone());
		player.getProperties().addServants(bonus.getServants());
		player.getProperties().addFaithPoints(bonus.getFaithPoints());
		player.getProperties().addMilitaryPoints(bonus.getMilitaryPoints());
		player.getProperties().addVictoryPoints(bonus.getVictoryPoints());
	 // gameLogic.addProperties(player, properties)
	}
	
	public void addInstantBonus(Player player){
		multiplyEffect(player);
	}

	@Override
	public void addPermanentEffect(Player player) {
		multiplyEffect(player);
		
	}
	
}
