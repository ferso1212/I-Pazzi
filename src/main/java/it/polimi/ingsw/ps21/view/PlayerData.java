package it.polimi.ingsw.ps21.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps21.controller.NotAdvancedPlayerException;
import it.polimi.ingsw.ps21.model.actions.WorkType;
import it.polimi.ingsw.ps21.model.deck.DevelopmentCard;
import it.polimi.ingsw.ps21.model.deck.DevelopmentCardType;
import it.polimi.ingsw.ps21.model.deck.IllegalCardTypeException;
import it.polimi.ingsw.ps21.model.deck.LeaderCard;
import it.polimi.ingsw.ps21.model.excommunications.Excommunication;
import it.polimi.ingsw.ps21.model.player.AdvancedPlayer;
import it.polimi.ingsw.ps21.model.player.MembersColor;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.player.PlayerColor;
import it.polimi.ingsw.ps21.model.player.PlayerProperties;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;
import it.polimi.ingsw.ps21.model.properties.PropertiesId;
import it.polimi.ingsw.ps21.model.properties.Property;

public class PlayerData implements Serializable {
	private final static Logger LOGGER = Logger.getLogger(PlayerData.class.getName());
	private PlayerColor id;
	private EnumMap<PropertiesId, Integer> properties;
	private ImmProperties tileHarvBonus;
	private int tileHarvDiceReq;
	private ImmProperties tileProdBonus;
	private int tileProdDiceReq;
	private EnumMap<DevelopmentCardType, ArrayList<DevelopmentCard>> cards;
	private EnumMap<MembersColor, FamilyMemberData> family;
	private Excommunication[] excommunications;
	private LeaderCard[] leaders;
	
	public PlayerData(Player player) {
		super();
		this.cards = new EnumMap<>(DevelopmentCardType.class);
		this.properties=new EnumMap<>(PropertiesId.class);
		this.id = player.getId();
		for(PropertiesId prop: PropertiesId.values())
			{
			this.properties.put(prop, player.getProperties().getProperty(prop).getValue());
			}
		this.tileHarvBonus = player.getPersonalBonusTile().getTileBonus(WorkType.HARVEST,10);
		this.tileHarvDiceReq = player.getPersonalBonusTile().getDiceReq(WorkType.HARVEST);
		this.tileProdBonus = player.getPersonalBonusTile().getTileBonus(WorkType.PRODUCTION,10);
		this.tileProdDiceReq = player.getPersonalBonusTile().getDiceReq(WorkType.PRODUCTION);
		
		//Clones each player's deck and puts it in the 'cards' map
		 for(DevelopmentCardType cardType: DevelopmentCardType.values())
		{
			try {
				ArrayList<DevelopmentCard> clonedDeck= new ArrayList<>();
				if (player.getDeck().getCards(cardType)!= null) clonedDeck.addAll(player.getDeck().getCards(cardType));
				cards.put(cardType, clonedDeck);
			} catch (IllegalCardTypeException e) {

				LOGGER.log(Level.SEVERE, "Illegal card type!", e);
			}
		}
		this.family=new EnumMap<>(MembersColor.class);
		for(MembersColor color: MembersColor.values())
		{
			family.put(color, new FamilyMemberData(player.getFamily().getMember(color)));
		}
		this.excommunications = player.getExcommunications();
		if(player instanceof AdvancedPlayer)
		{
			this.leaders=((AdvancedPlayer)player).getLeaders();
			
		}
		else this.leaders=new LeaderCard[0];
	}


	/**Returns the player's id, which is also used to identify the player among the others
	 * @return the player color (used also as id)
	 */
	public PlayerColor getId() {
		return id;
	}


	/**Returns a map containing the values of the properties of the player.
	 * @return a map containing the values of the properties of the player.
	 */
	public EnumMap<PropertiesId, Integer> getProperties() {
		return properties;
	}
	
	/**Returns the value of the selected property of the player.
	 * 
	 * @param propId the chosen property
	 * @return the value of the chosen property
	 */
	public int getPropertyValue(PropertiesId propId)
	{
		return this.properties.get(propId);
	}
	
	/**Returns the bonus provided by the personal tile when a harvest action is performed
	 * 
	 * @return the bonus
	 */
	public ImmProperties getTileHarvBonus() {
		return tileHarvBonus;
	}


	/**Returns the action value needed to receive the tile bonus when an harvest action is performed.
	 * 
	 * @return the dice requirement
	 */
	public int getTileHarvDiceReq() {
		return tileHarvDiceReq;
	}


	/**Returns the bonus provided by the personal tile when a production action is performed
	 * 
	 * @return the bonus
	 */
	public ImmProperties getTileProdBonus() {
		return tileProdBonus;
	}


	/**Returns the action value needed to receive the tile bonus when a production action is performed.
	 * 
	 * @return the dice requirement
	 */
	public int getTileProdDiceReq() {
		return tileProdDiceReq;
	}


	/**Returns the map containing all the development cards of the player
	 * 
	 * @return a map with all the cards belonging to the player
	 */
	public EnumMap<DevelopmentCardType, ArrayList<DevelopmentCard>> getCards() {
		return cards;
	}
	
	public FamilyMemberData getFamilyMember(MembersColor color)
	{
		return family.get(color);
	}
	
	public Excommunication[] getExcommunications(){
		return this.excommunications;
	}
	
	public LeaderCard[] getLeaders() throws NotAdvancedPlayerException
	{
		if(this.leaders==null) throw new NotAdvancedPlayerException();
		else return this.leaders;
	}
	
	
}
