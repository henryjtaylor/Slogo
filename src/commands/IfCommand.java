package commands;

import java.util.List;

import backend.UserMethodManager;
import backend.VariableManager;

public class IfCommand extends ConditionalCommand {

	private static final Integer NUM_OF_EXPRESSIONS = 2;

	public IfCommand(String instruction, VariableManager variables, UserMethodManager methods) {
		super(instruction, variables, methods, NUM_OF_EXPRESSIONS);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Double getValue(List<Object> args) {
		// TODO Auto-generated method stub
		Double returnValue = 0.0;
		returnValue = execute(returnValue, (Double) args.get(0), (List<Command>) args.get(1), false);
		return returnValue;
	}

}
