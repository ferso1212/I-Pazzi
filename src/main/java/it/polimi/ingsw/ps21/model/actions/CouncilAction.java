package it.polimi.ingsw.ps21.model.actions;

public class CouncilAction extends Action{
	
	private CouncilSpace space;
	private FamilyMember famMember;
	
	public CouncilAction(Match m, Player p, CouncilSpace space, FamilyMember famMember) {
		super(m, p);
		this.space = space;
		this.famMember = famMember;
	}

	@Override
	public boolean isLegal() {
		if (famMember.)
		return false;
	}

	@Override
	public boolean execute() {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	
	

}
