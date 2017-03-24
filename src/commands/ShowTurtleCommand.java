package commands;

import java.util.List;

import backend.UserMethodManager;
import backend.VariableManager;

/**
 * ShowTurtleCommand, a subclass of AbstractCommand
 * @author Yanbo Fang
 * @author Henry Taylor
 */
public class ShowTurtleCommand extends AbstractCommand {

	private static final long serialVersionUID = -4725764100771427793L;
	private static final Integer NUM_OF_EXPRESSIONS = 0;
	
	public ShowTurtleCommand(String instruction, VariableManager variables,
			UserMethodManager methods) {
		super(instruction, variables, methods, NUM_OF_EXPRESSIONS);
		setRunAllTurtles(true);
	}
	@Override
	public Double getValue(List<Object> args, VariableManager vars) {
		setValue(1.0);
		getTurtle().setShow(true);
		return getValue();
	}

	
	
}

