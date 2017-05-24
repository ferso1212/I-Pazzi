package it.polimi.ingsw.ps21.model;

/**Used to store datas of resources and points.
 * @author fabri
 *
 */
public class Property {
	private String id;
	private int value;
	
	/**Constructs a Property object
	 * 
	 * @param id the name (a string) of the property
	 * @param initialValue the initial value of the property
	 */
	public Property(String id, int initialValue)
	{
		this.id=new String(id);
		this.value=initialValue;
		
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
	public boolean subValue(int num)
	{
		if(num<0) return false; //this method can't be used to add values
		this.value-=num;
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
}
