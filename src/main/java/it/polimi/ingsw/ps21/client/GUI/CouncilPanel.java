package it.polimi.ingsw.ps21.client.GUI;

import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import it.polimi.ingsw.ps21.controller.FamilyMemberData;

public class CouncilPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton councilButton;
	private double scaleFactor;

	public CouncilPanel(ActionListener councilListener, double scaleFactor) {

		this.councilButton = new JButton();
		this.setLayout(null);
		this.setOpaque(true);
		this.scaleFactor = scaleFactor;
		this.councilButton.setOpaque(false);
		this.councilButton.setContentAreaFilled(false);
		this.councilButton.setBorderPainted(true);
		this.councilButton.setToolTipText("Proprietà");
		this.councilButton.setOpaque(false);
		this.councilButton.setContentAreaFilled(false);
		this.councilButton.setBorderPainted(true);
		this.councilButton.addActionListener(councilListener);
		this.add(councilButton);
		councilButton.setBounds(resize(110), resize(110), resize(1110), resize(215));

	}

	public void update(FamilyMemberData[] occupants) {

		int i = 0;
		for (FamilyMemberData f : occupants) {
			if (f.getOwnerId() != null) {
				JLabel memberLabel = new JLabel();
				this.add(memberLabel);
				memberLabel.setBounds(resize(75) + i * resize(125), resize(335), resize(125), resize(125));
				try {
					BufferedImage memberIcon = ImageIO.read(new File(new File("").getAbsolutePath()
							.concat("/src/images/Lorenzo_Pedine/" + f.getOwnerId().toString().toLowerCase() + "_Player_"
									+ f.getColor().toString().toLowerCase() + ".png")));
					memberLabel.setIcon(
							new ImageIcon(memberIcon.getScaledInstance(resize(125), resize(125), Image.SCALE_SMOOTH)));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				memberLabel.setVisible(true);
				memberLabel.setOpaque(true);
			}else{
				break;
			}
		}
	}

	private int resize(int originalSize) {
		return (int) (this.scaleFactor * originalSize);
	}

	public JButton getCouncilButton() {
		return this.councilButton;
	}

}
