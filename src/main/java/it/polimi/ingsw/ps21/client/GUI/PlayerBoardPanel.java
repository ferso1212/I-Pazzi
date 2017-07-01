package it.polimi.ingsw.ps21.client.GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import it.polimi.ingsw.ps21.controller.PlayerData;
import it.polimi.ingsw.ps21.model.properties.PropertiesId;

public class PlayerBoardPanel extends JPanel{
	
	private JLabel coins;
	private JLabel woods;
	private JLabel stones;
	private JLabel servants;
	private transient BufferedImage playerBoardImage; 
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
		
		this.add(coins).setBounds(resize(60), resize(405), resize(25), resize(25));
		this.add(woods).setBounds(resize(170), resize(405), resize(25), resize(25));
		this.add(stones).setBounds(resize(275), resize(405), resize(25), resize(25));
		this.add(servants).setBounds(resize(375), resize(405), resize(25), resize(25));
	}
	
	private int resize(int originalSize){
		return (int)(originalSize * this.resizeParam);
	}
	
	public void updateProperties(PlayerData playerInfo){
		coins = new JLabel(Integer.toString(playerInfo.getProperties().get(PropertiesId.COINS)));
		woods = new JLabel(Integer.toString(playerInfo.getProperties().get(PropertiesId.WOOD)));
		stones = new JLabel(Integer.toString(playerInfo.getProperties().get(PropertiesId.STONES)));
		servants = new JLabel(Integer.toString(playerInfo.getProperties().get(PropertiesId.SERVANTS)));
	}


}
