package it.polimi.ingsw.ps21.model;

import java.util.Queue;

public class MultipleSpace {

	private Queue<Player> occupants;
	private int requirement;
	private ImmProperties instantBonus;
	private MultipleSpaceType type;

	public MultipleSpace(Queue<Player> occupants, int requirement, ImmProperties instantBonus, MultipleSpaceType type) {
		super();
		this.occupants = occupants;
		this.requirement = requirement;
		this.instantBonus = instantBonus;
		this.type = type;
	}

	public Queue<Player> getOccupants() {
		return occupants;
	}

	public int getRequirement() {
		return requirement;
	}

	public ImmProperties getInstantBonus() {
		return instantBonus;
	}

	public MultipleSpaceType getType() {
		return type;
	}

	public boolean occupy(Player player) {
		switch (type) {
		case COUNCIL:
			boolean occupied = occupants.add(player);
			return occupied;

		case HARVEST:
		case PRODUCTION:
			if (occupants.contains(player)) {
				return false;
			} else {
				return occupants.add(player);
			}

		default:
			return false;
		}
	}

}
