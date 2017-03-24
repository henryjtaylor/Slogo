package commands;

import java.util.List;

import backend.UserMethodManager;
import backend.VariableManager;

/**
 * TurtlesCommand, a subclass of AbstractCommand
 * @author Yanbo Fang
 * @author Henry Taylor
 */
public class TurtlesCommand extends AbstractCommand {

	private static final long serialVersionUID = 7453547348846595875L;
	private static final int NUM_OF_EXPRESSIONS = 0;

	public TurtlesCommand(String instruction, VariableManager variables, UserMethodManager methods) {
		super(instruction, variables, methods, NUM_OF_EXPRESSIONS);
	}

	@Override
	public Double getValue(List<Object> args, VariableManager vars) {
		return new Double(getTurtleManager().allTurtles().size());
	}

}
