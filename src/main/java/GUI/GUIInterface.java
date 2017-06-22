package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.*;

import it.polimi.ingsw.ps21.client.UserInterface;
import it.polimi.ingsw.ps21.controller.AcceptedAction;
import it.polimi.ingsw.ps21.controller.MatchController;
import it.polimi.ingsw.ps21.controller.MatchData;
import it.polimi.ingsw.ps21.controller.RefusedAction;
import it.polimi.ingsw.ps21.model.deck.DevelopmentCard;
import it.polimi.ingsw.ps21.model.deck.LeaderCard;
import it.polimi.ingsw.ps21.model.effect.EffectSet;
import it.polimi.ingsw.ps21.model.player.PlayerColor;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;
import it.polimi.ingsw.ps21.view.ActionData;
import it.polimi.ingsw.ps21.view.ExtraActionData;

public class GUIInterface implements UserInterface{
	private static final Logger LOGGER = Logger.getLogger(GUIInterface.class.getName());
	private int rulesType;
	private JFrame mainWindow;
	private ImageIcon boardIcon;
	private JLabel boardLabel;
	private final String boardPath = (new File("")).getAbsolutePath().concat("/src/images/board.gif");
	
	
	public GUIInterface (int rulesType){
		this.rulesType = rulesType;
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				try {
					setUpWindow();
				} catch (IOException e) {
					LOGGER.log(Level.SEVERE, "\nUnable to set up a windows due to IOException", e);
				}
				
			}
		});
	}
	
	public void setUpWindow()throws IOException{
		
		mainWindow = new JFrame("Lorenzo il Magnifico");
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainWindow.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
		
		mainWindow.setLayout(new BorderLayout(5, 5));
		mainWindow.setBackground(Color.cyan);
		
		boardLabel = new JLabel();
		mainWindow.add(boardLabel);
		
		
		Image myImage = ImageIO.read(new File(boardPath));
		//boardLabel = new JLabel(new ImageIcon(myImage.getScaledInstance(boardPanel.getWidth(), boardPanel.getHeight(), Image.SCALE_SMOOTH)));
		//boardPanel.add(boardLabel);
		//ImageIcon imageIcon = new ImageIcon(new ImageIcon(myImage).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
		boardLabel.setIcon( new ImageIcon(myImage.getScaledInstance(800, 1000, Image.SCALE_DEFAULT)) );
		
		
		
	
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
	public void matchEnded() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public ImmProperties[] reqPrivileges(int number, ImmProperties[] privilegesValues) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int reqExtraActionChoice(ExtraActionData[] actions) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int reqWorkChoice(DevelopmentCard workCard) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String reqName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean reqIfWantsAdvancedRules() {
		// TODO Auto-generated method stub
		return false;
	}

}
