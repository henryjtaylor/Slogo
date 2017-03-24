package backend;

import java.util.Collection;
import backend.API.ModelAPI;
import turtles.TurtleManager;
import commands.Command;

/**
 * This class serves as the bridge between the backend and the controller, so
 * none of the other backend classes have to directly communicate with the
 * controller. This class extends from the ModelAPI, which is the 'contract'
 * between the controller and the model. The main function of this class is to
 * tell the backend to handle input and to tell the backend to process commands.
 * I think this shows good design because it eliminates dependencies between the
 * parser and the commandHandler, which gives the two classes very specific
 * jobs.
 * 
 * @author Henry and Yanbo
 *
 */
public class Model implements ModelAPI {

	private VariableManager myVariables;
	private UserMethodManager myUserMethods;
	private TurtleManager myTurtles;
	private Parser myParser;
	private CommandHandler myCommandHandler;

	/**
	 * @param syntax
	 *            - relevant reg-ex expression files for pattern parsing and
	 *            reflection
	 * @param variables
	 *            - variable manager
	 * @param methods
	 *            - userMethod manager
	 * @param turtles
	 *            - turtleManager to keep track of all turtles
	 */
	public Model(String[] syntax, VariableManager variables, UserMethodManager methods, TurtleManager turtles) {
		myVariables = variables;
		myUserMethods = methods;
		myTurtles = turtles;
		myParser = new Parser(syntax, myVariables, myUserMethods);
		myCommandHandler = new CommandHandler(myTurtles, myVariables);
	}

	/**
	 * Uses the parser to return a list of Commands, which is then passed to the
	 * CommandHandler Input - string version of what the user inputed into the
	 * frontend
	 */
	@Override
	public void handleInput(String input) {
		Collection<Command> commands = myParser.parse(input);
		for (Command c : commands) {
			c.resetCommand();
		}
		myCommandHandler.addCommands(commands);
	}

	/**
	 * The call from the controller that tells the backend to process all of the
	 * commands it has at that point returns the Double value from the last
	 * command executed
	 */
	@Override
	public Double getNextPos() {
		Double current = myCommandHandler.executeCommands();
		while (current != null) {
			current = myCommandHandler.executeCommands();
		}
		return current;
	}

	/**
	 * Allows the user to update a specific variable from the UI as opposed as
	 * through the console
	 */
	@Override
	public void updateVariable(String var, String value) {
		myVariables.addVariable(new Variable(var, Double.parseDouble(value)));
	}
}
