package it.polimi.ingsw.ps21.client.GUI;

import java.awt.Image;

import javax.swing.JButton;

public class MarketButton extends JButton{
	
	private int spaceNumber;
	
	public MarketButton(int spaceNumber){
		
		this.spaceNumber = spaceNumber;
		this.setToolTipText("Proprietà");
		this.setOpaque(false);
		this.setContentAreaFilled(false);
		this.setBorderPainted(true);
		
	}

	public int getSpaceNumber() {
		return spaceNumber;
	}
	
	

}
