package commands;

import java.util.List;
import backend.UserMethodManager;
import backend.Variable;
import backend.VariableManager;

/**
 * DoTimesCommand, a subclass of LoopCommand
 * @author Yanbo Fang
 * @author Henry Taylor
 */
public class DoTimesCommand extends LoopCommand {

	private static final long serialVersionUID = -5748896836448534960L;
	private static final Double DO_TIMES_START = 1.0;

	public DoTimesCommand(String instruction, VariableManager variables, UserMethodManager methods) {
		super(instruction, variables, methods);
	}

	@Override
	protected Double calculate(List<Object> args, VariableManager vars) {
		Variable var = new Variable(((Command) args.get(0)).getInstruction(), DO_TIMES_START);
		vars.addVariable(var);
		return runCommands(DO_TIMES_START, ((Command) args.get(1)).executeCommand(getTurtleManager(), vars, getTurtle().getID()),
				DO_TIMES_START, var, vars, getTurtle().getID());
	}
}
