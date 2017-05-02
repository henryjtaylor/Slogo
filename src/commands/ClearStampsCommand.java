package commands;

import java.util.List;

import backend.UserMethodManager;
import backend.VariableManager;

public class ClearStampsCommand extends AbstractCommand {

	private static final Integer NUM_OF_EXPRESSIONS = 0;

	public ClearStampsCommand(String instruction, VariableManager variables, UserMethodManager methods) {
		super(instruction, variables, methods, NUM_OF_EXPRESSIONS);
		setRunAllTurtles(true);
	}
	

	@Override
	protected Double getValue(List<Object> args, VariableManager localVariables) {
		return getTurtle().clearStamp() ? new Double(1) : new Double(0);
	}	
}
