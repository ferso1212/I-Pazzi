package it.polimi.ingsw.ps21.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps21.model.actions.WorkType;
import it.polimi.ingsw.ps21.model.deck.DevelopmentCard;
import it.polimi.ingsw.ps21.model.deck.DevelopmentCardType;
import it.polimi.ingsw.ps21.model.deck.IllegalCardTypeException;
import it.polimi.ingsw.ps21.model.effect.LeaderCard;
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
	private PlayerColor color;
	private EnumMap<DevelopmentCardType, ArrayList<DevCardData>> cards;
	private ArrayList<LeaderCard> leaderCards;
	
	public PlayerData(EnumMap<PropertiesId, Integer> properties, ImmProperties tileHarvBonus,
			int tileHarvDiceReq, ImmProperties tileProdBonus, int tileProdDiceReq, PlayerColor id,
			EnumMap<DevelopmentCardType, ArrayList<DevelopmentCard>> cards, ArrayList<LeaderCard> leaderCards) {
		super();
		this.id = id;
		this.properties = properties;
		this.tileHarvBonus = tileHarvBonus;
		this.tileHarvDiceReq = tileHarvDiceReq;
		this.tileProdBonus = tileProdBonus;
		this.tileProdDiceReq = tileProdDiceReq;
		this.color = color;
		//TODO : convert in DevCardData this.cards = cards;
		this.leaderCards = leaderCards;
	}
	
	public PlayerData(Player player) {
		super();
		this.id = player.getId();
		for(PropertiesId prop: PropertiesId.values())
			{
			this.properties.put(prop, player.getProperties().getProperty(prop).getValue());
			}
		this.tileHarvBonus = player.getPersonalBonusTile().getTileBonus(WorkType.HARVEST,10).clone();
		this.tileHarvDiceReq = player.getPersonalBonusTile().getDiceReq(WorkType.HARVEST);
		this.tileProdBonus = player.getPersonalBonusTile().getTileBonus(WorkType.PRODUCTION,10).clone();
		this.tileProdDiceReq = player.getPersonalBonusTile().getDiceReq(WorkType.PRODUCTION);
		/*TODO: clone player's deck
		 * for(DevelopmentCardType cardType: DevelopmentCardType.values())
		{
			try {
				ArrayList<DevelopmentCard> clonedDeck= new ArrayList<DevelopmentCard>(player.getDeck().getCards(cardType));
				cards.put(cardType, clonedDeck);
			} catch (IllegalCardTypeException e) {
				LOGGER.log(Level.SEVERE, "Illegal card type!", e);
				// TODO Auto-generated catch block
			
			}
			
		}*/
		//this.leaderCards = leaderCards;
	}
	
}
