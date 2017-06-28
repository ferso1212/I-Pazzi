package it.polimi.ingsw.ps21.client.GUI;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

public class NameDialog extends JDialog {
	
	
	private JTextField nameField;
	private JButton okButton;
	private String chosenName;
	private Boolean actionPerformed = false;
	
	public NameDialog(){
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				nameField = new JTextField("Insert your name here please ");
				okButton = new JButton("Send name");
				okButton.addActionListener(new ButtonHandler());
				getContentPane().setLayout(new GridLayout(2, 1));
				setSize(new Dimension(400, 200));
				getContentPane().add(nameField);
				getContentPane().add(okButton);
			}
		});
	}
	
	public class ButtonHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			if (event.getSource().equals(okButton)){
				chosenName = nameField.getText();
				synchronized (actionPerformed) {
					actionPerformed = true;
				}
			}
			
		}
		
	}
	
	
	public String getName(){
		synchronized (actionPerformed) {while(!actionPerformed);}
		return chosenName;
		
	}

}
