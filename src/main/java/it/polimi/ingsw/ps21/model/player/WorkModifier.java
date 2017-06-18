package it.polimi.ingsw.ps21.model.player;

import it.polimi.ingsw.ps21.model.actions.WorkType;


public class WorkModifier {
	private int harvestModifier;
	private int productionModifier;
	
	/**
	 * Returns the value of the production modifier. Production modifiers are
	 * modifiers that increase/reduce the production action value
	 * 
	 * @return value of the production modifier.
	 */
	public int getProdMod() {
		return this.productionModifier;
	}

	/**
	 * Returns the value of the harvest modifier. Harvest modifiers are
	 * modifiers that increase/reduce the harvest action value
	 * 
	 * @return value of the harvest modifier.
	 */
	public int getHarvMod() {
		return this.harvestModifier;
	}

	/**
	 * @param harvestModifier the harvestModifier to set
	 */
	public void setHarvestModifier(int harvestModifier) {
		this.harvestModifier = harvestModifier;
	}

	/**
	 * @param productionModifier the productionModifier to set
	 */
	public void setProductionModifier(int productionModifier) {
		this.productionModifier = productionModifier;
	}
	
	public int getWorkMod(WorkType type) throws IllegalArgumentException
	{
		if(type==WorkType.HARVEST) return this.harvestModifier;
		else if(type==WorkType.PRODUCTION) return this.productionModifier;
		else throw new IllegalArgumentException();
	}
	
	public void setWorkMod(WorkType type, int value) throws IllegalArgumentException
	{
		if(type==WorkType.HARVEST) this.harvestModifier=value;
		else if(type==WorkType.PRODUCTION) this.productionModifier=value;
		else throw new IllegalArgumentException();
	}
}
