package commands;

import java.util.List;

import backend.UserMethodManager;
import backend.VariableManager;

/**
 * SetHeadingCommand, a subclass of MoveCommand
 * @author Yanbo Fang
 * @author Henry Taylor
 */
public class SetHeadingCommand extends MoveCommand {
	
	private static final long serialVersionUID = -8147616540009761382L;
	private static final Integer NUM_OF_EXPRESSIONS = 1;

	public SetHeadingCommand(String instruction, VariableManager variables, UserMethodManager methods) {
		super(instruction, variables, methods, NUM_OF_EXPRESSIONS);
	}
	
	public Double calculateValue(List<Object> args) {
		Double degrees = (Double) args.get(args.size()-1);
		Double difference = getTurtle().getRotate() - degrees;
		getTurtle().setRotate(degrees);;
		return difference;
	}


}
