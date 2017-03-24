package commands;

import java.util.List;

import backend.UserMethodManager;
import backend.VariableManager;

/**
 * GetShapeCommand, a subclass of AbstractCommand
 * @author Yanbo Fang
 * @author Henry Taylor
 */
public class GetShapeCommand extends AbstractCommand {

	private static final long serialVersionUID = 8560847640187359737L;
	private static final int NUM_OF_EXPRESSIONS = 0;
	
	public GetShapeCommand(String instruction, VariableManager variables,
			UserMethodManager methods) {
		super(instruction, variables, methods, NUM_OF_EXPRESSIONS);
	}

	@Override
	public Double getValue(List<Object> args, VariableManager localVariables) {
		return getTurtle().getShape();
	}

	
	
}
