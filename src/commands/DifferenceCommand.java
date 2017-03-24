package commands;

import java.util.List;

import backend.UserMethodManager;
import backend.VariableManager;

/**
 * DifferenceCommand, a subclass of AbstractCommand
 * @author Yanbo Fang
 * @author Henry Taylor
 */
public class DifferenceCommand extends AbstractCommand {

	private static final long serialVersionUID = 8490462159227604653L;
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
