package commands;

import java.util.List;

import backend.UserMethodManager;
import backend.VariableManager;

/**
 * Product, a subclass of AbstractCommand
 * @author Yanbo Fang
 * @author Henry Taylor
 */
public class ProductCommand extends AbstractCommand {

	private static final long serialVersionUID = 1130203569956614952L;
	private static final Integer NUM_OF_EXPRESSIONS = 2;

	public ProductCommand(String instruction, VariableManager variables, UserMethodManager methods) {
		super(instruction, variables, methods, NUM_OF_EXPRESSIONS);
	}

	@Override
	public Double getValue(List<Object> args, VariableManager vars) {
		Double[] array = {1.0};
		args.stream()
			.forEach(d -> array[0] *= (Double) d);
		return array[0];
	}


}
