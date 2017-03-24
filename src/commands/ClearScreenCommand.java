package commands;

import java.util.List;

import coordinate.Coordinate;
import backend.UserMethodManager;
import backend.VariableManager;

/**
 * ClearScreenCommand, a subclass of AbstractCommand
 * @author Yanbo Fang
 * @author Henry Taylor
 */
public class ClearScreenCommand extends MoveCommand {

	private static final long serialVersionUID = 6908412808584611193L;
	private static final Integer NUM_OF_EXPRESSIONS = 0;
	private Coordinate myCoord;

	public ClearScreenCommand(String instruction, VariableManager variables,
			UserMethodManager methods) {
		super(instruction, variables, methods, NUM_OF_EXPRESSIONS);
	}

	@Override
	public Double calculateValue(List<Object> args) {
		myCoord = toHome();
		getTurtle().setClear(true);
		return getValue();
	}


}
