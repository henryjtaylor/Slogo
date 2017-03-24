package backend.API;

/**
 * The API for Model
 * @author Henry and Yanbo
 *
 */
public interface ModelAPI {

	/**
	 * Take in user Input and pass to Parser
	 * 
	 * @param input
	 *            - the String version of the user input
	 */
	public void handleInput(String input);

	/**
	 * Go to CommandHandler, and handle next command.
	 * 
	 * @return
	 */
	public Double getNextPos();

	/**
	 * Update the variable in VariableManager
	 * 
	 * @param var
	 *            - variable name
	 * @param value
	 *            - new value of variable
	 */
	public void updateVariable(String var, String value);

}
