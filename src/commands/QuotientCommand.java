package commands;

import java.util.List;

import backend.UserMethodManager;
import backend.VariableManager;

/**
 * QuotientCommand, a subclass of AbstractCommand
 * @author Yanbo Fang
 * @author Henry Taylor
 */
public class QuotientCommand extends AbstractCommand {
	
	private static final long serialVersionUID = 6193171677458971186L;
	private static final Integer NUM_OF_EXPRESSIONS = 2;

	public QuotientCommand(String instruction, VariableManager variables, UserMethodManager methods) {
		super(instruction, variables, methods, NUM_OF_EXPRESSIONS);
	}

	@Override
	public Double getValue(List<Object> args, VariableManager vars) {
		Double[] start = {(Double) args.get(0)};
		args.remove(start[0]);
		args.stream()
			.forEach(d -> start[0]  /= (Double) d);
		return start[0];
	}


}
