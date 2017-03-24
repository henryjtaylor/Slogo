package commands;

import java.util.List;

import backend.UserMethodManager;
import backend.VariableManager;

/**
 * ArcTangentCommand, a subclass of AbstractCommand
 * @author Yanbo Fang
 * @author Henry Taylor
 */
public class ArcTangentCommand extends AbstractCommand {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5446861651339580939L;
	private static final Integer NUM_OF_EXPRESSIONS = 1;

	public ArcTangentCommand(String instruction, VariableManager variables, UserMethodManager methods) {
		super(instruction, variables, methods, NUM_OF_EXPRESSIONS);
	}

	@Override
	public Double getValue(List<Object> args, VariableManager localVariables) {
		return Math.atan(Math.toRadians((Double) args.get(args.size()-1)));
	}
}
