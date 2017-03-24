package commands;

import java.util.ArrayList;
import java.util.List;

import backend.UserMethodManager;
import backend.VariableManager;

/**
 * ConditionalCommand, a subclass of AbstractCommand
 * @author Yanbo Fang
 * @author Henry Taylor
 */
public abstract class ConditionalCommand extends AbstractCommand {

	private static final long serialVersionUID = -3640331309373660433L;

	public ConditionalCommand(String instruction, VariableManager variables, UserMethodManager methods,
			int numOfExpressions) {
		super(instruction, variables, methods, numOfExpressions);
		setRunAllTurtles(true);
	}

	@Override
	public abstract Double getValue(List<Object> args, VariableManager vars);

	protected Double execute(Double expr, Command commandList, boolean ifElse, VariableManager vars) {
		Double returnValue = 0.0;
		if (ifElse == false && expr == 0.0) {
			return 0.0;
		}
		commandList.resetCommand();
		while (!commandList.isFinished()) {
			returnValue = commandList.executeCommand(getTurtleManager(), vars, getTurtle().getID());
		}
		return returnValue;
	}

	@Override
	protected ArrayList<Object> argumentsToConvert(VariableManager vars) {
		ArrayList<Object> convArgs = new ArrayList<Object>();
		getArguments().stream()
			.forEach(c -> {
				if (getArguments().indexOf(c) % 3 == 0){
					convArgs.addAll(convertArguments(c, vars, true));
				} else {
					convArgs.add(c);
				}
			});
		return convArgs;
	}

}
