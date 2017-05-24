package it.polimi.ingsw.ps21.model;

/**Used to store datas of resources and points. 
 * The following datas are stored for each property: 
 * <li>the <b>id</b>, which is a string that identifies the property;
 * <li>the <b>value</b>, which is an integer value that describes the quantity of that property;
 * <li>the <b>modifier</b>, which is an integer value that is added to the quantity to pay each time the payValue(num) method is called.
 * @author fabri
 *
 */
public class Property {
	private String id;
	private int value;
	private int modifier;
	
	/**Constructs a Property object with custom id and initial value. The modifier is automatically set to 0.
	 * 
	 * @param id the name (a string) of the property
	 * @param initialValue the initial value of the property
	 */
	public Property(String id, int initialValue)
	{
		this.id=new String(id);
		this.value=initialValue;
		this.modifier=0;
	}
	
	/**Returns the value of the property.
	 * @return the value of the property.
	 */
	public int getValue() {
		return value;
	}
	
	/**
	 * @param value the value to set
	 */
	public void setValue(int value) {
		this.value = value;
	}
	
	/**Returns the identifier of the property.
	 * @return the id of the property.
	 */
	public String getId() {
		return id;
	}
	
	/**Increases the value stored in the object by num units.
	 * This method can't be used to increase the value of the property.
	 * @param num
	 * @return true if the operation succeeded, false if the 'num' parameter is negative
	 */
	public boolean addValue(int num)
	{
		if(num<0) return false; //this method can't be used to subtract values
		this.value+=num;
		return true;
	}
	
	/**Reduces the value stored in the object by num units
	 * This method can't be used to increase the value of the property.
	 * @param num
	 * @return true if the operation succeeded, false if the 'num' parameter is negative
	 */
	public boolean payValue(int num)
	{
		if(num<0) return false; //this method can't be used to add values
		int modifiedNum = num + this.modifier; //adds the modifier value to the value that should be payed
		if(this.value<modifiedNum) return false; 
		this.value-=modifiedNum;
		return true;
	}
	
	/**Returns true if the value stored in the object in greater or equal than the number passed as argument
	 * 
	 * @param num to compare
	 * @return
	 */
	public boolean greaterOrEqual(int num)
	{
		if(this.value>=num) return true;
		else return false;
	}

	/**Sets the modifier
	 * @param modifier the value of the modifier to set
	 */
	public void setModifier(int modifier) {
		this.modifier = modifier;
	}
	
	/**Used to increase the value of the modifier  by num units.
	 * If num<0, the value of the modifier is reduced by |num| units.
	 * @param num the number to add to the modifier value
	 */
	public void increaseModifier(int num) {
		this.modifier += num;
	}
}
