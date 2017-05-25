package it.polimi.ingsw.ps21.model;

/**This class is used to store excommunication modifiers that change how the final score is calculated. 
 * There are 8 different final modifiers resulting from excommunications:
 * <li>noGreenPoints: the player does not acquire the victory points that he should have had due to the number of territory cards he owns
 * <li>noYellowPoints: the player does not acquire the victory points that he should have had due to the number of building cards he owns
 * <li>noBluePoints: the player does not acquire the victory points that he should have had due to the number of character cards he owns
 * <li>victoryPointsReductionDivisor: the player loses floor((Player's victory points)/Divisor) victory points
 * <li>militaryDivisorVPointsReduction: the player loses floor((Player's military points)/Divisor) victory points
 * <li>vPointsReductionBuildingWoodDivisor: the player loses floor((sum of wood requirements on player's building cards)/Divisor) victory points
 * <li>vPointsReductionBuildingStoneDivisor: the player loses floor((sum of stone requirements on player's building cards)/Divisor) victory points
 * <li>vPointsReductionResDivisor: the player loses floor((sum of resources in player's supply)/Divisor) victory points
 * @author fabri
 *
 */
public class FinalExcomModifier extends Modifier{
	private boolean noGreenPoints;
	private boolean noBluePoints;
	private boolean noYellowPoints;
	private int victoryPointsReductionDivisor;
	private int militaryDivisorVPointsReduction;
	private int vPointsReductionBuildingWoodDivisor;
	private int vPointsReductionBuildingStoneDivisor;
	private int vPointsReductionResDivisor;
	/**
	 * @return the noGreenPoints
	 */
	public boolean isNoGreenPoints() {
		return noGreenPoints;
	}
	/**
	 * @param noGreenPoints the noGreenPoints to set
	 */
	public void setNoGreenPoints(boolean noGreenPoints) {
		this.noGreenPoints = noGreenPoints;
	}
	/**
	 * @return the noBluePoints
	 */
	public boolean isNoBluePoints() {
		return noBluePoints;
	}
	/**
	 * @param noBluePoints the noBluePoints to set
	 */
	public void setNoBluePoints(boolean noBluePoints) {
		this.noBluePoints = noBluePoints;
	}
	/**
	 * @return the noYellowPoints
	 */
	public boolean isNoYellowPoints() {
		return noYellowPoints;
	}
	/**
	 * @param noYellowPoints the noYellowPoints to set
	 */
	public void setNoYellowPoints(boolean noYellowPoints) {
		this.noYellowPoints = noYellowPoints;
	}
	/**
	 * @return the victoryPointsReductionDivisor
	 */
	public int getVictoryPointsReductionDivisor() {
		return victoryPointsReductionDivisor;
	}
	/**
	 * @param victoryPointsReductionDivisor the victoryPointsReductionDivisor to set
	 */
	public void setVictoryPointsReductionDivisor(int victoryPointsReductionDivisor) {
		this.victoryPointsReductionDivisor = victoryPointsReductionDivisor;
	}
	/**
	 * @return the militaryDivisorVPointsReduction
	 */
	public int getMilitaryDivisorVPointsReduction() {
		return militaryDivisorVPointsReduction;
	}
	/**
	 * @param militaryDivisorVPointsReduction the militaryDivisorVPointsReduction to set
	 */
	public void setMilitaryDivisorVPointsReduction(int militaryDivisorVPointsReduction) {
		this.militaryDivisorVPointsReduction = militaryDivisorVPointsReduction;
	}
	/**
	 * @return the vPointsReductionBuildingWoodDivisor
	 */
	public int getvPointsReductionBuildingWoodDivisor() {
		return vPointsReductionBuildingWoodDivisor;
	}
	/**
	 * @param vPointsReductionBuildingWoodDivisor the vPointsReductionBuildingWoodDivisor to set
	 */
	public void setvPointsReductionBuildingWoodDivisor(int vPointsReductionBuildingWoodDivisor) {
		this.vPointsReductionBuildingWoodDivisor = vPointsReductionBuildingWoodDivisor;
	}
	/**
	 * @return the vPointsReductionBuildingStoneDivisor
	 */
	public int getvPointsReductionBuildingStoneDivisor() {
		return vPointsReductionBuildingStoneDivisor;
	}
	/**
	 * @param vPointsReductionBuildingStoneDivisor the vPointsReductionBuildingStoneDivisor to set
	 */
	public void setvPointsReductionBuildingStoneDivisor(int vPointsReductionBuildingStoneDivisor) {
		this.vPointsReductionBuildingStoneDivisor = vPointsReductionBuildingStoneDivisor;
	}
	/**
	 * @return the vPointsReductionResDivisor
	 */
	public int getvPointsReductionResDivisor() {
		return vPointsReductionResDivisor;
	}
	/**
	 * @param vPointsReductionResDivisor the vPointsReductionResDivisor to set
	 */
	public void setvPointsReductionResDivisor(int vPointsReductionResDivisor) {
		this.vPointsReductionResDivisor = vPointsReductionResDivisor;
	}
	
	
}

