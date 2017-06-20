package it.polimi.ingsw.ps21.model.player;

public class AdvancedModifier {
	private boolean reoccupyPlaces;
	private boolean noPayOccupiedTower;
	private boolean vaticanSupportBonus;
	private boolean noMilitaryForTerritory;
	private boolean doubleResources;
	private boolean buildingCardDiscount;
	/**
	 * @return the reoccupyPlaces
	 */
	public boolean canReoccupyPlaces() {
		return reoccupyPlaces;
	}
	/**
	 * @param reoccupyPlaces the reoccupyPlaces to set
	 */
	public void setReoccupyPlaces(boolean reoccupyPlaces) {
		this.reoccupyPlaces = reoccupyPlaces;
	}
	
	/**
	 * @return the noPayOccupiedTower
	 */
	public boolean notPaysOccupiedTower() {
		return noPayOccupiedTower;
	}
	/**
	 * @param noPayOccupiedTower the noPayOccupiedTower to set
	 */
	public void setNoPayOccupiedTower(boolean noPayOccupiedTower) {
		this.noPayOccupiedTower = noPayOccupiedTower;
	}
	/**
	 * @return the vaticanSupportBonus
	 */
	public boolean hasVaticanSupportBonus() {
		return vaticanSupportBonus;
	}
	/**
	 * @param vaticanSupportBonus the vaticanSupportBonus to set
	 */
	public void setVaticanSupportBonus(boolean vaticanSupportBonus) {
		this.vaticanSupportBonus = vaticanSupportBonus;
	}
	/**
	 * @return the noMilitaryForTerritory
	 */
	public boolean hasNoMilitaryForTerritory() {
		return noMilitaryForTerritory;
	}
	/**
	 * @param noMilitaryForTerritory the noMilitaryForTerritory to set
	 */
	public void setNoMilitaryForTerritory(boolean noMilitaryForTerritory) {
		this.noMilitaryForTerritory = noMilitaryForTerritory;
	}
	/**
	 * @return the doubleResources
	 */
	public boolean acquiresDoubleResources() {
		return doubleResources;
	}
	/**
	 * @param doubleResources the doubleResources to set
	 */
	public void setDoubleResources(boolean doubleResources) {
		this.doubleResources = doubleResources;
	}
	/**
	 * @return the buildingCardDiscount
	 */
	public boolean hasBuildingCardDiscount() {
		return buildingCardDiscount;
	}
	/**
	 * @param buildingCardDiscount the buildingCardDiscount to set
	 */
	public void setBuildingCardDiscount(boolean buildingCardDiscount) {
		this.buildingCardDiscount = buildingCardDiscount;
	}
	
	public AdvancedModifier()
	{
		this.reoccupyPlaces=false;
		this.buildingCardDiscount=false;
		this.doubleResources=false;
		this.noMilitaryForTerritory=false;
		this.vaticanSupportBonus=false;
		this.noPayOccupiedTower=false;
	}
	
}
