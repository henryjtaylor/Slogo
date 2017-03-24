package commands;

import java.util.List;

import backend.UserMethodManager;
import backend.VariableManager;

/**
 * IDCommand, a subclass of AbstractCommand
 * @author Yanbo Fang
 * @author Henry Taylor
 */
public class IDCommand extends AbstractCommand {

	private static final long serialVersionUID = 4931527663220004673L;
	private static final int NUM_OF_EXPRESSIONS = 0;
	public IDCommand(String instruction, VariableManager variables,
			UserMethodManager methods) {
		super(instruction, variables, methods, NUM_OF_EXPRESSIONS);
		setRunAllTurtles(true);
	}
	@Override
	public Double getValue(List<Object> args, VariableManager vars) {
		return getTurtle().getID();
	}

}
