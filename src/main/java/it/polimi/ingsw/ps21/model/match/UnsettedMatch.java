package it.polimi.ingsw.ps21.model.match;

import java.util.Collections;
import java.util.Vector;

import javax.xml.parsers.ParserConfigurationException;

import it.polimi.ingsw.ps21.model.actions.Action;
import it.polimi.ingsw.ps21.model.player.Player;

public class UnsettedMatch extends Match {

	
	private MatchBuilder builder;
	
	public UnsettedMatch(Player players[]) throws ParserConfigurationException {
		super();
		builder = new MatchBuilder();
		for (Player p: players){
			this.players.add(p);
		}
		orangeDice = 0;
		blackDice = 0;
		whiteDice = 0;
		board.setDeck(builder.makeDeck());
	}
	@Override
	public Match getCopy() throws CloneNotSupportedException {
		// TODO
		return this;
	}
	
	public Match startMatch(){
		Collections.shuffle(players);
		for (int i=0; i <4 ; i++){
			for (Player p: players){
				order.add(p);
			}
		}
		throwDices();
		return new InitialRoundMatch(this);
		
	}
	
	
	@Override
	public Match makeAction(Action nextAction) {
		return this;
	}
	
	

}
