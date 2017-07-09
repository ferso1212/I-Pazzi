package it.polimi.ingsw.ps21.client.GUI;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import it.polimi.ingsw.ps21.view.FamilyMemberData;

public class MultipleWorkActionPanel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton marketButton;
	private JLabel occupants[];
	
	public MultipleWorkActionPanel(ActionListener listener){
		super();
		setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		marketButton = new JButton("Add family member here");
		marketButton.setOpaque(false);
		marketButton.setContentAreaFilled(false);
		marketButton.setBorderPainted(true);
		marketButton.addActionListener(listener);
		add(marketButton);
		
	}
	
	public void update(FamilyMemberData[] members){
		removeAll();
		occupants = new JLabel[members.length];
		for (int i=0; i<members.length; i++){
			occupants[i] = new JLabel();
			try {
				BufferedImage icon = ImageIO.read(new File((new File("").getAbsolutePath().concat("/src/images/Lorenzo_Pedine/" + members[i].getOwnerId().toString().toLowerCase() +
						"_Player_" + members[i].getColor().toString().toLowerCase() + ".png"))));
				occupants[i].setIcon(new ImageIcon(icon));
				add(occupants[i]);
				occupants[i].setVisible(true);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		add(marketButton);
	}

	public JButton getButton(){
		return this.marketButton;
	}
}
