package it.polimi.ingsw.ps21.controller;

import java.util.ArrayList;

import it.polimi.ingsw.ps21.model.player.PersonalBonusTile;
import it.polimi.ingsw.ps21.model.player.PlayerColor;

/**
 *This message is used from TileChoiceAction to perform a request for user to choice the advanced personal tile to 
 * attach to his personal board
 * @author gullit
 *
 */
public class TileChoice extends Message {
	
	
	private ArrayList<PersonalBonusTile> possibleChoices;
	private int chosen;
	
	public TileChoice(PlayerColor dest, ArrayList<PersonalBonusTile> possibilities, int id) {
		super(dest, id);
		this.possibleChoices = possibilities; 
		chosen = 0;
	}
	
	
	public void setChosen(int choice){
		chosen = choice;
	}
	
	public PersonalBonusTile getChosen(){
		PersonalBonusTile choice = possibleChoices.remove(chosen);
		return choice;
	}
	
	public PersonalBonusTile[] getChoices()
	{
		return this.possibleChoices.toArray(new PersonalBonusTile[0]);
	}

}
