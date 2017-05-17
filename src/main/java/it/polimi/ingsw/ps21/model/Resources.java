package it.polimi.ingsw.ps21.model;

public class Resources {
	private int wood;
	private int servants;
	private int stone;
	private int coins;
	
	public Resources(int wood, int servants, int stone, int coins){
		this.wood = wood;
		this.coins = coins;
		this.servants = servants;
		this.stone = stone;
	}
	public int getWood() {
		return wood;
	}
	public void setWood(int wood) {
		this.wood = wood;
	}
	public int getServants() {
		return servants;
	}
	public void setServants(int servants) {
		this.servants = servants;
	}
	public int getStone() {
		return stone;
	}
	public void setStone(int stone) {
		this.stone = stone;
	}
	public int getCoins() {
		return coins;
	}
	public void setCoins(int coins) {
		this.coins = coins;
	}
}
