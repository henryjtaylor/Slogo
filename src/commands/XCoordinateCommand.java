package commands;

import java.util.List;

import backend.UserMethodManager;
import backend.VariableManager;

/**
 * XCoordinateCommand, a subclass of AbstractCommand
 * @author Yanbo Fang
 * @author Henry Taylor
 */
public class XCoordinateCommand extends AbstractCommand {

	private static final long serialVersionUID = 7651663277325963008L;
	private static final Integer NUM_OF_EXPRESSIONS = 0;
	
	public XCoordinateCommand(String instruction, VariableManager variables,
			UserMethodManager methods) {
		super(instruction, variables, methods, NUM_OF_EXPRESSIONS);
		setRunAllTurtles(true);
	}

	@Override
	public Double getValue(List<Object> args, VariableManager vars) {
		setValue(getTurtle().getLocation(true).getX());
		return getValue();
	}

}
