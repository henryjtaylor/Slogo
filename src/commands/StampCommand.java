package commands;

import java.util.List;

import backend.UserMethodManager;
import backend.VariableManager;

public class StampCommand extends AbstractCommand {
	
	private static final Integer NUM_OF_EXPRESSIONS = 0;

	public StampCommand(String instruction, VariableManager variables, UserMethodManager methods) {
		super(instruction, variables, methods, NUM_OF_EXPRESSIONS);
		setRunAllTurtles(true);
	}
	

	@Override
	protected Double getValue(List<Object> args, VariableManager localVariables) {
		getTurtle().stamp();
		return getTurtle().getID();
	}	
}
