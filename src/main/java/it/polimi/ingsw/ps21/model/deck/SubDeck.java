package it.polimi.ingsw.ps21.model.deck;

import java.util.List;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import it.polimi.ingsw.ps21.view.PlayerData;

public class SubDeck<T extends DevelopmentCard> {
	private final static Logger LOGGER = Logger.getLogger(SubDeck.class.getName());

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
			int i = generator.nextInt(tempDeck.size());
			shuffledDeck.add(tempDeck.get(i));
			tempDeck.remove(i);
		}
		firstEra = shuffledDeck;
		shuffledDeck = new ArrayDeque<>();
		tempDeck = new ArrayList<>();
		while(!secondEra.isEmpty()){
			tempDeck.add(secondEra.poll());
		}
		while (!tempDeck.isEmpty()){
			int i = generator.nextInt(tempDeck.size());
			shuffledDeck.add(tempDeck.get(i));
			tempDeck.remove(i);
		}
		secondEra = shuffledDeck;
		shuffledDeck = new ArrayDeque<>();
		tempDeck = new ArrayList<>();
		while(!thirdEra.isEmpty()){
			tempDeck.add(thirdEra.poll());
		}
		while (!tempDeck.isEmpty()){
			int i = generator.nextInt(tempDeck.size());
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
	 Stream<T> tempStream = firstEra.stream();
	 List<T> firstEraCards = tempStream.collect(Collectors.toList());
	 tempStream = secondEra.stream();
	 List<T> secondEraCards = tempStream.collect(Collectors.toList());
	 tempStream = thirdEra.stream();
	 List<T> thirdEraCards = tempStream.collect(Collectors.toList());
	 temp.append("\n----------\t First Era \t-------------");
	 for (T c: firstEraCards){
		 temp.append("\n-" + c.toString());
	 }
	 temp.append("\n----------\t Second Era \t-------------");
	 for (T c: secondEraCards){
		 temp.append("\n-" + c.toString());
	 }
	 temp.append("\n----------\t Third Era \t-------------");
	 for (T c: thirdEraCards){
		 temp.append("\n-" + c.toString());
	 }
	 return temp.toString();
	}
	
	public SubDeck<T> clone(){
		SubDeck<T> clone = new SubDeck<>();
		ArrayList<T> clonedCards = new ArrayList<>();
		clonedCards.addAll(firstEra);
		clonedCards.addAll(secondEra);
		clonedCards.addAll(thirdEra);
		for (T c: clonedCards)
			try {
				clone.addCard((T)c);
			} catch (IllegalCardException e) {
				LOGGER.log(Level.WARNING, "Error cloning subdeck", e);
			}
		return clone;
	}
	
}

