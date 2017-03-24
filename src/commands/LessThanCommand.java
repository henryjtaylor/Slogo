package commands;

import java.util.List;

import backend.UserMethodManager;
import backend.VariableManager;

/**
 * LessThanCommand, a subclass of AbstractCommand
 * @author Yanbo Fang
 * @author Henry Taylor
 */
public class LessThanCommand extends AbstractCommand {

	private static final long serialVersionUID = -1985738851272177631L;
	private static final Integer NUM_OF_EXPRESSIONS = 2;

	public LessThanCommand(String instruction, VariableManager variables, UserMethodManager methods) {
		super(instruction, variables, methods, NUM_OF_EXPRESSIONS);
	}

	@Override
	public Double getValue(List<Object> args, VariableManager vars) {
		Double exp1 = (Double) args.get(args.size() - 2);
		Double exp2 = (Double) args.get(args.size() - 1);
		setValue(exp1 < exp2 ? 1.0 : 0.0);
		return getValue();
	}

}
