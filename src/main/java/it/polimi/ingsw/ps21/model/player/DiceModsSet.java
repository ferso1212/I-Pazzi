package it.polimi.ingsw.ps21.model.player;

import java.util.EnumMap;

import it.polimi.ingsw.ps21.model.deck.DevelopmentCardType;

public class DiceModsSet{
	private EnumMap<DevelopmentCardType, DiceMod> diceMods;
	
	public DiceModsSet()
	{
		this.diceMods=new EnumMap<>(DevelopmentCardType.class);
		for(DevelopmentCardType cardType: DevelopmentCardType.values())
		{
			diceMods.put(cardType, new DiceMod(cardType));
		}
	}
	
	public DiceMod getDiceMod(DevelopmentCardType type)
	{
		return diceMods.get(type);
	}
}
