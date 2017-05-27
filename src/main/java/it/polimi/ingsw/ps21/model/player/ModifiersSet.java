package it.polimi.ingsw.ps21.model.player;

import java.util.EnumMap;

public class ModifiersSet {
	private DiscountsSet discountsMods;
	private DiceModsSet diceMods;
	private ActionModifier actionMods;
	private FinalExcomModifier finalMods;
	private WorkModifier workMods;
	
	public ModifiersSet()
	{
		this.diceMods=new DiceModsSet();
		this.discountsMods= new DiscountsSet();
		this.actionMods= new ActionModifier();
		this.finalMods= new FinalExcomModifier();
		this.workMods= new WorkModifier();
	}

	/**
	 * @return the discountsMods
	 */
	public DiscountsSet getDiscountsMods() {
		return discountsMods;
	}

	/**
	 * @return the diceMods
	 */
	public DiceModsSet getDiceMods() {
		return diceMods;
	}

	/**
	 * @return the actionMods
	 */
	public ActionModifier getActionMods() {
		return actionMods;
	}

	/**
	 * @return the finalMods
	 */
	public FinalExcomModifier getFinalMods() {
		return finalMods;
	}

	/**
	 * @return the workMods
	 */
	public WorkModifier getWorkMods() {
		return workMods;
	}
	
	
}
