package commands;

import java.util.List;

import backend.UserMethodManager;
import backend.VariableManager;

/**
 * RightCommand, a subclass of MoveCommand
 * @author Yanbo Fang
 * @author Henry Taylor
 */
public class RightCommand extends MoveCommand {

	private static final long serialVersionUID = -8771743056044012777L;
	private static final Integer NUM_OF_EXPRESSIONS = 1;

	public RightCommand(String instruction, VariableManager variables, UserMethodManager methods) {
		super(instruction, variables, methods, NUM_OF_EXPRESSIONS);
	}
	
	public Double calculateValue(List<Object> args) {
		args.stream()
			.forEach(d-> getTurtle().setRotate(getTurtle().getRotate() + (Double) d));
		return (Double) args.get(0);
	}
	
}
