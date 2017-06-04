package it.polimi.ingsw.ps21.model.deck;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Class that implements an aggregator of excommunications based on its period.
 * This class offers different functions like shuffling and picking excoms
 * @author gullit
 *
 */
public class ExcommunicationDeck {

	private ArrayList<Excommunication> firstExcommunications;
	private ArrayList<Excommunication> secondExcommunications;
	private ArrayList<Excommunication> thirdExcommunications;
	
	public ExcommunicationDeck(){
		firstExcommunications = new ArrayList<>();
		thirdExcommunications = new ArrayList<>();
		secondExcommunications = new ArrayList<>();
	}
	
	public void addCard(Excommunication excom){
		if (excom.getEra() == 1) firstExcommunications.add(excom);
		else
			if (excom.getEra() == 2) secondExcommunications.add(excom);
			else
				if (excom.getEra() == 3) thirdExcommunications.add(excom);
	}
	
	public Excommunication getExcommunication(int period){
		if (period == 1) return firstExcommunications.get(0);
		else
			if (period == 2) return secondExcommunications.get(0);
		else
			return thirdExcommunications.get(0);
	}
	
	public void shuffle(){
		Collections.shuffle(firstExcommunications);
		Collections.shuffle(secondExcommunications);
		Collections.shuffle(thirdExcommunications);
	}

	public boolean isEmpty() {
		return firstExcommunications.isEmpty() && secondExcommunications.isEmpty() && thirdExcommunications.isEmpty();
	}
	
	
	
}
