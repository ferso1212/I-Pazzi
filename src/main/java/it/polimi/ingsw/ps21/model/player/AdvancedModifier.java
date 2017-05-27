package it.polimi.ingsw.ps21.model.player;

public class AdvancedModifier {
	private boolean reoccupyPlaces;
	private int fixedBlackValue;
	private int fixedOrangeValue;
	private int fixedWhiteValue;
	private int fixedNeutralValue;
	private int bonusBlackValue;
	private int bonusOrangeValue;
	private int bonusWhiteValue;
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
	 * @return the fixedBlackValue
	 */
	public int getFixedBlackValue() {
		return fixedBlackValue;
	}
	/**
	 * @param fixedBlackValue the fixedBlackValue to set
	 */
	public void setFixedBlackValue(int fixedBlackValue) {
		this.fixedBlackValue = fixedBlackValue;
	}
	/**
	 * @return the fixedOrangeValue
	 */
	public int getFixedOrangeValue() {
		return fixedOrangeValue;
	}
	/**
	 * @param fixedOrangeValue the fixedOrangeValue to set
	 */
	public void setFixedOrangeValue(int fixedOrangeValue) {
		this.fixedOrangeValue = fixedOrangeValue;
	}
	/**
	 * @return the fixedWhiteValue
	 */
	public int getFixedWhiteValue() {
		return fixedWhiteValue;
	}
	/**
	 * @param fixedWhiteValue the fixedWhiteValue to set
	 */
	public void setFixedWhiteValue(int fixedWhiteValue) {
		this.fixedWhiteValue = fixedWhiteValue;
	}
	/**
	 * @return the fixedNeutralValue
	 */
	public int getFixedNeutralValue() {
		return fixedNeutralValue;
	}
	/**
	 * @param fixedNeutralValue the fixedNeutralValue to set
	 */
	public void setFixedNeutralValue(int fixedNeutralValue) {
		this.fixedNeutralValue = fixedNeutralValue;
	}
	/**
	 * @return the bonusBlackValue
	 */
	public int getBonusBlackValue() {
		return bonusBlackValue;
	}
	/**
	 * @param bonusBlackValue the bonusBlackValue to set
	 */
	public void setBonusBlackValue(int bonusValue) {
		this.bonusBlackValue = bonusValue;
	}
	/**
	 * @return the bonusOrangeValue
	 */
	public int getBonusOrangeValue() {
		return bonusOrangeValue;
	}
	/**
	 * @param bonusOrangeValue the bonusOrangeValue to set
	 */
	public void setBonusOrangeValue(int bonusValue) {
		this.bonusOrangeValue = bonusValue;
	}
	/**
	 * @return the bonusWhiteValue
	 */
	public int getBonusWhiteValue() {
		return bonusWhiteValue;
	}
	/**
	 * @param bonusWhiteValue the bonusWhiteValue to set
	 */
	public void setBonusWhiteValue(int bonusValue) {
		this.bonusWhiteValue = bonusValue;
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
	
	
	
}
