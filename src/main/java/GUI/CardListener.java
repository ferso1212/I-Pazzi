package GUI;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class CardListener implements ActionListener{
	

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource().getClass().equals(DevelopmentCardLabel.class)){
			
			DevelopmentActionPanel actionPanel = new DevelopmentActionPanel((DevelopmentCardLabel)event.getSource());

			
		}else if (event.getSource() instanceof WorkActionButton){
			
			}
		}
	
	

}
