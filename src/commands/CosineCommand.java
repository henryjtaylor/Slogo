package commands;

import java.util.List;

import backend.UserMethodManager;
import backend.VariableManager;

/**
 * AndCommand, a subclass of AbstractCommand
 * @author Yanbo Fang
 * @author Henry Taylor
 */
public class CosineCommand extends AbstractCommand {

	private static final long serialVersionUID = -465671639200748418L;
	private static final Integer NUM_OF_EXPRESSIONS = 1;

	public CosineCommand(String instruction, VariableManager variables, UserMethodManager methods) {
		super(instruction, variables, methods, NUM_OF_EXPRESSIONS);
	}

	@Override
	public Double getValue(List<Object> args, VariableManager vars) {
		return Math.cos(Math.toRadians((Double) args.get(args.size()-1)));
	}
}
