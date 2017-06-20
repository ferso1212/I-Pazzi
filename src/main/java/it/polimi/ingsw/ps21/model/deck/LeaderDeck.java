package it.polimi.ingsw.ps21.model.deck;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EmptyStackException;
import java.util.List;

public class LeaderDeck{
	
	private ArrayDeque<LeaderCard> leaderCards;
	
	public LeaderDeck(){
		leaderCards = new ArrayDeque<>();
		
	}
	
	public void addCard(LeaderCard newCard){
		leaderCards.add(newCard);
	}
	
	public void shuffle(){
		List<LeaderCard> oldDeck = new ArrayList<>();
		oldDeck.addAll(leaderCards);
		Collections.shuffle(oldDeck);
		leaderCards = new ArrayDeque<>();
		leaderCards.addAll(oldDeck);
	}
	
	public LeaderCard getCard() throws EmptyStackException {
		if (leaderCards.isEmpty()) throw new EmptyStackException();
		return leaderCards.poll();
	}

	public LeaderDeck copy() {
		LeaderDeck copy = new LeaderDeck();
		for (LeaderCard c : leaderCards){
			copy.addCard(c);
		}
		return copy;
	}

	public boolean isEmpty() {
		return leaderCards.isEmpty();
	}

}
