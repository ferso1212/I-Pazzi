package it.polimi.ingsw.ps21.controller;

import java.util.ArrayList;

import it.polimi.ingsw.ps21.model.deck.LeaderCard;
import it.polimi.ingsw.ps21.model.player.Player;

public class AdvPlayerData extends PlayerData{
	private ArrayList<LeaderCard> leaderCards;

	public AdvPlayerData(Player player, ArrayList<LeaderCard> leaderCards) {
		super(player);
		this.leaderCards = leaderCards;
	}
	
	
}
