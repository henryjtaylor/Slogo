package commands;

import java.util.List;

import backend.UserMethodManager;
import backend.VariableManager;

public class ArcTangentCommand extends AbstractCommand {
	private static final Integer NUM_OF_EXPRESSIONS = 1;
	
	public ArcTangentCommand(String instruction, VariableManager variables,
			UserMethodManager methods) {
		super(instruction, variables, methods, NUM_OF_EXPRESSIONS);
	}

	@Override
	public Double getValue(List<Object> args) {
		return Math.atan(Math.toRadians((Double) args.get(0)));
	}
}