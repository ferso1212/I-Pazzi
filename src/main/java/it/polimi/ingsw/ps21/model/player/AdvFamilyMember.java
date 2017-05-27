package it.polimi.ingsw.ps21.model.player;

public class AdvFamilyMember extends FamilyMember {
	private boolean fixed;

	public AdvFamilyMember(MembersColor color, String id) {
		super(color, id);
		this.fixed=false;
	}

	public void setValue(int value)
	{
		if(this.fixed==false) this.value=value;
	}



	/**
	 * @param fixed the fixed to set
	 */
	public void setFixed(boolean fixed) {
		this.fixed = fixed;
	}
	
	
}
