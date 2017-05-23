package it.polimi.ingsw.ps21.model;

public class DiscountModifier {
	private int territoryDiscount;
	private int buildingDiscount;
	private int characterDiscount;
	private int ventureDiscount;

	public DiscountModifier() {
		this.territoryDiscount = 0;
		this.buildingDiscount = 0;
		this.characterDiscount = 0;
		this.ventureDiscount = 0;
	}

	/**
	 * @return the territoryDiscount
	 */
	public int getTerritoryDiscount() {
		return territoryDiscount;
	}

	/**
	 * @param territoryDiscount the territoryDiscount to set
	 */
	public void setTerritoryDiscount(int territoryDiscount) {
		this.territoryDiscount = territoryDiscount;
	}

	/**
	 * @return the buildingDiscount
	 */
	public int getBuildingDiscount() {
		return buildingDiscount;
	}

	/**
	 * @param buildingDiscount the buildingDiscount to set
	 */
	public void setBuildingDiscount(int buildingDiscount) {
		this.buildingDiscount = buildingDiscount;
	}

	/**
	 * @return the characterDiscount
	 */
	public int getCharacterDiscount() {
		return characterDiscount;
	}

	/**
	 * @param characterDiscount the characterDiscount to set
	 */
	public void setCharacterDiscount(int characterDiscount) {
		this.characterDiscount = characterDiscount;
	}

	/**
	 * @return the ventureDiscount
	 */
	public int getVentureDiscount() {
		return ventureDiscount;
	}

	/**
	 * @param ventureDiscount the ventureDiscount to set
	 */
	public void setVentureDiscount(int ventureDiscount) {
		this.ventureDiscount = ventureDiscount;
	}
	
	

}
