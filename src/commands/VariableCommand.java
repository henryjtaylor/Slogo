package commands;

import java.util.List;

import backend.UserMethodManager;
import backend.VariableManager;

/**
 * VariableCommand, a subclass of AbstractCommand
 * @author Yanbo Fang
 * @author Henry Taylor
 */
public class VariableCommand extends AbstractCommand {

	private static final long serialVersionUID = -9068230354576988445L;
	private static final Integer NUM_OF_EXPRESSIONS = 0;

	public VariableCommand(String instruction, VariableManager variables, UserMethodManager methods) {
		super(instruction, variables, methods, NUM_OF_EXPRESSIONS);
	}

	@Override
	public Double getValue(List<Object> args, VariableManager localVariables) {
		if (localVariables.get(getInstruction()) == null) {
			return null;
		}
		return localVariables.get(getInstruction()).getValue();
	}

}
