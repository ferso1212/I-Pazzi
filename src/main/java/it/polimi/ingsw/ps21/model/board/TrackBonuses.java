package it.polimi.ingsw.ps21.model.board;

public class TrackBonuses {

	private int[] faithTrack;
	private int[] militaryBonuses;

	public TrackBonuses(int[] faithTrack, int[] militaryBonuses) {
		super();
		this.faithTrack = faithTrack;
		this.militaryBonuses = militaryBonuses;
	}

	public int getFaithBonus(int position) throws IllegalArgumentException {
		if (position < this.faithTrack.length) {
			return this.faithTrack[position];
		} else throw new IllegalArgumentException();
	}

	public int getFaithTrackSize()
	{
		return this.faithTrack.length;
	}
	
	public int[] getMilitaryBonuses() {
		return militaryBonuses;
	}

	

}
