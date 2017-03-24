package commands;

import java.util.List;

import backend.UserMethodManager;
import backend.VariableManager;

/**
 * HideTurtleCommand, a subclass of AbstractCommand
 * @author Yanbo Fang
 * @author Henry Taylor
 */
public class HideTurtleCommand extends AbstractCommand {

	private static final long serialVersionUID = -215612221642102269L;
	private static final Integer NUM_OF_EXPRESSION = 0;
	
	public HideTurtleCommand(String instruction, VariableManager variables,
			UserMethodManager methods) {
		super(instruction, variables, methods, NUM_OF_EXPRESSION);
		setRunAllTurtles(true);
	}

	@Override
	public Double getValue(List<Object> args, VariableManager vars) {
		setValue(0.0);
		getTurtle().setShow(false);
		return getValue();
	}

}
