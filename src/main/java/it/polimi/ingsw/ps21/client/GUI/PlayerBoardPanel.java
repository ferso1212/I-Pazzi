package it.polimi.ingsw.ps21.client.GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import it.polimi.ingsw.ps21.controller.PlayerData;
import it.polimi.ingsw.ps21.model.deck.DevelopmentCard;
import it.polimi.ingsw.ps21.model.deck.DevelopmentCardType;
import it.polimi.ingsw.ps21.model.properties.PropertiesId;

public class PlayerBoardPanel extends JPanel{
	
	private JLabel coins;
	private JLabel woods;
	private JLabel stones;
	private JLabel servants;
	private JLabel victoryPoints;
	private JLabel militaryPoints;
	private JLabel faithPoints;
	private transient BufferedImage playerBoardImage; 
	private JLabel[] territory;
	private JLabel[] building;
	private double resizeParam;
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

 	
 	public PlayerBoardPanel(String boardPath, PlayerData playerInfo){
 		super(true);  //crea un JPanel con doubleBuffered true
 		try {
 			setImage(ImageIO.read(new File(boardPath)));
 		} catch (IOException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
 		setProperties(playerInfo);
 	}
 	
 	public void setImage(BufferedImage img){
 		this.playerBoardImage = img;
 		this.resizeParam = this.playerBoardImage.getHeight() / (screenSize.getHeight() / 2);
 	}
 	
 	@Override
 	public void paintComponent(Graphics g){
 		super.paintComponent(g);
 		try {
			g.drawImage(resizeImage(playerBoardImage, (int)(this.playerBoardImage.getWidth() * this.getHeight() / this.playerBoardImage.getHeight()), this.getHeight(), BufferedImage.TYPE_INT_RGB), 0, 0, null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 	}
 	
 	private BufferedImage resizeImage(BufferedImage originalImage, int width, int height, int type) throws IOException {  
        BufferedImage resizedImage = new BufferedImage(width, height, type);  
        Graphics2D g = resizedImage.createGraphics();  
        g.drawImage(originalImage, 0, 0, width, height, null);  
        g.dispose();  
        
        return resizedImage;  
    }

	public BufferedImage getPlayerBoardImage() {
		return this.playerBoardImage;
	}
	
	private void setProperties(PlayerData playerInfo){
		
		coins = new JLabel(Integer.toString(playerInfo.getProperties().get(PropertiesId.COINS)));
		woods = new JLabel(Integer.toString(playerInfo.getProperties().get(PropertiesId.WOOD)));
		stones = new JLabel(Integer.toString(playerInfo.getProperties().get(PropertiesId.STONES)));
		servants = new JLabel(Integer.toString(playerInfo.getProperties().get(PropertiesId.SERVANTS)));
		victoryPoints = new JLabel(Integer.toString(playerInfo.getProperties().get(PropertiesId.VICTORYPOINTS)));
		militaryPoints = new JLabel(Integer.toString(playerInfo.getProperties().get(PropertiesId.MILITARYPOINTS)));
		faithPoints = new JLabel(Integer.toString(playerInfo.getProperties().get(PropertiesId.FAITHPOINTS)));
		this.setLayout(null);
		
		coins.setBackground(Color.WHITE);
		coins.setOpaque(true);
		coins.setHorizontalAlignment(JLabel.CENTER);
		woods.setBackground(Color.WHITE);
		woods.setOpaque(true);
		woods.setHorizontalAlignment(JLabel.CENTER);
		stones.setBackground(Color.WHITE);
		stones.setOpaque(true);
		stones.setHorizontalAlignment(JLabel.CENTER);
		servants.setBackground(Color.WHITE);
		servants.setOpaque(true);
		servants.setHorizontalAlignment(JLabel.CENTER);
		faithPoints.setBackground(Color.WHITE);
		faithPoints.setOpaque(true);
		faithPoints.setHorizontalAlignment(JLabel.CENTER);
		militaryPoints.setBackground(Color.WHITE);
		militaryPoints.setOpaque(true);
		militaryPoints.setHorizontalAlignment(JLabel.CENTER);
		victoryPoints.setBackground(Color.WHITE);
		victoryPoints.setOpaque(true);
		victoryPoints.setHorizontalAlignment(JLabel.CENTER);
		
		
		this.add(coins).setBounds(resize(60), resize(405), resize(25), resize(25));
		this.add(woods).setBounds(resize(170), resize(405), resize(25), resize(25));
		this.add(stones).setBounds(resize(275), resize(405), resize(25), resize(25));
		this.add(servants).setBounds(resize(375), resize(405), resize(25), resize(25));
		this.add(faithPoints).setBounds(resize(745), resize(110), resize(25), resize(25));
		this.add(militaryPoints).setBounds(resize(750), resize(240), resize(25), resize(25));
		this.add(victoryPoints).setBounds(resize(750), resize(400), resize(25), resize(25));
	}
	
	private int resize(int originalSize){
		return (int)(originalSize * this.resizeParam);
	}
	
	public void updateProperties(PlayerData playerInfo){
		coins.setText(Integer.toString(playerInfo.getProperties().get(PropertiesId.COINS)));
		woods.setText(Integer.toString(playerInfo.getProperties().get(PropertiesId.WOOD)));
		stones.setText(Integer.toString(playerInfo.getProperties().get(PropertiesId.STONES)));
		servants.setText(Integer.toString(playerInfo.getProperties().get(PropertiesId.SERVANTS)));
		faithPoints.setText(Integer.toString(playerInfo.getProperties().get(PropertiesId.FAITHPOINTS)));
		militaryPoints.setText(Integer.toString(playerInfo.getProperties().get(PropertiesId.MILITARYPOINTS)));
		victoryPoints.setText(Integer.toString(playerInfo.getProperties().get(PropertiesId.VICTORYPOINTS)));
		updateTerritory(playerInfo.getCards().get(DevelopmentCardType.TERRITORY));
		updateBuilding(playerInfo.getCards().get(DevelopmentCardType.BUILDING));
	}
	
	private void updateTerritory(ArrayList<DevelopmentCard> territoryCards){
		int i=0;
		for(DevelopmentCard c : territoryCards){
			try {
				BufferedImage cardImage = ImageIO.read(new File((new File("")).getAbsolutePath().concat("/src/images/DevelopmentCards/" + c.getName().replaceAll(" ", "") + ".png")));
				JLabel territoryLabel = new JLabel();
				territoryLabel.setIcon(new ImageIcon(cardImage.getScaledInstance(resize(90), resize(125), Image.SCALE_DEFAULT)));
				territoryLabel.setToolTipText("<html><body><div><img src=\"file:src/images/DevelopmentCards/"+c.getName().replace(" ", "")+".png\"></div><div>"+ c.toString() + "</div></body></html> ");
				this.add(territoryLabel);
				territoryLabel.setBounds(resize(20) + i * resize(110), resize(225), resize(90), resize(125));
				i++;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void updateBuilding(ArrayList<DevelopmentCard> buildingCards){
		int i=0;
		for(DevelopmentCard c : buildingCards){
			try {
				BufferedImage cardImage = ImageIO.read(new File((new File("")).getAbsolutePath().concat("/src/images/DevelopmentCards/" + c.getName().replaceAll(" ", "") + ".png")));
				JLabel buildingLabel = new JLabel();
				buildingLabel.setIcon(new ImageIcon(cardImage.getScaledInstance(resize(90), resize(125), Image.SCALE_DEFAULT)));
				buildingLabel.setToolTipText("<html><body><div><img src=\"file:src/images/DevelopmentCards/"+c.getName().replace(" ", "")+".png\"></div><div>"+ c.toString() + "</div></body></html> ");
				this.add(buildingLabel);
				buildingLabel.setBounds(resize(20) + i * resize(110), resize(40), resize(90), resize(125));
				i++;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}


}
