package it.polimi.ingsw.ps21.client.GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JLabel;
import javax.swing.JPanel;

import it.polimi.ingsw.ps21.controller.NotAdvancedPlayerException;
import it.polimi.ingsw.ps21.model.player.MembersColor;
import it.polimi.ingsw.ps21.model.player.PlayerColor;
import it.polimi.ingsw.ps21.view.MatchData;
import it.polimi.ingsw.ps21.view.PlayerData;

public class ActionPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7551327098728821261L;
	private static final Logger LOGGER = Logger.getLogger(ActionPanel.class.getSimpleName());
	private FamilyMemberPanel memberPanel;
	private JLabel roundInfo;
	private ServantsSlider servantsSlider;
	private PlayerColor playerId;
	private LeaderPanel leaderPanel;
	private boolean isAdvanced;
	private NullActionButton nullActionButton;

	public ActionPanel(MatchData matchInfo, PlayerColor playerId, Dimension mainFrameDimension,
			ActionListener nullActionListener) {

		this.playerId = playerId;
		this.isAdvanced = false;
		this.setPreferredSize(
				new Dimension((int) (mainFrameDimension.getWidth() / 2), (int) (mainFrameDimension.getHeight() / 2)));
		this.setLayout(new BorderLayout(5, 2));
		roundInfo = new JLabel(
				"This is the " + matchInfo.getRound() + " of the " + matchInfo.getPeriod() + "° period.");

		JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		centerPanel.setOpaque(false);// used to center label roundinfo
		centerPanel.add(roundInfo);
		this.add(centerPanel, BorderLayout.PAGE_START);

		// PAGE_END
		memberPanel = new FamilyMemberPanel(playerId);
		servantsSlider = new ServantsSlider();
		for (PlayerData p : matchInfo.getPlayers()) {
			if (p.getId() == this.playerId)
				servantsSlider.updateSlider(p);
		}
		JPanel temp = new JPanel();
		temp.setLayout(new GridBagLayout());
		temp.setOpaque(false);
		GridBagConstraints constraints = new GridBagConstraints();
		nullActionButton = new NullActionButton(playerId);
		nullActionButton.addActionListener(nullActionListener);
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.WEST;
		temp.add(nullActionButton, constraints);
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.CENTER;
		temp.add(memberPanel, constraints);

		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.EAST;

		temp.add(new JLabel("Servants to add:"));
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.CENTER;
		temp.add(servantsSlider, constraints);
		this.add(temp, BorderLayout.PAGE_END);
		this.setVisible(true);
		this.setOpaque(false);
	}

	public ActionPanel(MatchData matchInfo, PlayerColor playerId, Dimension mainFrameDimension,
			ActionListener nullActionListener, ActionListener leaderListener) {

		this.playerId = playerId;
		this.isAdvanced = true;
		this.setPreferredSize(
				new Dimension((int) (mainFrameDimension.getWidth() / 2), (int) (mainFrameDimension.getHeight() / 2)));
		this.setLayout(new BorderLayout(5, 2));
		roundInfo = new JLabel(
				"This is the " + matchInfo.getRound() + " of the " + matchInfo.getPeriod() + "° period.");

		JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		centerPanel.setOpaque(false);// used to center label roundinfo
		centerPanel.add(roundInfo);
		this.add(centerPanel, BorderLayout.PAGE_START);

		// LINE
		leaderPanel = new LeaderPanel(this.getPreferredSize(), leaderListener);
		this.add(leaderPanel, BorderLayout.CENTER);

		// PAGE_END
		memberPanel = new FamilyMemberPanel(playerId);
		servantsSlider = new ServantsSlider();
		for (PlayerData p : matchInfo.getPlayers()) {
			if (p.getId() == this.playerId)
				servantsSlider.updateSlider(p);
		}
		JPanel temp = new JPanel();
		temp.setLayout(new GridBagLayout());
		temp.setOpaque(false);
		GridBagConstraints constraints = new GridBagConstraints();

		nullActionButton = new NullActionButton(playerId);
		nullActionButton.addActionListener(nullActionListener);
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.WEST;
		temp.add(nullActionButton, constraints);

		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.CENTER;
		temp.add(memberPanel, constraints);

		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.EAST;

		temp.add(new JLabel("Servants to add:"));
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.EAST;
		temp.add(servantsSlider, constraints);
		this.add(temp, BorderLayout.PAGE_END);
		this.setVisible(true);
		this.setOpaque(false);
	}

	public void updateActionPanel(MatchData matchInfo) {
		roundInfo.setText("This is the " + matchInfo.getRound() + " of the " + matchInfo.getPeriod() + "° period.");
		for (PlayerData p : matchInfo.getPlayers()) {
			if (p.getId() == this.playerId) {
				servantsSlider.updateSlider(p);
				memberPanel.update(p);

				if (this.isAdvanced) {
					try {
						leaderPanel.update(p.getLeaders());
					} catch (NotAdvancedPlayerException e) {
						LOGGER.log(Level.SEVERE, "The Player is not an AdvancedPlayer!", e);
					}
				}
			}
		}

	}

	public MembersColor getChosenColor() {
		return memberPanel.getChosenColor();
	}

	public int getChosenServants() {
		return servantsSlider.getValue();
	}

	public void setActionPanel() {
		if (isAdvanced) {
			for (int i = 0; i < 4; i++) {
				leaderPanel.setButtons();
			}
		}
	}

	public LeaderPanel getLeaderPanel() {
		return this.leaderPanel;
	}

	public NullActionButton getNullButton() {
		return this.nullActionButton;
	}

}
