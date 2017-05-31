package it.polimi.ingsw.ps21.controller;

import java.util.ArrayList;
import java.util.Optional;

import it.polimi.ingsw.ps21.model.deck.Effect;
import it.polimi.ingsw.ps21.view.Chooser;

public class EffectChoice implements Choosable{

	
	public ArrayList<Chooser> acceptedChoosers;
	public ArrayList<Effect> possibilities;
	public Optional<Effect> choosen;
	
	
	public EffectChoice(Effect... choices){
		acceptedChoosers = new ArrayList<>();
		possibilities = new ArrayList<>();
		choosen = Optional.empty();
		for (Effect e: choices){
			possibilities.add(e);
		}
	}
	
	public Effect[] getPossibilities() {
		return (Effect[]) possibilities.toArray();
	}
	
	public Effect getChoosen() throws UnchosenException{
		if (choosen.isPresent()) return choosen.get();
		else throw new UnchosenException();
	}
	
	public void setChoosen(Chooser chooser, Effect choosen) throws UnacceptedChooser{
		if (!(acceptedChoosers.contains(chooser))) throw new UnacceptedChooser();
		if (!(possibilities.contains(choosen))) throw new IllegalArgumentException();
		this.choosen = Optional.of(choosen);
	}
	@Override
	public void acceptChooser(Chooser chooser) {
		if (!(acceptedChoosers.contains(chooser)))acceptedChoosers.add(chooser);
	}

}
