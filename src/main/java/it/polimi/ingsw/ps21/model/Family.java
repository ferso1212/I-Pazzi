package it.polimi.ingsw.ps21.model;

/**Used to store the set of the Family Members of a Player
 * Exposes methods to set, increase and get the value of each family member.
 * @author fabri
 *
 */
public class Family {
	private FamilyMember orange;
	private FamilyMember white;
	private FamilyMember neutral;
	private FamilyMember black;
	protected int servantsForOne;
	
	/**
	 * The members are stored in an array of 4 element, each one of the FamilyMember type.
	 * The constructor initializes the array with 4 Family Members, each one with his color.
	 */
	public Family()
	{
	
		white= new FamilyMember(MembersColor.WHITE);
		orange= new FamilyMember(MembersColor.ORANGE);
		black= new FamilyMember(MembersColor.BLACK);
		neutral= new FamilyMember(MembersColor.NEUTRAL);
	}
	
	public FamilyMember getMember(MembersColor color) throws IllegalArgumentException
	{
		switch(color)
		{
		case WHITE: return this.white;
		case ORANGE: return this.orange;
		case BLACK: return this.black;
		case NEUTRAL: return this.neutral;
		default: throw new IllegalArgumentException();
		}
	}
	
	
	/** Resets all the Family members' values to 0.
	 * 
	 */
	public void roundReset()
	{
		this.white.setValue(0);
		this.orange.setValue(0);
		this.black.setValue(0);
		this.neutral.setValue(0);
	
	}
}
