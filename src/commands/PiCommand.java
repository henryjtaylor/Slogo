package commands;

import java.util.List;

import backend.UserMethodManager;
import backend.VariableManager;

/**
 * PiCommand, a subclass of AbstractCommand
 * @author Yanbo Fang
 * @author Henry Taylor
 */
public class PiCommand extends AbstractCommand {

	private static final long serialVersionUID = 6595070706710694910L;
	private static final Integer NUM_OF_EXPRESSIONS = 0;
	
	public PiCommand(String instruction, VariableManager variables,
			UserMethodManager methods) {
		super(instruction, variables, methods, NUM_OF_EXPRESSIONS);
	}

	@Override
	public Double getValue(List<Object> args, VariableManager vars) {
		return Math.PI;
	}
	
}

