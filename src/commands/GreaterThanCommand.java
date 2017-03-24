package commands;

import java.util.List;

import backend.UserMethodManager;
import backend.VariableManager;

/**
 * GreaterThanCommand, a subclass of AbstractCommand
 * @author Yanbo Fang
 * @author Henry Taylor
 */
public class GreaterThanCommand extends AbstractCommand {

	private static final long serialVersionUID = 6232006930610750365L;
	private static final Integer NUM_OF_EXPRESSIONS = 2;

	public GreaterThanCommand(String instruction, VariableManager variables, UserMethodManager methods) {
		super(instruction, variables, methods, NUM_OF_EXPRESSIONS);
	}

	@Override
	public Double getValue(List<Object> args, VariableManager vars) {
		Double exp1 = (Double) args.get(args.size() - 2);
		Double exp2 = (Double) args.get(args.size() - 1);
		setValue(exp1 > exp2 ? 1.0 : 0.0);
		return getValue();
	}

}
