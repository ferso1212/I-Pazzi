package it.polimi.ingsw.ps21.client.GUI;

import java.awt.Container;
import java.awt.Image;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import it.polimi.ingsw.ps21.model.deck.DevelopmentCardType;

public class DevelopmentCardButton extends JButton{
	
	private static final Logger LOGGER = Logger.getLogger(DevelopmentCardButton.class.getSimpleName());
	private transient Image cardImage;
	private double scaleFactor;
	private int floor;
	private DevelopmentCardType tower;
	
	public DevelopmentCardButton(double scaleFactor, int floor, int tower){
		this.scaleFactor = scaleFactor;
		this.floor = floor;
		switch (tower) {
		case 0:
			this.tower = DevelopmentCardType.TERRITORY;
			break;
		case 1:
			this.tower = DevelopmentCardType.CHARACTER;
			break;
		case 2:
			this.tower = DevelopmentCardType.BUILDING;
			break;
		case 3:
			this.tower = DevelopmentCardType.VENTURE;
			break;
		default:
			this.tower = DevelopmentCardType.TERRITORY;
			break;
		}
		this.setVisible(false);
	}

	public Image getCardImage() {
		return cardImage;
	}
	
	public Container getFather(){
		return this.getParent();
	}
	
	public void hideButton(){
		this.setVisible(false);
	}
	
	public void update(String name, String description){
		try {
			int width = (int)(470 *scaleFactor);
			int height = (int)(754 * scaleFactor);
			this.cardImage = ImageIO.read(new File((new File("")).getAbsolutePath().concat("/src/images/DevelopmentCards/").concat(name.replace(" ","")).concat(".png")));
			this.setIcon(new ImageIcon(cardImage.getScaledInstance(width, height, Image.SCALE_DEFAULT)));
			this.setToolTipText("<html><body><table><tr><td><img style='display: inline;' src=\"file:src/images/DevelopmentCards/"+name.replace(" ", "")+".png\"></td>"+"<td>"+ description + "</td></tr></table></body></html> ");
			
		} catch (IOException e) {
			LOGGER.log(Level.WARNING, "Error finding card name." + name, e);
		}
		
		this.setVisible(true);
	}

	public int getFloor() {
		return floor;
	}

	public DevelopmentCardType getTower() {
		return tower;
	}

	
}
