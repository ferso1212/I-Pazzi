package it.polimi.ingsw.ps21.model.properties;

/**Used to store datas of resources and points. 
 * The following datas are stored for each property: 
 * <li>the <b>id</b>, which is a string that identifies the property;
 * <li>the <b>value</b>, which is an integer value that describes the quantity of that property;
 * <li>the <b>modifier</b>, which is an integer value that is added to the quantity to pay each time the payValue(num) method is called.
 * @author fabri
 *
 */
public class Property implements Cloneable{
	private PropertiesId id;
	private int value;
	private int paymentModifier;
	private int additionModifier;
	
	/**Constructs a Property object with custom id and initial value. The modifier is automatically set to 0.
	 * 
	 * @param id the name (a string) of the property
	 * @param initialValue the initial value of the property
	 */
	public Property(PropertiesId id, int initialValue)
	{
		this.id=id;
		this.value=initialValue;
		this.paymentModifier=0;
		this.additionModifier=0;
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
	public PropertiesId getId() {
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
		int modifiedNum = num + this.additionModifier; //adds the modifier value to the value that should be added 
		this.value+=modifiedNum;
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
		int modifiedNum = num + this.paymentModifier; //adds the modifier value to the value that should be payed
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

	/**Sets the modifier that modifies the number that is subtracted from the property's value when the payValue(num) method is called.
	 * When the payValue(num) method is called, (num + modifier) is subtracted from the property's value.
	 * @param modifier the value of the modifier to set
	 */
	public void setPaymentModifier(int modifier) {
		this.paymentModifier = modifier;
	}
	
	
	/**Sets the modifier that modifies the number that is added to the property's value when the addValue(num) method is called
	 * When the addValue(num) method is called, (num + modifier) is added to the property's value.
	 * @param increasingModifier the increasingModifier to set
	 */
	public void setAdditionModifier(int increasingModifier) {
		this.additionModifier = increasingModifier;
	}

	public Property clone()
	{
		try {
			return (Property)super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
	}
}
