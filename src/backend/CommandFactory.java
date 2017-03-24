package backend;

/**
 * The purpose of this class is to reflect a given string to a command class. I think this shows good
 * design because the only purpose of this class is to reflect and return a command, which isolates its function,
 * creating a small but very important class.
 * @author Henry and Yanbo
 */
import commands.Command;

public class CommandFactory {
	
	private static final String COMMAND_PACKAGE = "commands.";
	
	/**
	 * Uses reflection to find the corresponding command
	 * @param input - the name of the command before myParser converts it -- used to give the command
	 * 					the actual name of the instruction
	 * @param s - the actual command name
	 * @param variables - variable manager
	 * @param methods - method manager
	 * @return returns a new command or null if no match
	 */
	public Command reflectCommand(String input, String s, VariableManager variables, UserMethodManager methods) {
		try {
			Class<?> clazz = Class.forName( COMMAND_PACKAGE + s + "Command" );
			return (Command) clazz.getDeclaredConstructor(java.lang.String.class, backend.VariableManager.class, backend.UserMethodManager.class).newInstance(input, variables, methods);
		} catch (Exception e) {
			return null;
		}
	}
	
	

}
