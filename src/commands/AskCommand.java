package commands;

import java.util.ArrayList;
import java.util.List;

import backend.UserMethodManager;
import backend.VariableManager;

/**
 * AskCommand, a subclass of LoopCommand
 * @author Yanbo Fang
 * @author Henry Taylor
 */
public class AskCommand extends LoopCommand {

	private static final long serialVersionUID = 2539398430320890157L;

	public AskCommand(String instruction, VariableManager variables, UserMethodManager methods) {
		super(instruction, variables, methods);
	}

	@Override
	protected ArrayList<Object> argumentsToConvert(VariableManager vars) {
		ArrayList<Object> convArgs = new ArrayList<Object>();
		getArguments().stream().forEach(c -> {
			convArgs.add(getArguments().indexOf(c) % 2 == 0 ? convertArguments(c.getArguments(), vars, true) : c);
		});
		return convArgs;
	}

	@Override
	protected Double calculate(List<Object> args, VariableManager vars) {
		Double returnValue = 0.0;
		for (Object d : args) {
			returnValue = runCommands(1.0, 1.0, 1.0, null, vars, (Double) d);
		}
		return returnValue;
	}

}
