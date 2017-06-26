package it.polimi.ingsw.ps21.client.GUI;

import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class DevelopmentActionPanel extends JPanel {
	
	private JLabel upSx = new JLabel();
	private JLabel upDx = new JLabel();
	private JLabel downSx = new JLabel();
	private JLabel downDx = new JLabel();
	
	public DevelopmentActionPanel(DevelopmentCardLabel relativeCard) {
		setLayout(new GridLayout(2, 2, 0, 0));
		
		upSx.setIcon(new ImageIcon(relativeCard.getCardImage().getScaledInstance(100, 170, Image.SCALE_DEFAULT)));
		add(upSx);
		add(upDx);
		add(downSx);
		add(downDx);
		this.setVisible(true);
	}
	


}
