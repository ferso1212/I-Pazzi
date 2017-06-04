package it.polimi.ingsw.ps21.model.deck;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ExcommunicationDeck {

	private ArrayList<Excommunication> firstExcommunications;
	private ArrayList<Excommunication> secondExcommunications;
	private ArrayList<Excommunication> thirdExcommunications;
	
	public ExcommunicationDeck(){
		firstExcommunications = new ArrayList<>();
		thirdExcommunications = new ArrayList<>();
		secondExcommunications = new ArrayList<>();
	}
	
	public void shuffle(){
		Collections.shuffle(firstExcommunications);
		Collections.shuffle(secondExcommunications);
		Collections.shuffle(thirdExcommunications);
	}
	
	
	
}
