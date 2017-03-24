package commands;

import java.util.List;

import backend.UserMethodManager;
import backend.VariableManager;

/**
 * HeadingCommand, a subclass of AbstractCommand
 * @author Yanbo Fang
 * @author Henry Taylor
 */
public class HeadingCommand extends AbstractCommand {

	private static final long serialVersionUID = -6622070321152005115L;
	private static final Integer NUM_OF_EXPRESSION = 0;
	
	public HeadingCommand(String instruction, VariableManager variables,
			UserMethodManager methods) {
		super(instruction, variables, methods, NUM_OF_EXPRESSION);
		setRunAllTurtles(true);
	}

	public Double getValue(List<Object> args, VariableManager vars) {
		return getTurtle().getRotate();
	}

}
