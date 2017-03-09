package commands;

import java.util.List;

import backend.UserMethodManager;
import backend.VariableManager;

public class DifferenceCommand extends AbstractCommand {

	private static final Integer NUM_OF_EXPRESSIONS = 2;

	public DifferenceCommand(String instruction, VariableManager variables, UserMethodManager methods) {
		super(instruction, variables, methods, NUM_OF_EXPRESSIONS);

	}

	@Override
	public Double getValue(List<Object> args, VariableManager vars) {
		Double start = (Double) args.get(0);
		args.remove(start);
		Double difference = args.stream()
			.mapToDouble(d -> (Double) d)
			.sum();	
		return start - difference;
	}

}
