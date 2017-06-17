package it.polimi.ingsw.ps21.client;

import java.awt.GridLayout;
import java.awt.Image;
import java.util.ArrayList;

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
	private Jpanel
	
	
	public GUIInterface (int rulesType){
		this.rulesType = rulesType;
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				setUpWindow();
				
			}
		});
	}
	
	public void setUpWindow(){
		mainWindow = new JFrame("Lorenzo il Magnifico");
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainWindow.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
		mainWindow.setContentPane(contentPane);
		
		
		boardIcon = new ImageIcon("Users/darix/OneDrive/Immagini/LorenzoGraphics/gameboard_f_c.jpeg");
		boardLabel = new JLabel(boardIcon);
		boardLabel.setVisible(true);
		
		
		mainWindow.setLayout(new GridLayout(1, 2));
		mainWindow.add(boardLabel, 0);
		
		mainWindow.pack();
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
