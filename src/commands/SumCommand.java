package commands;

import java.util.List;
import backend.UserMethodManager;
import backend.VariableManager;

/**
 * SumCommand, a subclass of AbstractCommand
 * @author Yanbo Fang
 * @author Henry Taylor
 */
public class SumCommand extends AbstractCommand {
	
	private static final long serialVersionUID = 7633449437457178568L;
	private static final Integer NUM_OF_EXPRESSIONS = 2;

	public SumCommand(String instruction, VariableManager variables, UserMethodManager methods) {
		super(instruction, variables, methods, NUM_OF_EXPRESSIONS);
	}

	@Override
	public Double getValue(List<Object> args, VariableManager vars) {
		return args.stream().mapToDouble(d -> (Double) d).sum();
	}

}
