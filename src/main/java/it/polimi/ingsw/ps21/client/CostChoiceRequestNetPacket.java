package it.polimi.ingsw.ps21.client;

import java.io.Serializable;
import java.util.ArrayList;

import it.polimi.ingsw.ps21.model.properties.ImmProperties;

public class CostChoiceRequestNetPacket extends NetPacket implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7848968607379477412L;
	ArrayList<ImmProperties> costChoices;

	public CostChoiceRequestNetPacket(int messNum, ArrayList<ImmProperties> costChoices) {
		super(messNum);
		this.costChoices = costChoices;
		this.type=PacketType.COST_CHOICE;
	}

	public ArrayList<ImmProperties> getCostChoices() {
		return costChoices;
	}

	public CostChoiceRequestNetPacket(int messNum, ImmProperties[] costChoices) {
		super(messNum);
		this.costChoices = new ArrayList<>();
		for (ImmProperties cost: costChoices)
		{
			this.costChoices.add(cost);
		}
		this.type=PacketType.COST_CHOICE;
	}

}
