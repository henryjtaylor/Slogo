package commands;

import java.util.List;

import backend.UserMethodManager;
import backend.VariableManager;

/**
 * IsPenDownCommand, a subclass of AbstractCommand
 * @author Yanbo Fang
 * @author Henry Taylor
 */
public class IsPenDownCommand extends AbstractCommand {

	private static final long serialVersionUID = 7047672167745985902L;
	private static final Integer NUM_OF_EXPRESSION = 0;

	public IsPenDownCommand(String instruction, VariableManager variables, UserMethodManager methods) {
		super(instruction, variables, methods, NUM_OF_EXPRESSION);
		setRunAllTurtles(true);
	}

	@Override
	public Double getValue(List<Object> args, VariableManager vars) {
		setValue(getTurtle().getPen().showPen() ? 1.0 : 0.0);
		return getValue();
	}

}
