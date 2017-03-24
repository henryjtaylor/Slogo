package commands;

import java.util.List;

import backend.UserMethodManager;
import backend.VariableManager;

/**
 * LeftCommand, a subclass of MoveCommand
 * @author Yanbo Fang
 * @author Henry Taylor
 */
public class LeftCommand extends MoveCommand {

	private static final long serialVersionUID = -5696799279296128657L;
	private static final Integer NUM_OF_EXPRESSIONS = 1;
	
	public LeftCommand(String instruction, VariableManager variables, UserMethodManager methods) {
		super(instruction, variables, methods, NUM_OF_EXPRESSIONS);
	}
	
	@Override
	public Double calculateValue(List<Object> args) {
		args.stream()
			.mapToDouble(d->(Double) d)
			.forEach(d -> getTurtle().setRotate(getTurtle().getRotate() - d));
		return (Double) args.get(args.size()-1);
	}

}
