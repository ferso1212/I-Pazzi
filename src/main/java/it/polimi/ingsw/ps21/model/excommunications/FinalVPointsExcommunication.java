package it.polimi.ingsw.ps21.model.excommunications;

import it.polimi.ingsw.ps21.model.player.Player;

/**Used to store excommunications that at the end of the game reduce the player's victory points according to other properties.
 * @author fabri
 *
 */
public class FinalVPointsExcommunication extends Excommunication{
	private int victoryPointsReductionDivisor;
	private int militaryDivisorVPointsReduction;
	private int vPointsReductionBuildingWoodDivisor;
	private int vPointsReductionBuildingStoneDivisor;
	private int vPointsReductionResDivisor;

	/**Activates the excommunication: its effects are stored in the player's Final Excommunications modifier.
	 */
	@Override
	public void activate(Player player) {
		if(victoryPointsReductionDivisor!=0) player.getModifiers().getFinalMods().setVictoryPointsReductionDivisor(this.victoryPointsReductionDivisor);
		if(militaryDivisorVPointsReduction!=0) player.getModifiers().getFinalMods().setMilitaryDivisorVPointsReduction(this.militaryDivisorVPointsReduction);
		if(vPointsReductionBuildingWoodDivisor!=0) player.getModifiers().getFinalMods().setvPointsReductionBuildingWoodDivisor(this.vPointsReductionBuildingWoodDivisor);
		if(vPointsReductionBuildingStoneDivisor!=0) player.getModifiers().getFinalMods().setvPointsReductionBuildingStoneDivisor(this.vPointsReductionBuildingStoneDivisor);
		if(vPointsReductionResDivisor!=0) player.getModifiers().getFinalMods().setvPointsReductionResDivisor(this.vPointsReductionResDivisor);
		
	}
	
	
	/**Constructor
	 * 
	 * @param victoryPointsReductionDivisor if this value is !=0, at the end of the game you lose  1 victory point for each VALUE victory points you have
	 * @param militaryDivisorVPointsReduction if this value is !=0, at the end of the game you lose  1 victory point for each VALUE military points you have
	 * @param vPointsReductionBuildingWoodDivisor if this value is !=0, at the end of the game you lose  1 victory point for each VALUE wood pieces in the costs of your territory cards.
	 * @param vPointsReductionBuildingStoneDivisor if this value is !=0, at the end of the game you lose  1 victory point for each VALUE stones in the costs of your territory cards.
	 * @param vPointsReductionResDivisor if this value is !=0, at the end of the game you lose  1 victory point for each VALUE resources you have
	 */
	public FinalVPointsExcommunication(int victoryPointsReductionDivisor, int militaryDivisorVPointsReduction,
			int vPointsReductionBuildingWoodDivisor, int vPointsReductionBuildingStoneDivisor,
			int vPointsReductionResDivisor) {
		super();
		this.victoryPointsReductionDivisor = victoryPointsReductionDivisor;
		this.militaryDivisorVPointsReduction = militaryDivisorVPointsReduction;
		this.vPointsReductionBuildingWoodDivisor = vPointsReductionBuildingWoodDivisor;
		this.vPointsReductionBuildingStoneDivisor = vPointsReductionBuildingStoneDivisor;
		this.vPointsReductionResDivisor = vPointsReductionResDivisor;
	}



	/**Returns a string that describes the excommunication
	 */
	@Override
	public String toString() {
		String output=new String();
		if(victoryPointsReductionDivisor!=0) output.concat("At the end of the game, before calculating the final points, you lose 1 victory point for each " + victoryPointsReductionDivisor + " victory points you have. \n");
		if(militaryDivisorVPointsReduction!=0) output.concat("At the end of the game, before calculating the final points, you lose 1 victory point for each " + militaryDivisorVPointsReduction + " military points you have. \n");
		if(vPointsReductionBuildingWoodDivisor!=0) output.concat("At the end of the game, before calculating the final points, you lose 1 victory point for each " + vPointsReductionBuildingWoodDivisor + " wood pieces in the costs of your territory cards. \n");
		if(vPointsReductionBuildingStoneDivisor!=0)	output.concat("At the end of the game, before calculating the final points, you lose 1 victory point for each " + vPointsReductionBuildingStoneDivisor + " stones in the costs of your territory cards. \n");
		if(vPointsReductionResDivisor!=0) output.concat("At the end of the game, before calculating the final points, you lose 1 victory point for each " + vPointsReductionResDivisor + " resources you have. \n");
		return output;
	}

}
