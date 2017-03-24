package commands;

import java.util.List;

import backend.UserMethodManager;
import backend.VariableManager;

/**
 * GetPenColorCommand, a subclass of AbstractCommand
 * @author Yanbo Fang
 * @author Henry Taylor
 */
public class GetPenColorCommand extends AbstractCommand {

	private static final long serialVersionUID = -8665450371251237597L;
	private static final int NUM_OF_EXPRESSIONS = 0;
	
	public GetPenColorCommand(String instruction, VariableManager variables,
			UserMethodManager methods) {
		super(instruction, variables, methods, NUM_OF_EXPRESSIONS);
	}

	@Override
	public Double getValue(List<Object> args, VariableManager localVariables) {
		return getTurtle().getPen().getColor();
	}

}
