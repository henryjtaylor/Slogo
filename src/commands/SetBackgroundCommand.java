package commands;

import java.util.List;

import backend.UserMethodManager;
import backend.VariableManager;

/**
 * SetBackgroundCommand, a subclass of AbstractCommand
 * @author Yanbo Fang
 * @author Henry Taylor
 */
public class SetBackgroundCommand extends AbstractCommand {

	private static final long serialVersionUID = 3390092944986979730L;
	private static final int NUM_OF_EXPRESSIONS = 1;
	public SetBackgroundCommand(String instruction, VariableManager variables,
			UserMethodManager methods) {
		super(instruction, variables, methods, NUM_OF_EXPRESSIONS);
	}
	@Override
	public Double getValue(List<Object> args, VariableManager localVariables) {
		Double value = (Double) args.get(args.size()-1);
		getTurtleManager().setBackground(value);
		return value;
	}

}
