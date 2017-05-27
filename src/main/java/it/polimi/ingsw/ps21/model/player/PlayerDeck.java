package it.polimi.ingsw.ps21.model.player;

import java.util.ArrayList;
import java.util.EnumMap;
import it.polimi.ingsw.ps21.model.deck.BuildingCard;
import it.polimi.ingsw.ps21.model.deck.CardsNumber;
import it.polimi.ingsw.ps21.model.deck.CharacterCard;
import it.polimi.ingsw.ps21.model.deck.DevelopmentCard;
import it.polimi.ingsw.ps21.model.deck.DevelopmentCardType;
import it.polimi.ingsw.ps21.model.deck.IllegalCardTypeException;
import it.polimi.ingsw.ps21.model.deck.Requirement;
import it.polimi.ingsw.ps21.model.deck.TerritoryCard;
import it.polimi.ingsw.ps21.model.deck.VentureCard;

public class PlayerDeck{
	
	private ArrayList<TerritoryCard> greenCards;
	private ArrayList<BuildingCard> yellowCards;
	private ArrayList<CharacterCard> blueCards;
	private ArrayList<VentureCard> purpleCards;
	
	//nella k-esima cella dell'array Requirement[], c'è il requisito che il player deve soddisfare per
	//poter acquisire la (k+1)esima carta di quel tipo
	private EnumMap<DevelopmentCardType, Requirement[]> requirementMap;
	
	public PlayerDeck(){
		super();
		requirementMap = new EnumMap<DevelopmentCardType, Requirement[]>(DevelopmentCardType.class);
	}
	
	public int countCards(DevelopmentCardType type){
		return requirementMap.get(type).length;
	}
	
	public void addCard(DevelopmentCard card) throws RequirementNotMetException{	
	if (card instanceof TerritoryCard ) this.greenCards.add((TerritoryCard) card);
	if (card instanceof BuildingCard ) this.yellowCards.add((BuildingCard) card);
	if (card instanceof CharacterCard ) this.blueCards.add((CharacterCard) card);
	if (card instanceof VentureCard) this.purpleCards.add((VentureCard) card);
	}
	
	public Requirement getAddingCardRequirement(DevelopmentCard card) {
		
		return requirementMap.get(card.getCardType())[countCards(card.getCardType())];
	}
	
	public DevelopmentCard[] getCards(DevelopmentCardType type) throws IllegalCardTypeException{
		switch (type) {
		case BUILDING:
			return (BuildingCard[]) yellowCards.toArray();
		case CHARACTER:
			return (CharacterCard[]) blueCards.toArray();
		case TERRITORY:
			return (TerritoryCard[]) greenCards.toArray();
		case VENTURE:
			return (VentureCard[]) purpleCards.toArray();
		default:
			throw new IllegalCardTypeException();
		}
	}
	
	public boolean checkCardsNumReq(CardsNumber req)
	{
		for(DevelopmentCardType cardType: DevelopmentCardType.values())
		{
			if(req.getCardsNumReq(cardType)>this.countCards(cardType)) return false;
		}
		return true;
	}
}
