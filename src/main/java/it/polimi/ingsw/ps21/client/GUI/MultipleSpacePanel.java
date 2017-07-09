package it.polimi.ingsw.ps21.client.GUI;

import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import it.polimi.ingsw.ps21.view.FamilyMemberData;

public class MultipleSpacePanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(MultipleSpacePanel.class.getSimpleName());
	private JButton spaceButton;
	private double scaleFactor;

	public MultipleSpacePanel(ActionListener listener, double scaleFactor) {

		this.spaceButton = new JButton();
		this.setLayout(null);
		this.setOpaque(false);
		this.scaleFactor = scaleFactor;
		this.spaceButton.setOpaque(false);
		this.spaceButton.setContentAreaFilled(false);
		this.spaceButton.setBorderPainted(true);
		this.spaceButton.setToolTipText("Proprietà");
		this.spaceButton.setOpaque(false);
		this.spaceButton.setContentAreaFilled(false);
		this.spaceButton.setBorderPainted(true);
		this.spaceButton.addActionListener(listener);
		this.add(spaceButton);
		spaceButton.setBounds(0, 0, resize(215), resize(215));

	}

	public void update(FamilyMemberData[] occupants) {
		
		this.removeAll();
		this.add(spaceButton);
		spaceButton.setBounds(0, 0, resize(215), resize(215));
		int i = 0;
		for (FamilyMemberData f : occupants) {
			if (f.getOwnerId() != null) {
				JLabel memberLabel = new JLabel();
				try {
					BufferedImage memberIcon = ImageIO.read(new File(new File("").getAbsolutePath()
							.concat("/src/images/Lorenzo_Pedine/" + f.getOwnerId().toString().toLowerCase() + "_Player_"
									+ f.getColor().toString().toLowerCase() + ".png")));
					memberLabel.setIcon(
							new ImageIcon(memberIcon.getScaledInstance(resize(115), resize(115), Image.SCALE_SMOOTH)));
					memberLabel.setVisible(true);
					memberLabel.setOpaque(true);
					this.add(memberLabel);
					memberLabel.setBounds(resize(215) + i * resize(115), this.spaceButton.getHeight() / 4 , memberLabel.getIcon().getIconWidth(), memberLabel.getIcon().getIconHeight());
				} catch (IOException e) {
					LOGGER.log(Level.SEVERE, "Unable to find pawn image.", e);
					memberLabel.setToolTipText("This is the " + f.getColor().toString().toLowerCase() + "member.");
					memberLabel.setVisible(true);
					memberLabel.setOpaque(true);
					this.add(memberLabel);
					memberLabel.setBounds(resize(75) + i * resize(125), resize(335), resize(125), resize(125));
				}
				i++;
			}else{
				break;
			}
		}
	}

	private int resize(int originalSize) {
		return (int) (this.scaleFactor * originalSize);
	}

	public JButton getSpaceButton() {
		return this.spaceButton;
	}

}
