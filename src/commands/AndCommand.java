package commands;

import java.util.ArrayList;
import java.util.List;

import backend.UserMethodManager;
import backend.VariableManager;

/**
 * AndCommand, a subclass of AbstractCommand
 * @author Yanbo Fang
 * @author Henry Taylor
 */
public class AndCommand extends AbstractCommand {

	private static final long serialVersionUID = 1021018639939721241L;
	private static final Integer NUM_OF_EXPRESSIONS = 2;

	public AndCommand(String instruction, VariableManager variables, UserMethodManager methods) {
		super(instruction, variables, methods, NUM_OF_EXPRESSIONS);
	}

	@Override
	public Double getValue(List<Object> args, VariableManager localVariables) {
		List<Double> correctList = new ArrayList<Double>();
		args.stream().mapToDouble(d -> (Double) d).filter(d -> d != 0.0).forEach(d -> correctList.add(d));
		setValue(correctList.size() == args.size() ? 1.0 : 0.0);
		return getValue();
	}

}
