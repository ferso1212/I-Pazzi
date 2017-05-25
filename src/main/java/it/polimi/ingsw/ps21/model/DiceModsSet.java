package it.polimi.ingsw.ps21.model;

import java.util.EnumMap;

public class DiceModsSet extends Modifier{
	private EnumMap<DevelopmentCardType, DiceMod> diceMods;
	
	public DiceModsSet()
	{
		this.diceMods=new EnumMap<DevelopmentCardType, DiceMod>(DevelopmentCardType.class);
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
