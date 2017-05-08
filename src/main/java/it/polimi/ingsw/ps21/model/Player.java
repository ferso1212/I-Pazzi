package it.polimi.ingsw.ps21.model;
import java.net.Socket;
import java.util.*;

/**Used to store the status of each player.
 * It stores the following datas of a player:
 * <li> Name
 * <li>Cards (Territory Cards, Building Cards, Character Cards and Venture Cards)
 * <li> Number of council privileges acquired during this Player's round
 * <li>Socket
 * <li>Properties: resources (wood, coins, servants, stones) and points (military points, victory points and faith points).
 * <li>Modifiers of player's actions 
 * @author fabri
 *
 */
public class Player {
	private String name;
	private LeaderCard[] leaderCards;
	//Since there is not a "Personal Board" class, player's cards are stored here
	private ArrayList<TerritoryCard> greenCards;
	private ArrayList<BuildingCard> yellowCards;
	private ArrayList<CharacterCard> blueCards;
	private ArrayList<VentureCard> purpleCards;
	private int roundCouncilBonus; //number of Council Privileges acquired during this Player's round
	private Socket socket;
	private Properties properties;
	private CurrentModifier curModifier; 
	
	/**Returns an object that contains the values of the resources (stone, wood, servants and coins) and points (military points, faith points and victory points) of the player. 
	 * 
	 * @return object containing the player's properties.
	 */
	public Properties getProperties()
	{
		return this.properties;
	}
	
	public Socket getSocket()
	{
		return this.socket;
	}
	
	/**Returns the number of the building (yellow) cards that the player has acquired during the game.
	 * 
	 * @return number of building cards on the player's personal board.
	 */
	public int countYellowCards()
	{
		return yellowCards.size();
	}
	
	/**Returns the number of the territory (green) cards that the player has acquired during the game.
	 * 
	 * @return number of territory cards on the player's personal board.
	 */
	public int countGreenCards()
	{
		return greenCards.size();
	}
	
	/**Returns the number of the character (blue) cards that the player has acquired during the game.
	 * 
	 * @return number of character cards on the player's personal board.
	 */
	public int countBlueCards()
	{
		return blueCards.size();
	}
	
	/**Returns the number of the venture (purple) cards that the player has acquired during the game.
	 * 
	 * @return number of venture cards on the player's personal board.
	 */
	public int countPurpleCards()
	{
		return purpleCards.size();
	}
}
