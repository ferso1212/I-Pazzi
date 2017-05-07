package it.polimi.ingsw.ps21.model;

/**This class is used to store modifiers that may have effects on each action of the player. 
 * Modifiers are induced by excommunications and by permanent effects of development cards. 
 * It is possible to store the following kinds of modifiers:
 * <li> Properties modifiers: modifiers that increase/reduce the amount of resources or points acquired each time the player acquires points or resources.
 * <li> Harvest modifiers: modifiers that increase/reduce the harvest action value
 * <li> Production modifiers: modifiers that increase/reduce the production action value
 * <li> Dice modifiers: modifiers that increase or reduce the value of a dice
 * <li> Dice modifier according to card color: ???
 * @author fabri
 *
 */
public class CurrentModifier {
	
private ImmProperties propModifier;
private int harvestModifier;
private int productionModifier;
private int whiteDiceModifier;
private int orangeDiceModifier;
private int blackDiceModifier;
private int[] cardDiceModifier;

/**Returns the value of the production modifier.
 *Production modifiers are modifiers that increase/reduce the production action value 
 * @return value of the production modifier.
 */
public int getProdMod()
{
	return this.productionModifier;
}

/**Returns the value of the harvest modifier.
 *Harvest modifiers are modifiers that increase/reduce the harvest action value 
 * @return value of the harvest modifier.
 */
public int getHarvMod()
{
	return this.harvestModifier;
}

/**Returns an object containing the properties modifiers.
 *Property modifiers are modifiers that increase/reduce the amount of resources or points acquired each time the player acquires points or resources. 
 * @return An object that contains property modifiers.
 */
public ImmProperties getPropMod()
{
	return this.propModifier;
}

/**Returns the value of the white dice modifier.
*Each time the player rolls the white dice, this modifier changes the value of the white dice.  
* @return value of the white dice modifier.
*/
public int getWhiteDiceMod()
{
	return this.whiteDiceModifier;
}

/**Returns the value of the black dice modifier.
*Each time the player rolls the black dice, this modifier changes the value of the black dice. 
* @return value of the black dice modifier.
*/
public int getBlackDiceMod()
{
	return this.blackDiceModifier;
}

/**Returns the value of the orange dice modifier.
*Each time the player rolls the orange dice, this modifier changes the value of the orange dice. 
* @return value of the orange dice modifier.
*/
public int orangeDiceMod()
{
	return this.orangeDiceModifier;
}

public int getGreenModifier()
{
	return this.cardDiceModifier[0];
}

public int getYellowModifier()
{
	return this.cardDiceModifier[1];
}

public int getPurpleModifier()
{
	return this.cardDiceModifier[2];
}

public int getBlueModifier()
{
	return this.cardDiceModifier[3];
}
}
