package it.polimi.ingsw.ps21.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.EnumMap;

import it.polimi.ingsw.ps21.model.actions.WorkType;
import it.polimi.ingsw.ps21.model.deck.DevelopmentCard;
import it.polimi.ingsw.ps21.model.deck.DevelopmentCardType;
import it.polimi.ingsw.ps21.model.deck.IllegalCardTypeException;
import it.polimi.ingsw.ps21.model.deck.LeaderCard;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.player.PlayerColor;
import it.polimi.ingsw.ps21.model.player.PlayerProperties;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;
import it.polimi.ingsw.ps21.model.properties.PropertiesId;
import it.polimi.ingsw.ps21.model.properties.Property;

public class PlayerData implements Serializable {
	private int id;
	private EnumMap<PropertiesId, Integer> properties;
	private ImmProperties tileHarvBonus;
	private int tileHarvDiceReq;
	private ImmProperties tileProdBonus;
	private int tileProdDiceReq;
	private PlayerColor color;
	private EnumMap<DevelopmentCardType, ArrayList<DevelopmentCard>> cards;
	private ArrayList<LeaderCard> leaderCards;
	
	public PlayerData(int id, EnumMap<PropertiesId, Integer> properties, ImmProperties tileHarvBonus,
			int tileHarvDiceReq, ImmProperties tileProdBonus, int tileProdDiceReq, PlayerColor color,
			EnumMap<DevelopmentCardType, ArrayList<DevelopmentCard>> cards, ArrayList<LeaderCard> leaderCards) {
		super();
		this.id = id;
		this.properties = properties;
		this.tileHarvBonus = tileHarvBonus;
		this.tileHarvDiceReq = tileHarvDiceReq;
		this.tileProdBonus = tileProdBonus;
		this.tileProdDiceReq = tileProdDiceReq;
		this.color = color;
		this.cards = cards;
		this.leaderCards = leaderCards;
	}
	
	public PlayerData(Player player) {
		super();
		this.id = player.getId();
		for(PropertiesId prop: PropertiesId.values())
			{
			this.properties.put(prop, player.getProperties().getProperty(prop).getValue());
			};
		this.tileHarvBonus = player.getPersonalBonusTile().getTileBonus(WorkType.HARVEST,10).clone();
		this.tileHarvDiceReq = player.getPersonalBonusTile().getDiceReq(WorkType.HARVEST);
		this.tileProdBonus = player.getPersonalBonusTile().getTileBonus(WorkType.PRODUCTION,10).clone();
		this.tileProdDiceReq = player.getPersonalBonusTile().getDiceReq(WorkType.PRODUCTION);
		this.color = player.getColor();
		for(DevelopmentCardType cardType: DevelopmentCardType.values())
		{
			try {
				ArrayList<DevelopmentCard> clonedDeck= new ArrayList<DevelopmentCard>(player.getDeck().getCards(cardType));
				cards.put(cardType, clonedDeck);
			} catch (IllegalCardTypeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		//this.leaderCards = leaderCards;
	}
	
}
