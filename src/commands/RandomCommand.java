package commands;

import java.util.List;

import backend.UserMethodManager;
import backend.VariableManager;

/**
 * RandomCommand, a subclass of AbstractCommand
 * @author Yanbo Fang
 * @author Henry Taylor
 */
public class RandomCommand extends AbstractCommand {

	private static final long serialVersionUID = 2218264768113738642L;
	private static final Integer NUM_OF_EXPRESSIONS = 1;
	
	public RandomCommand(String instruction, VariableManager variables,
			UserMethodManager methods) {
		super(instruction, variables, methods, NUM_OF_EXPRESSIONS);
	}

	@Override
	public Double getValue(List<Object> args, VariableManager vars) {
		Double max = (Double) args.get(args.size()-1);
		return Math.random() * (max - 0) + 0;
	}
	
	

}
