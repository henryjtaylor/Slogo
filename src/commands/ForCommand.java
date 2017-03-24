package commands;

import java.util.List;
import backend.UserMethodManager;
import backend.Variable;
import backend.VariableManager;

/**
 * AndCommand, a subclass of LoopCommand
 * @author Yanbo Fang
 * @author Henry Taylor
 */
public class ForCommand extends LoopCommand {

	private static final long serialVersionUID = -8273555395033115049L;

	public ForCommand(String instruction, VariableManager variables, UserMethodManager methods) {
		super(instruction, variables, methods);
	}

	@Override
	protected Double calculate(List<Object> args, VariableManager vars) {
		Double start = ((Command) args.get(1)).executeCommand(getTurtleManager(), vars, getTurtle().getID());
		Double end = ((Command) args.get(2)).executeCommand(getTurtleManager(), vars, getTurtle().getID());
		Double increment = ((Command) args.get(3)).executeCommand(getTurtleManager(), vars, getTurtle().getID());
		Variable var = new Variable(((Command) args.get(0)).getInstruction(), start);
		vars.addVariable(var);
		return runCommands(start, end, increment, var, vars, getTurtle().getID());
	}

}
