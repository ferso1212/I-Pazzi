package it.polimi.ingsw.ps21.view;

public class PlayLeaderCardActionData extends ActionData{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2811230124593033009L;
	private int card;

	public PlayLeaderCardActionData(int card) {
		super();
		this.card = card;
	}

	public int getCard() {
		return card;
	}
	
	
}
