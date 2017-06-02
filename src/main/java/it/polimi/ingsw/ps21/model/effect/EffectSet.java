package it.polimi.ingsw.ps21.model.effect;

import java.util.ArrayList;

import it.polimi.ingsw.ps21.controller.UnchosenException;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;
import it.polimi.ingsw.ps21.model.properties.PropertiesId;

public class EffectSet {
	
	private Effect[] simultaneousEffects;
	
	public EffectSet(Effect ...effects){
		if (effects.length == 0 || effects == null) {
			simultaneousEffects = new Effect[1];
			simultaneousEffects[0] = new NullEffect();
		}
		else simultaneousEffects = effects;
		
	}
		
		public void activate(Player player) throws UnchosenException {
			for (Effect e: simultaneousEffects){
				e.activate(player);
			}
		}
		
		public String[] getTypes(){
			ArrayList<String> temp = new ArrayList<>();
			for (Effect e: simultaneousEffects){
				temp.add(e.getType());
			}
			return (String[]) temp.toArray();
		}
		
		/**Returns the total cost to activate all the effects in the set.
		 * 
		 * @return an immutable object that contains the properties costs of all the effects in the set.
		 */
		public ImmProperties getTotalCost()
		{
			ImmProperties totalCost=new ImmProperties();
			for(Effect e: this.simultaneousEffects)
			{
				totalCost=totalCost.sum(e.getCost());
			}
			return totalCost;
		}

	}