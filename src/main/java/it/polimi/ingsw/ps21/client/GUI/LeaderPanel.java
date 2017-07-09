package it.polimi.ingsw.ps21.client.GUI;

import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import it.polimi.ingsw.ps21.model.deck.LeaderCard;

public class LeaderPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private LeaderCardButton[] leaders = new LeaderCardButton[4];
	
	public LeaderPanel(Dimension actionPanelDimension, ActionListener leaderListener){
		this.setPreferredSize(new Dimension((int)(actionPanelDimension.getWidth()), (int)(actionPanelDimension.getHeight()* 13 / 18)));
		this.setVisible(true);
		this.setOpaque(false);
		
		for (int i=0; i < leaders.length; i++){
			this.leaders[i] = new LeaderCardButton();
			this.leaders[i].addActionListener(leaderListener);
			this.add(this.leaders[i]);
		}
	}
	
	public void setButtons(){
		for(int i=0; i < leaders.length; i++){
			leaders[i].setButton((int)(this.getPreferredSize().getHeight()));
		}
	}
	
	public void update(LeaderCard cards[]){
		for (int i=0; i < cards.length; i++){
			this.leaders[i].update(cards[i].getName(), cards[i].toString());
			this.leaders[i].setEnabled(!cards[i].isActivated());
		}
	}
	
	public LeaderCardButton[] getLeadersButtons(){
		return this.leaders;
	}

}
