package GUI;

import java.awt.Image;

import javax.swing.JButton;

public class MarketButton extends JButton{
	
	private int spaceNumber;
	
	public MarketButton(int spaceNumber){
		
		this.spaceNumber = spaceNumber;
		this.setToolTipText("Proprietà");
		this.addActionListener(new CardListener());
		this.setOpaque(false);
		this.setContentAreaFilled(false);
		this.setBorderPainted(false);
		
	}

	public int getSpaceNumber() {
		return spaceNumber;
	}
	
	

}
