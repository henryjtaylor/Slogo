package commands;

import java.util.List;

import backend.UserMethodManager;
import backend.VariableManager;

/**
 * YCoordinateCommand, a subclass of AbstractCommand
 * @author Yanbo Fang
 * @author Henry Taylor
 */
public class YCoordinateCommand extends AbstractCommand {

	private static final long serialVersionUID = 6530716139534500897L;
	private static final Integer NUM_OF_EXPRESSIONS = 0;
	
	public YCoordinateCommand(String instruction, VariableManager variables,
			UserMethodManager methods) {
		super(instruction, variables, methods, NUM_OF_EXPRESSIONS);
		setRunAllTurtles(true);
	}

	@Override
	public Double getValue(List<Object> args, VariableManager vars) {
		setValue(getTurtle().getLocation(true).getY());
		return getValue();
	}


}
