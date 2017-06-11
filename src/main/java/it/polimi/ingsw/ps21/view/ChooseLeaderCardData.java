package it.polimi.ingsw.ps21.view;

public class ChooseLeaderCardData extends ActionData{

	private static final long serialVersionUID = -6462956425022074079L;
	private int card;

	public ChooseLeaderCardData(int card) {
		super();
		this.card = card;
	}

	public int getCard() {
		return card;
	}
	
}
