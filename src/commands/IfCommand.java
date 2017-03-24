package commands;

import java.util.List;

import backend.UserMethodManager;
import backend.VariableManager;

/**
 * IfCommand, a subclass of ConditionalCommand
 * @author Yanbo Fang
 * @author Henry Taylor
 */
public class IfCommand extends ConditionalCommand {

	private static final long serialVersionUID = -8889501036189048184L;
	private static final Integer NUM_OF_EXPRESSIONS = 2;

	public IfCommand(String instruction, VariableManager variables, UserMethodManager methods) {
		super(instruction, variables, methods, NUM_OF_EXPRESSIONS);
	}

	@Override
	public Double getValue(List<Object> args, VariableManager vars) {
		Double returnValue = 0.0;
		int k = 0;
		System.out.println(args);
		while ( k < args.size()) {
			returnValue = execute((Double) args.get(k), (Command) args.get(k+1), false, vars);
			k += NUM_OF_EXPRESSIONS;
		}
		return returnValue;
	}

}
