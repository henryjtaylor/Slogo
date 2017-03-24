package commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import turtles.Turtle;
import turtles.TurtleManagerCommandAPI;
import backend.ParserException;
import backend.UserMethodManager;
import backend.VariableManager;

/**
 * This class provides a skeletal implementation of the {@link Command}
 * interface.
 * @author Yanbo Fang
 * @author Henry Taylor
 */
public abstract class AbstractCommand implements Command {

	private static final long serialVersionUID = -7877106052459852818L;
	private Integer myNumOfExpressions;
	private String myInstruction;
	private ArrayList<Command> myArguments;
	private ArrayList<Object> myConvertedArguments;
	private boolean myFinished;
	private Turtle myTurtle;
	private Double myValue;
	private VariableManager myVariables;
	private UserMethodManager myUserMethods;
	private TurtleManagerCommandAPI myTurtleManager;
	private boolean myRunAllTurtles;

	/**
	 * Constructor for AbstractCommand, set the initial myNumOfExpressions to 0.
	 * @param Name
	 *            of the command
	 * @param VariableManager
	 *            - variables
	 * @param UserMethodManager
	 *            - methods
	 */
	public AbstractCommand(String instruction, VariableManager variables, UserMethodManager methods) {
		myArguments = new ArrayList<Command>();
		myNumOfExpressions = 0;
		myVariables = variables;
		myInstruction = instruction.toLowerCase();
		myUserMethods = methods;
		myFinished = false;
		myRunAllTurtles = false;
	}

	protected AbstractCommand(String instruction, VariableManager variables, UserMethodManager methods,
			int numOfExpressions) {
		this(instruction, variables, methods);
		myNumOfExpressions = numOfExpressions;
	}

	public Integer getNumOfExpressions() {
		return myNumOfExpressions;
	}

	protected void setNumOfExpressions(int myNumOfExpressions) {
		this.myNumOfExpressions = myNumOfExpressions;
	}

	public String getInstruction() {
		return myInstruction;
	}

	public ArrayList<Command> getArguments() {
		return myArguments;
	}

	protected ArrayList<Object> getConvertedArguments() {
		return myConvertedArguments;
	}

	protected void setConvertedArguments(ArrayList<Object> myConvertedArguments) {
		this.myConvertedArguments = myConvertedArguments;
	}

	public boolean isFinished() {
		return myFinished;
	}

	protected void setFinished(boolean myFinished) {
		this.myFinished = myFinished;
	}

	protected Turtle getTurtle() {
		return myTurtle;
	}

	protected void setTurtle(Turtle myTurtle) {
		this.myTurtle = myTurtle;
	}

	protected Double getValue() {
		return myValue;
	}

	protected void setValue(Double myValue) {
		this.myValue = myValue;
	}

	protected VariableManager getVariables() {
		return myVariables;
	}

	protected void setVariables(VariableManager myVariables) {
		this.myVariables = myVariables;
	}

	protected UserMethodManager getUserMethods() {
		return myUserMethods;
	}

	protected void setUserMethods(UserMethodManager myUserMethods) {
		this.myUserMethods = myUserMethods;
	}

	protected TurtleManagerCommandAPI getTurtleManager() {
		return myTurtleManager;
	}

	protected void setTurtleManager(TurtleManagerCommandAPI myTurtleManager) {
		this.myTurtleManager = myTurtleManager;
	}

	public boolean isRunAllTurtles() {
		return myRunAllTurtles;
	}

	protected void setRunAllTurtles(boolean myRunAllTurtles) {
		this.myRunAllTurtles = myRunAllTurtles;
	}

	public void add(Command... args) {
		for (Command each : args) {
			myArguments.add(each);
		}
	}

	protected void changeToFinished() {
		myFinished = true;
	}

	public void resetCommand() {
		myFinished = false;
	}

	protected List<Command> unNestList(Command c) {
		return c.getArguments();
	}

	public void performBeforeExecution() {
	}

	protected abstract Double getValue(List<Object> args, VariableManager localVariables);

	public Double executeCommand(TurtleManagerCommandAPI turtles, VariableManager vars, Double turtleID) {
		myTurtleManager = turtles;
		VariableManager localVariables = new VariableManager();
		localVariables.addAll(vars.getVariableMap());
		myTurtle = turtles.getTurtle(turtleID);
		myConvertedArguments = argumentsToConvert(localVariables);
		this.changeToFinished();
		return this.getValue(myConvertedArguments, localVariables);
	}

	protected ArrayList<Object> argumentsToConvert(VariableManager vars) {
		return convertArguments(myArguments, vars, true);
	}

	protected ArrayList<Object> convertArguments(List<Command> list, VariableManager localVariables, boolean nest) {
		ArrayList<Object> newArgs = new ArrayList<Object>();
		for (Command o : list) {
			try {
				if (nest) {
					Double value = o.executeCommand(myTurtleManager, localVariables, myTurtle.getID());
					if (value != null) {
						newArgs.add(value);
					} else {
						throw new ParserException(String.format("WRONG INPUT %s", o), new Exception());
					}
				} else {
					newArgs.add(o);
				}
			} catch (Exception e) {
				throw new ParserException(String.format("WRONG INPUT %s", o), new Exception());
			}
		}
		return newArgs;
	}

	protected ArrayList<Object> convertArguments(Command list, VariableManager vars, boolean nest) {
		ArrayList<Command> temp = new ArrayList<Command>(Collections.singletonList(list));
		return convertArguments(temp, vars, nest);
	}
}
