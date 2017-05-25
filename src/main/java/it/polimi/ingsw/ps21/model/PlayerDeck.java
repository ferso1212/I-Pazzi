package it.polimi.ingsw.ps21.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.management.RuntimeErrorException;

public class PlayerDeck{
	
	private ArrayList<TerritoryCard> greenCards;
	private ArrayList<BuildingCard> yellowCards;
	private ArrayList<CharacterCard> blueCards;
	private ArrayList<VentureCard> purpleCards;
	private Map<DevelopmentCardType, ImmProperties[]> requirementMap;
	
	public PlayerDeck(){
		super();
		requirementMap = new HashMap<DevelopmentCardType, ImmProperties[]>();
	}
	
	public int countCards(DevelopmentCardType type){
		return requirementMap.get(type).length;
	}
	
	public void addCard(DevelopmentCard card) throws Exception{
	if (card instanceof TerritoryCard ) this.greenCards.add((TerritoryCard) card);
	if (card instanceof BuildingCard ) this.yellowCards.add((BuildingCard) card);
	if (card instanceof CharacterCard ) this.blueCards.add((CharacterCard) card);
	if (card instanceof VentureCard) this.purpleCards.add((VentureCard) card);
	}
	
	public ImmProperties getAddingCardRequirement(Card card) throws RuntimeException{
		
		if (card instanceof TerritoryCard) return requirementMap.get(DevelopmentCardType.TERRITORY)[countCards(DevelopmentCardType.TERRITORY) + 1];
		if (card instanceof BuildingCard) return requirementMap.get(DevelopmentCardType.BUILDING)[countCards(DevelopmentCardType.BUILDING) + 1];
		if (card instanceof CharacterCard) return requirementMap.get(DevelopmentCardType.CHARACTER)[countCards(DevelopmentCardType.CHARACTER) + 1];
		if (card instanceof VentureCard) return requirementMap.get(DevelopmentCardType.VENTURE)[countCards(DevelopmentCardType.VENTURE) + 1];
		throw new RuntimeException("Illegal Card Type");
	}
}
