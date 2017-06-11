package it.polimi.ingsw.ps21.view;

import it.polimi.ingsw.ps21.model.actions.WorkType;

public class WorkActionData extends ActionData{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4594695870246571226L;
	private WorkType spaceType;
	private boolean multipleSpace;
	
	public WorkActionData(WorkType spaceType, boolean multipleSpace) {
		super();
		this.spaceType = spaceType;
		this.multipleSpace = multipleSpace;
	}

	public WorkType getSpaceType() {
		return spaceType;
	}


	public boolean isMultipleSpace() {
		return multipleSpace;
	}


}
