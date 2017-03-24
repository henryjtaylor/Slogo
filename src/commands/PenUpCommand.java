package commands;

import java.util.List;

import backend.UserMethodManager;
import backend.VariableManager;

/**
 * PenUpCommand, a subclass of AbstractCommand
 * @author Yanbo Fang
 * @author Henry Taylor
 */
public class PenUpCommand extends AbstractCommand {

	private static final long serialVersionUID = -4726959028806683686L;
	private static final Integer NUM_OF_EXPRESSIONS = 0;

	public PenUpCommand(String instruction, VariableManager variables,
			UserMethodManager methods) {
		super(instruction, variables, methods, NUM_OF_EXPRESSIONS);
		setRunAllTurtles(true);
	}

	@Override
	public Double getValue(List<Object> args, VariableManager vars) {
		setValue(0.0);
		getTurtleManager().setPenState(false, getTurtle().getID());
		return getValue();
	}

}
