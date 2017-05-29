package it.polimi.ingsw.ps21.model.deck;

import java.awt.List;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class SubDeck<T extends DevelopmentCard> {
	private Queue<T> firstEra;
	private Queue<T> secondEra;
	private Queue<T> thirdEra;
	
	public SubDeck(){
		firstEra = new ArrayDeque<>();
		secondEra = new ArrayDeque<>();
		thirdEra = new ArrayDeque<>();
	}
	
	public void addCard(T card) throws IllegalCardException{
		switch (card.cardEra) {
		case 1:
			firstEra.add(card);
			return;
		case 2:
			secondEra.add(card);		
			return;
		case 3:
			thirdEra.add(card);
			return;
		default:
			throw new IllegalCardException();
		}
	}
	
	public T getCard(int era) throws IllegalCardException {
		T result;
		switch (era) {
		case 1:
			result = firstEra.poll();
			firstEra.remove(result);
			return result;
		case 2:
			result = secondEra.poll();
			secondEra.remove(result);
			return result;
		case 3:
			result = thirdEra.poll();
			thirdEra.remove(result);
			return result;
		default:
			throw new IllegalCardException();
		}
	}
	
	
	public void shuffle(){		
		Random generator = new Random();
		ArrayList<T> tempDeck = new ArrayList<>();
		Queue<T> shuffledDeck = new ArrayDeque<>();
		while(!firstEra.isEmpty()){
			tempDeck.add(firstEra.poll());
		}
		while (!tempDeck.isEmpty()){
			int i = generator.nextInt(tempDeck.size() -1);
			shuffledDeck.add(tempDeck.get(i));
			tempDeck.remove(i);
		}
		firstEra = shuffledDeck;
		shuffledDeck = new ArrayDeque<>();
		tempDeck = new ArrayList<>();
		while(!secondEra.isEmpty()){
			tempDeck.add(firstEra.poll());
		}
		while (!tempDeck.isEmpty()){
			int i = generator.nextInt(tempDeck.size() -1);
			shuffledDeck.add(tempDeck.get(i));
			tempDeck.remove(i);
		}
		secondEra = shuffledDeck;
		shuffledDeck = new ArrayDeque<>();
		tempDeck = new ArrayList<>();
		while(!thirdEra.isEmpty()){
			tempDeck.add(firstEra.poll());
		}
		while (!tempDeck.isEmpty()){
			int i = generator.nextInt(tempDeck.size() -1);
			shuffledDeck.add(tempDeck.get(i));
			tempDeck.remove(i);
		}
		thirdEra = shuffledDeck;
	}

	public boolean isEmpty() {
		return firstEra.isEmpty() && secondEra.isEmpty() && thirdEra.isEmpty();
	}
	
	public String toString(){
	 StringBuilder temp = new StringBuilder();
	 temp.append("{ " + firstEra.toString() + "\n" + secondEra.toString() + "\n" + thirdEra.toString() + " }");
	 return temp.toString();
	}
	
}

