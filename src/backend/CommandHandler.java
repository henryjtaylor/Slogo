package backend;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import turtles.TurtleManager;
import commands.Command;

public class CommandHandler {

	private Queue<Command> myCommands;
	private Command currentCommand;
	private VariableManager myVariables;
	private TurtleManager myTurtles;

	public CommandHandler(TurtleManager turtle, VariableManager vars) {
		myTurtles = turtle;
		myCommands = new LinkedList<Command>();
		myVariables = vars;
	}

	public void addCommands(List<Command> lst) {
		for (Command c : lst) {
			myCommands.add(c);
		}
	}

	/**
	 * Execute a single command -- assures we return our values for each command
	 * 
	 * @return
	 */

	public Double executeCommands() {
		Double current = null;
		if (!myCommands.isEmpty()) {
			currentCommand = myCommands.peek();
			List<Double> activeTurtles = myTurtles.getActiveTurtleIDs();
			if (currentCommand.isFinished()) {
				myCommands.remove();
			} else if (currentCommand.isRunAllTurtles()) {
				for (Double k : activeTurtles) {
					current = currentCommand.executeCommand(myTurtles, myVariables, k);
				}
			} else {
				current = currentCommand.executeCommand(myTurtles, myVariables, activeTurtles.get(0));
			}
		}
		return current;
	}

}
