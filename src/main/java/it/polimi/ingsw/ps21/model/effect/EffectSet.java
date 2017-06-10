package it.polimi.ingsw.ps21.model.effect;

import java.io.Serializable;
import java.util.ArrayList;

import it.polimi.ingsw.ps21.controller.UnchosenException;
import it.polimi.ingsw.ps21.model.actions.ExtraAction;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;
import it.polimi.ingsw.ps21.model.properties.PropertiesId;

public class EffectSet implements Serializable {
	
	private Effect[] simultaneousEffects;
	
	public EffectSet(Effect ...effects){
		if (effects.length == 0) {
			simultaneousEffects = new Effect[1];
			simultaneousEffects[0] = new NullEffect();
		}
		else simultaneousEffects = effects;
		
	}
		
		public ArrayList<ExtraAction> activate(Player player) {
			ArrayList<ExtraAction> returnExtraActions = new ArrayList<ExtraAction>();
			for (Effect e: simultaneousEffects){
				returnExtraActions.add(e.activate(player));
			}
			return returnExtraActions;
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
			ImmProperties totalCost=new ImmProperties(0);
			for(Effect e: this.simultaneousEffects)
			{
				totalCost=totalCost.sum(e.getCost());
			}
			return totalCost;
		}

	}