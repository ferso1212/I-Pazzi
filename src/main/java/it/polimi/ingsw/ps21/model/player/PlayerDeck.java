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

public class PlayerDeck implements Cloneable{
	
	/*private ArrayList<TerritoryCard> greenCards;
	private ArrayList<BuildingCard> yellowCards;
	private ArrayList<CharacterCard> blueCards;
	private ArrayList<VentureCard> purpleCards;*/
	private EnumMap<DevelopmentCardType, ArrayList<DevelopmentCard>> decksMap;
	
	//nella k-esima cella dell'array Requirement[], c'è il requisito che il player deve soddisfare per
	//poter acquisire la (k+1)esima carta di quel tipo
	private EnumMap<DevelopmentCardType, Requirement[]> requirementMap;
	
	public PlayerDeck(){
		super();
		requirementMap = new EnumMap<DevelopmentCardType, Requirement[]>(DevelopmentCardType.class);
		decksMap= new EnumMap<DevelopmentCardType, ArrayList<DevelopmentCard>>(DevelopmentCardType.class);
	}
	
	public int countCards(DevelopmentCardType type){
		return decksMap.get(type).size();
	}
	
	public void addCard(DevelopmentCard card) throws RequirementNotMetException{
		decksMap.get(card.getCardType()).add(card);
	}
	
	public Requirement getAddingCardRequirement(DevelopmentCard card) {
		
		return requirementMap.get(card.getCardType())[countCards(card.getCardType())];
	}
	
	public ArrayList<DevelopmentCard> getCards(DevelopmentCardType type) throws IllegalCardTypeException{
		return this.decksMap.get(type);
	}
	
	/**Used to check whether the player has enough territory cards, building cards, venture cards and character cards to meet a specific requirement.
	 * 
	 * @param req
	 * @return TRUE if the player satisfies the requirements (i.e. : he has at least the number of cards required), FALSE if not.
	 */
	public boolean checkCardsNumReq(CardsNumber req)
	{
		for(DevelopmentCardType cardType: DevelopmentCardType.values())
		{
			if(req.getCardsNumReq(cardType)>this.countCards(cardType)) return false;
		}
		return true;
	}
	
	/*public PlayerDeck clone()
	{
		EnumMap<DevelopmentCardType, ArrayList<DevelopmentCard>> clonedDecksMap= new EnumMap<DevelopmentCardType, ArrayList<DevelopmentCard>>(DevelopmentCardType.class);
		
		for(DevelopmentCardType cardType: DevelopmentCardType.values())
		{
			clonedDecksMap.put(cardType, (ArrayList<DevelopmentCard>)this.decksMap.get(cardType).clone());
		}
	}*/

	public PlayerDeck(EnumMap<DevelopmentCardType, ArrayList<DevelopmentCard>> decksMap,
			EnumMap<DevelopmentCardType, Requirement[]> requirementMap) {
		super();
		this.decksMap = decksMap;
		this.requirementMap = requirementMap;
	}
}
