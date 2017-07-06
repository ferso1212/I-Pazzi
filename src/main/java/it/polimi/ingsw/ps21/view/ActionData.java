package it.polimi.ingsw.ps21.view;

import java.io.Serializable;

import it.polimi.ingsw.ps21.model.actions.ActionType;
import it.polimi.ingsw.ps21.model.deck.DevelopmentCardType;
import it.polimi.ingsw.ps21.model.player.MembersColor;

public class ActionData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -146641297978347212L;

	private ActionType type;
	private MembersColor familyMember;
	private int servants;
	private DevelopmentCardType tower;
	private int space;
	private int id;
	
	public ActionData(ActionType type, MembersColor familyMember, int servants, DevelopmentCardType tower, int space, int actionId) {
		super();
		this.type = type;
		this.familyMember = familyMember;
		this.servants = servants;
		this.tower = tower;
		this.space = space;
		this.id=actionId;
	}

	public ActionType getType() {
		return type;
	}

	public MembersColor getFamilyMember() {
		return familyMember;
	}

	public int getServants() {
		return servants;
	}

	public DevelopmentCardType getTower() {
		return tower;
	}
	
	public int getSpace() {
		return space;
	}

	public int getId() {
		return id;
	}
	
	
	
	
}
