package commands;

import java.util.List;

import backend.UserMethodManager;
import backend.VariableManager;

/**
 * MinusCommand, a subclass of AbstractCommand
 * @author Yanbo Fang
 * @author Henry Taylor
 */
public class MinusCommand extends AbstractCommand {

	private static final long serialVersionUID = 2941347859485134480L;
	private static final Integer NUM_OF_EXPRESSIONS = 1;

	public MinusCommand(String instruction, VariableManager variables, UserMethodManager methods) {
		super(instruction, variables, methods, NUM_OF_EXPRESSIONS);

	}

	@Override
	public Double getValue(List<Object> args, VariableManager vars) {
		setValue(-1.0 * (Double) args.get(args.size()-1));
		return getValue();
	}

}
