package commands;

import java.util.List;

import backend.UserMethodManager;
import backend.VariableManager;

/**
 * SetShapeCommand, a subclass of AbstractCommand
 * @author Yanbo Fang
 * @author Henry Taylor
 */
public class SetShapeCommand extends AbstractCommand {

	private static final long serialVersionUID = -874875269929958110L;
	private static final int NUM_OF_EXPRESSIONS = 1;
	
	public SetShapeCommand(String instruction, VariableManager variables,
			UserMethodManager methods) {
		super(instruction, variables, methods, NUM_OF_EXPRESSIONS);
		
	}

	@Override
	public Double getValue(List<Object> args, VariableManager localVariables) {
		Double value = (Double) args.get(args.size()-1);
		getTurtle().setImage(value);
		return value;
	}
	

}
