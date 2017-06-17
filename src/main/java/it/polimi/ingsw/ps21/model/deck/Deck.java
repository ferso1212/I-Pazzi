package it.polimi.ingsw.ps21.model.deck;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps21.controller.PlayerData;


public class Deck implements Cloneable {
	private final static Logger LOGGER = Logger.getLogger(Deck.class.getName());
	protected SubDeck<TerritoryCard> greenCards;
	protected SubDeck<BuildingCard> yellowCards;
	protected SubDeck<CharacterCard> blueCards;
	protected SubDeck<VentureCard> purpleCards;
	protected ExcommunicationDeck excommunications;
	
	public Deck(){
		greenCards = new SubDeck<>();
		yellowCards = new SubDeck<>();
		blueCards = new SubDeck<>();
		purpleCards = new SubDeck<>();
		excommunications = new ExcommunicationDeck();
	}
	

	public void addCard(Card card) throws IllegalCardException{
		if(card instanceof TerritoryCard) greenCards.addCard((TerritoryCard) card);
		else 
			if(card instanceof BuildingCard) yellowCards.addCard((BuildingCard) card);
			else 
				if(card instanceof CharacterCard) blueCards.addCard((CharacterCard) card);
				else 
					if(card instanceof VentureCard) purpleCards.addCard((VentureCard) card);
					else	throw new IllegalCardException();
	}
	
	public void setGreenDeck(SubDeck<TerritoryCard> greenDeck){
		greenCards = greenDeck;
		
	}
	
	public void setPurpleDeck(SubDeck<VentureCard> purpleDeck){
		purpleCards = purpleDeck;
	}
	
	public void setBlueDeck(SubDeck<CharacterCard> blueDeck){
		blueCards = blueDeck;
	
	}
	
	public void setYellowDeck(SubDeck<BuildingCard> yellowDeck){
		yellowCards = yellowDeck;
	}
	
	public void setExcommunication(ExcommunicationDeck excoms){
		this.excommunications = excoms;
	}
	
	public DevelopmentCard getCard(int era, DevelopmentCardType type) throws IllegalCardException{
		switch(type){
		case BUILDING:
			return yellowCards.getCard(era);
		case CHARACTER:
			return blueCards.getCard(era);
		case TERRITORY:
			return greenCards.getCard(era);
		case VENTURE:
			return purpleCards.getCard(era);
		default:
			throw new IllegalCardException();
		}
	}
	
	public void shuffle(){
		blueCards.shuffle();
		greenCards.shuffle();
		purpleCards.shuffle();
		yellowCards.shuffle();
	}
	
	@Override
	public String toString(){
		StringBuilder temp = new StringBuilder();		
		temp.append("Deck:");
		temp.append("\n=============================\tTerritory Deck\t=====================");
		temp.append(greenCards.toString());
		temp.append("\n=============================\tBuilding Deck\t=====================");
		temp.append(yellowCards.toString());
		temp.append("\n=============================\tCharacter Deck\t=====================");
		temp.append(blueCards.toString());
		temp.append("\n=============================\tVenture Deck\t=====================");
		temp.append(purpleCards.toString());
		return temp.toString();
	}
	
	public boolean isEmpty(){
		return greenCards.isEmpty() && yellowCards.isEmpty() && blueCards.isEmpty() && purpleCards.isEmpty() && excommunications.isEmpty();
	}
	
	//TODO implement deep clone
	public Deck clone()
	{
		Deck clone = new Deck();
		clone.setBlueDeck(blueCards.clone());
		clone.setYellowDeck(yellowCards.clone());
		clone.setPurpleDeck(purpleCards.clone());
		clone.setGreenDeck(greenCards.clone());
		return clone;
	}
}
