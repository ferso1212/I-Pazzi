package it.polimi.ingsw.ps21.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;

import it.polimi.ingsw.ps21.controller.AcceptedAction;
import it.polimi.ingsw.ps21.controller.MatchData;
import it.polimi.ingsw.ps21.controller.RefusedAction;
import it.polimi.ingsw.ps21.model.deck.LeaderCard;
import it.polimi.ingsw.ps21.model.effect.EffectSet;
import it.polimi.ingsw.ps21.model.player.PlayerColor;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;
import it.polimi.ingsw.ps21.view.ActionData;

public class GUIInterface implements UserInterface{
	
	private int rulesType;
	private JFrame mainWindow;
	private ImageIcon boardIcon;
	private JLabel boardLabel;
	private JPanel boardPanel;
	private final String boardPath = (new File("")).getAbsolutePath().concat("/src/images/board.gif");
	
	
	public GUIInterface (int rulesType){
		this.rulesType = rulesType;
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				try {
					setUpWindow();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
	}
	
	public void setUpWindow()throws IOException{
		
		mainWindow = new JFrame("Lorenzo il Magnifico");
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainWindow.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
		
		mainWindow.setLayout(new GridLayout(1, 2));
		
		boardPanel = new JPanel();
		JPanel board2 = new JPanel();
		mainWindow.add(boardPanel, 0);
		mainWindow.add(board2, 1);
		boardPanel.setLayout(new BorderLayout());
		boardPanel.setBackground(new Color(0, 127, 255));
		board2.setBackground(new Color(255, 191, 0));
		
		boardLabel = new JLabel();
		boardPanel.add(boardLabel);
		
		mainWindow.pack();
		
		Image myImage = ImageIO.read(new File(boardPath));
		//boardLabel = new JLabel(new ImageIcon(myImage.getScaledInstance(boardPanel.getWidth(), boardPanel.getHeight(), Image.SCALE_SMOOTH)));
		//boardPanel.add(boardLabel);
		//ImageIcon imageIcon = new ImageIcon(new ImageIcon(myImage).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
		boardLabel.setIcon( new ImageIcon(myImage.getScaledInstance(800, 1000 , Image.SCALE_DEFAULT)) );
		
		
		
	
		mainWindow.setVisible(true);
	}

	@Override
	public void playMatch() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateView(MatchData match) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ActionData makeAction() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void showInfo(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean reqVaticanChoice() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int reqCostChoice(ArrayList<ImmProperties> costChoices) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int reqEffectChoice(EffectSet[] effectChoice) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int chooseLeaderCard(LeaderCard[] possibleChoices) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void showMessage(AcceptedAction mess) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showMessage(RefusedAction mess) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setID(PlayerColor id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String nextInput() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ImmProperties[] reqPrivileges(int number) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void matchEnded() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int reqExtraActionChoice(ActionData[] actions) {
		// TODO Auto-generated method stub
		return 0;
	}

}
