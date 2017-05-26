package it.polimi.ingsw.ps21.model;

import java.util.ArrayList;

public class SubDeck<T extends DevelopmentCard> {
	private ArrayList<T> firstEra;
	private ArrayList<T> secondEra;
	private ArrayList<T> thirdEra;
	
	public SubDeck(){
		firstEra = new ArrayList<T>();
		secondEra = new ArrayList<T>();
		thirdEra = new ArrayList<T>();
	}
	
	public void addCard(T card, int era) throws Exception{
		switch (era) {
		case 1:
			firstEra.add(card);
			break;
			
		case 2:
			secondEra.add(card);		
			break;
		case 3:
			thirdEra.add(card);
			break;
		default:
			throw new Exception();
		}
	}
	
	public T getCard(int era) {
		T result;
		switch (era) {
		case 1:
			result = firstEra.get(1);
			firstEra.remove(result);
			return result;
		case 2:
			result = secondEra.get(1);
			secondEra.remove(result);
			return result;
		case 3:
			result = thirdEra.get(1);
			thirdEra.remove(result);
			return result;
		default:
			throw new RuntimeException("Illegal Eran number");
		}
	}
}
