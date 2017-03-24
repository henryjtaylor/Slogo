package commands;

import java.util.List;

import backend.UserMethodManager;
import backend.VariableManager;

/**
 * PowerCommand, a subclass of AbstractCommand
 * @author Yanbo Fang
 * @author Henry Taylor
 */
public class PowerCommand extends AbstractCommand {

	private static final long serialVersionUID = 4973578028556953130L;
	private static final Integer NUM_OF_EXPRESSIONS = 1;
	
	public PowerCommand(String instruction, VariableManager variables,
			UserMethodManager methods) {
		super(instruction, variables, methods, NUM_OF_EXPRESSIONS);
	}

	@Override
	public Double getValue(List<Object> args, VariableManager vars) {
		return Math.pow((Double) args.get(args.size()-2), (Double) args.get(args.size()-1));
	}
}
