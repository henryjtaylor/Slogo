package commands;

import backend.UserMethodManager;
import backend.VariableManager;


public class LeftCommand extends MoveCommand {

	private static final Integer NUM_OF_EXPRESSIONS = 1;
	
	public LeftCommand(String instruction, VariableManager variables, UserMethodManager methods) {
		super(instruction, variables, methods, NUM_OF_EXPRESSIONS);
	}
	
	public Double calculateValue() {
		Double degrees = (Double) myArguments.get(0);
		myTurtle.setFutureRotate(myTurtle.getFutureRotate() - degrees);
		return degrees;
	}
	
	public Double executeCommand() {
		myTurtle.setRotate(myTurtle.getRotate() - myValue);
		return myValue;
	}

}