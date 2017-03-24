package commands;

import java.util.List;
import turtles.TurtleManagerCommandAPI;
import backend.VariableManager;

/**
 * The API of Command
 * @author Yanbo Fang
 * @author Henry Taylor
 */
public interface Command extends java.io.Serializable {
	/**
	 * Add all the command in args into myArguments
	 * @param args
	 */
	void add(Command... args);
	/**
	 * Get the list of command in myArguments
	 * @return a list of commands
	 */
	List<Command> getArguments();
	/**
	 * Execute the command, returns a double value
	 * @param turtles
	 * @param vars
	 * @param turtleID
	 * @return Double value calculated in getValue
	 */
	Double executeCommand(TurtleManagerCommandAPI turtles, VariableManager vars, Double turtleID);
	/**
	 * Reset the command to its unfinished status
	 */
	void resetCommand();
	/**
	 * Get the number of expressions (parameters) for a command
	 * @return Integer value of the number of parameters needed for a command
	 */
	Integer getNumOfExpressions();
	/**
	 * Get the name of the instruction
	 * @return String
	 */
	String getInstruction();
	/**
	 * Check if a command has been executed
	 * @return a boolean
	 */
	boolean isFinished();
	/**
	 * Check if needs to run all the turtles
	 * @return a boolean
	 */
	boolean isRunAllTurtles();
	/**
	 * Perform the steps in the implementation before executing the command
	 */
	void performBeforeExecution();
}
