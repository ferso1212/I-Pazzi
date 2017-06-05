package it.polimi.ingsw.ps21.model.player;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.ParserConfigurationException;

import it.polimi.ingsw.ps21.model.deck.BuildingCard;
import it.polimi.ingsw.ps21.model.deck.CardsNumber;
import it.polimi.ingsw.ps21.model.deck.CharacterCard;
import it.polimi.ingsw.ps21.model.deck.DevelopmentCard;
import it.polimi.ingsw.ps21.model.deck.DevelopmentCardType;
import it.polimi.ingsw.ps21.model.deck.IllegalCardTypeException;
import it.polimi.ingsw.ps21.model.deck.Requirement;
import it.polimi.ingsw.ps21.model.deck.TerritoryCard;
import it.polimi.ingsw.ps21.model.deck.VentureCard;
import it.polimi.ingsw.ps21.model.match.MatchFactory;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;

public class PlayerDeck implements Cloneable{
	private final static Logger LOGGER = Logger.getLogger(PlayerDeck.class.getName());
	
	private EnumMap<DevelopmentCardType, ArrayList<DevelopmentCard>> decksMap;
	
	//nella k-esima cella dell'array Requirement[], c'è il requisito che il player deve soddisfare per
	//poter acquisire la (k+1)esima carta di quel tipo
	private EnumMap<DevelopmentCardType, Requirement[]> addingCardRequirements;
	
	/**Removes all the adding card requirements for a specific card type.
	 * Once this method has been called with a specific cardType, each time the player wants to acquire a card of that type he doesn't have to meet the requirements
	 * related to the addition of another card (for example, if activated on Territory cards, you won't need to have enough military points to add another territory card.
	 * @param cardType
	 */
	public void setNoAddingRequirement(DevelopmentCardType cardType)
	{
		Requirement[] replacingReqs= new Requirement[this.addingCardRequirements.get(cardType).length];
		for(int i=0; i<replacingReqs.length; i++)
		{
			replacingReqs[i]= new Requirement(new CardsNumber(0,0,0,0), new ImmProperties(0));
		}
		addingCardRequirements.replace(cardType, replacingReqs);
	}
	
	public PlayerDeck() throws ParserConfigurationException{
		super();
		addingCardRequirements = (EnumMap<DevelopmentCardType, Requirement[]>) MatchFactory.instance().makeCardAddingRequirements();
		decksMap= new EnumMap<DevelopmentCardType, ArrayList<DevelopmentCard>>(DevelopmentCardType.class);
	}
	
	public int countCards(DevelopmentCardType type){
		return decksMap.get(type).size();
	}
	
	public void addCard(DevelopmentCard card) throws RequirementNotMetException{
		decksMap.get(card.getCardType()).add(card);
	}
	
	public Requirement getAddingCardRequirement(DevelopmentCard card) {
		
		return addingCardRequirements.get(card.getCardType())[countCards(card.getCardType())];
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
	
	//TODO implement Playerdeck.clone
	@Override
	public PlayerDeck clone()
	{	 
	    try {
			PlayerDeck output=(PlayerDeck) super.clone();
			return output;
		} catch (CloneNotSupportedException e) {
			LOGGER.log(Level.SEVERE, "Clone failed", e);
			return null;
		}
		/*EnumMap<DevelopmentCardType, ArrayList<DevelopmentCard>> clonedDecksMap= new EnumMap<DevelopmentCardType, ArrayList<DevelopmentCard>>(DevelopmentCardType.class);
		
		for(DevelopmentCardType cardType: DevelopmentCardType.values())
		{
			clonedDecksMap.put(cardType, (ArrayList<DevelopmentCard>)this.decksMap.get(cardType).clone());
			
		}
		*/
	}

	public PlayerDeck(EnumMap<DevelopmentCardType, ArrayList<DevelopmentCard>> decksMap,
			EnumMap<DevelopmentCardType, Requirement[]> requirementMap) {
		super();
		this.decksMap = decksMap;
		this.addingCardRequirements = requirementMap;
	}
}
