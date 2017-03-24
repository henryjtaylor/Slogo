package backend;

/**
 * Wrapper class for the variables -- contains the variable name as well as it's value. VariableManager
 * manages all the variables.
 * @author Henry and Yanbo
 *
 */
public class Variable implements java.io.Serializable {

	
	private static final long serialVersionUID = 7189003635086889178L;
	private String myName;
	private Double myValue;

	/**
	 * myName is the variable name. myValue is the variable value. 
	 * @param variable - name of the variable in String
	 * @param value - value of the variable
	 */
	public Variable(String variable, Double value) {
		myName = variable;
		myValue = value;
	}

	/**
	 * @return the string of variable name
	 */
	public String getVariableName() {
		return myName;
	}

	/**
	 * @return the Double of the variable value
	 */
	public Double getValue() {
		return myValue;
	}

	/**
	 * Set the value for the variable
	 * @param value - value for variable
	 */
	public void setValue(Double value) {
		myValue = value;
	}
}
