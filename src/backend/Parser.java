package backend;

/***
 * This class is the class that's responsible for parsing the data input as given by a string.
 * This class takes advantage of recursion and reflection to effectively parse the input. For our
 * algorithm, we have created command classes for every kind of input as to make this the most efficient
 * parser as possible. So, in addition to the typical commands, there is also a command for Double,
 * Variable, and UserMethod. The only thing there are no commands for are comments, listEnd, GroupEnd,
 * and GroupStart. This class is only called by Model. I think this is good design because it delegates
 * there to be only one place where parsing the commands takes place. In addition, parser matters about 
 * nothing else other than taking the input and trying to make them into commands
 * @author Henry
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import commands.Command;
public class Parser {

	private PatternParse myPatterns;
	private CommandFactory myFactory;
	private VariableManager myVariables;
	private UserMethodManager myUserMethods;

	/**
	 * @param syntax - relevant reg-ex expression files for pattern parsing and reflection
	 * @param variables - variable manager 
	 * @param methods - userMethod manager
	 */
	public Parser(String[] syntax, VariableManager variables, UserMethodManager methods) {
		myPatterns = new PatternParse();
		for (String each : syntax) {
			myPatterns.addPattern(each);
		}
		myVariables = variables;
		myUserMethods = methods;
		myFactory = new CommandFactory();
	}

	/**
	 * Start of the parsing -- returns the full list of Commands ( just a list of outermost commands --
	 * if a command is nestled (like fd fd 50 ), myCommands would be a list of only 1 command. 
	 * @param input -- a long string of commands as inputed by the user
	 * @return a list of Commands for CommandHandler to execute
	 */
	public List<Command> parse(String input) {
		ArrayList<Command> myCommands = new ArrayList<Command>();
		Scanner s = new Scanner(input);
		while (s.hasNext()) {
			Command currentCommand = recurseParse(s);
			myCommands.add(currentCommand);
		}
		return myCommands;
	}

	/**
	 * Using reflection to create the appropriate command  -- commandFactory is responsible reflection.
	 * @param current string from user input 
	 * @return returns null if not a proper command, or the correct command
	 */
	private Command reflect(String current) {
		Command currentCommand = myFactory.reflectCommand(current, myPatterns.getSymbol(current), myVariables,
				myUserMethods);
		return currentCommand;
	}

	/**
	 * Builds the correct number of arguments nestled within the command as given by the command,
	 * or as given by grouping ( which indicates an infinite number of arguments )
	 * @param command - the current command
	 * @param s - scanner of input
	 * @param numberOfTimes - the number of arguments the command needs before completing
	 */
	private void runArguments(Command command, Scanner s, int numberOfTimes) {
		while (command.getArguments().size() < numberOfTimes || numberOfTimes < 0) {
			Command toBeAdded = recurseParse(s);
			if (toBeAdded == null) {
				break;
			}
			command.add(toBeAdded);
		}
	}

	/**
	 * Pass in a scanner, pop off a single string, iterate through the scanner recursively
	 * until the command is completed
	 * 
	 * @param s - scanner of the input
	 * @return - returns an object depending on the the command/value
	 */
	private Command recurseParse(Scanner s) {
		Command currentCommand;
		if (s.hasNext()) {
			String current = s.next();
			currentCommand = reflect(current);
			if (currentCommand != null) {
				runArguments(currentCommand, s, currentCommand.getNumOfExpressions());
				currentCommand.performBeforeExecution();
				return currentCommand;
			} else {
				return getDataObject(current, s);
			}
		} else {
			throw new ParserException(String.format("Improper input"), new Exception());
		}
	}
	
	/**
	 * Responsible for dealing with characters that aren't commands, such as ListEnd, GroupEnd, Comments,
	 * and GroupStart. If it it doesn't match with any of those, illegal input and throws error
	 * @param current - current string input
	 * @param s - scanner of input
	 * @return - returns null if the list needs to end, or a command
	 */

	private Command getDataObject(String current, Scanner s) {
		if (myPatterns.getSymbol(current).equals("ListEnd") ||
			myPatterns.getSymbol(current).equals("GroupEnd")) {
			return null;
		} else if (myPatterns.getSymbol(current).equals("Comment")) {
			s.nextLine();
			return recurseParse(s);
		} else if (myPatterns.getSymbol(current).equals("GroupStart")) {
			return getInfiniteCommand(s);
		} else {
			throw new ParserException(String.format("NOT A VALID TYPE %s", current), new Exception());
		}
	}
	
	/**
	 * Deals with commands that are grouped to take in infinite arguments ( but only for the first command
	 * in the group ). Checks to see if the command has the correct number of arguments ( must be an amount
	 * that is a multiple of the original number of arguments ) and if the command can even have multiple commands. 
	 * @param s - scanner of input
	 * @return - returns a command that has an infinite number of arguments. 
	 */
	private Command getInfiniteCommand(Scanner s) {
		String current = s.next();
		Command infiniteCommand = reflect(current);
		if (infiniteCommand != null && infiniteCommand.getNumOfExpressions() != 0) {
			runArguments(infiniteCommand, s, -1);
			if (infiniteCommand.getArguments().size() % infiniteCommand.getNumOfExpressions() != 0) {
				throw new ParserException(String.format("WRONG NUMBER OF ARGUMENTS FOR %s", current), new Exception());
			}
			infiniteCommand.performBeforeExecution();
			return infiniteCommand;
		}
		throw new ParserException(String.format("DOESN'T TAKE IN INFINITE ARGUMENTS %s", current), new Exception());
	}

}
