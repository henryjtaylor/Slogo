package commands;

import java.util.List;

import backend.UserMethodManager;
import backend.VariableManager;

/**
 * ListCommand, a subclass of AbstractCommand
 * @author Yanbo Fang
 * @author Henry Taylor
 */
public class ListCommand extends AbstractCommand {

	private static final long serialVersionUID = -8339729797090235764L;
	private static final int NUM_OF_EXPRESSIONS = -1;
	public ListCommand(String instruction, VariableManager variables,
			UserMethodManager methods) {
		super(instruction, variables, methods, NUM_OF_EXPRESSIONS);
		setRunAllTurtles(true);
	}
	@Override
	public Double getValue(List<Object> args, VariableManager localVariables) {
		return (Double) args.get(args.size()-1);
	}

}
