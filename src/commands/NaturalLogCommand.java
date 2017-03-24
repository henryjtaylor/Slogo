package commands;

import java.util.List;

import backend.UserMethodManager;
import backend.VariableManager;

/**
 * NaturalLogCommand, a subclass of AbstractCommand
 * @author Yanbo Fang
 * @author Henry Taylor
 */
public class NaturalLogCommand extends AbstractCommand {
	
	private static final long serialVersionUID = -4158360913155771953L;
	private static final Integer NUM_OF_EXPRESSIONS = 1;
	
	public NaturalLogCommand(String instruction, VariableManager variables,
			UserMethodManager methods) {
		super(instruction, variables, methods, NUM_OF_EXPRESSIONS);
	}

	@Override
	public Double getValue(List<Object> args, VariableManager vars) {
		return Math.log((Double) args.get(args.size()-1));
	}
}
