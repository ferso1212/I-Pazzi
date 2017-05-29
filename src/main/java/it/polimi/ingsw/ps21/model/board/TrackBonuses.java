package it.polimi.ingsw.ps21.model.board;

public class TrackBonuses {

	private int[] faithTrack;
	private int militaryBonus1;
	private int militaryBonus2;

	public TrackBonuses(int[] faithTrack, int militaryBonus1, int militaryBonus2) {
		super();
		this.faithTrack = faithTrack;
		this.militaryBonus1 = militaryBonus1;
		this.militaryBonus2 = militaryBonus2;
	}

	public int getFaithBonus(int position) throws IllegalArgumentException {
		if (position < this.faithTrack.length) {
			return this.faithTrack[position];
		} else throw new IllegalArgumentException();
	}

	public int getMilitaryBonus1() {
		return militaryBonus1;
	}

	public int getMilitaryBonus2() {
		return militaryBonus2;
	}

}
