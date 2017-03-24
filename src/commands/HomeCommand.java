package commands;

import java.util.List;

import coordinate.Coordinate;
import backend.UserMethodManager;
import backend.VariableManager;

/**
 * HomeCommand, a subclass of AbstractCommand
 * @author Yanbo Fang
 * @author Henry Taylor
 */
public class HomeCommand extends MoveCommand {

	private static final long serialVersionUID = -3926349367468371091L;
	private static final Integer NUM_OF_EXPRESSIONS = 0;
	private Coordinate myCoord;
	
	public HomeCommand(String instruction, VariableManager variables,
			UserMethodManager methods) {
		super(instruction, variables, methods, NUM_OF_EXPRESSIONS);
	}

	@Override
	public Double calculateValue(List<Object> args) {
		myCoord = toHome();
		return getValue();
	}

	
}
