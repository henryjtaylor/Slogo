package commands;

import java.util.List;

import backend.UserMethodManager;
import backend.VariableManager;

/**
 * AndCommand, a subclass of AbstractCommand
 * @author Yanbo Fang
 * @author Henry Taylor
 */
public class EqualCommand extends AbstractCommand {

	private static final long serialVersionUID = -3687395848956032308L;
	private static final Integer NUM_OF_EXPRESSIONS = 2;

	public EqualCommand(String instruction, VariableManager variables, UserMethodManager methods) {
		super(instruction, variables, methods, NUM_OF_EXPRESSIONS);
	}

	@Override
	public Double getValue(List<Object> args, VariableManager vars) {
		Double reference = (Double) args.get(0);
		double[] compareList = args.stream().mapToDouble(d -> (Double) d).filter(d -> d == reference).toArray();
		setValue(compareList.length == args.size() ? 1.0 : 0.0);
		return getValue();
	}

}
