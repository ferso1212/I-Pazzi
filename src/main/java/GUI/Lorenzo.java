package GUI;

import java.awt.EventQueue;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import it.polimi.ingsw.ps21.controller.MatchData;

import java.awt.FlowLayout;
import javax.swing.JTabbedPane;

public class Lorenzo {

	private JFrame mainWindow;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Lorenzo window = new Lorenzo();
					window.mainWindow.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Lorenzo() {
		initialize(3, false);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(int numberOfPlayer, boolean isAdvanced) {
		mainWindow = new JFrame("Lorenzo il Magnifico");
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainWindow.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
		mainWindow.getContentPane().setLayout(new GridLayout(1, 2, 0, 0));

		JPanel boardPanel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) boardPanel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		mainWindow.getContentPane().add(boardPanel);

		JLabel lblNewLabel = new JLabel();
		boardPanel.add(lblNewLabel);
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\darix\\I-Pazzi\\src\\images\\board.gif"));

		JPanel generalViewPanel = new JPanel();
		mainWindow.getContentPane().add(generalViewPanel);
		generalViewPanel.setLayout(new GridLayout(2, 1, 0, 0));

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);

		generalViewPanel.add(tabbedPane);

		for (int i = 1; i <= numberOfPlayer; i++) {
			JLabel playerTile = new JLabel("Player " + i + " Tile");
			playerTile.setHorizontalAlignment(SwingConstants.LEFT);
			playerTile.setIcon(new ImageIcon("C:\\Users\\darix\\I-Pazzi\\src\\images\\Lorenzo_Punchboard_FRONT_compressed\\punchboard_f_c_03.jpg"));
			tabbedPane.addTab("Player " + i + " Tile", null, playerTile, null);
		}

	}

}
