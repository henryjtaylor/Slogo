package commands;

import java.util.List;

import backend.UserMethodManager;
import backend.VariableManager;

/**
 * AndCommand, a subclass of AbstractCommand
 * @author Yanbo Fang
 * @author Henry Taylor
 */
public class ConstantCommand extends AbstractCommand {

	private static final long serialVersionUID = 834632117687447006L;
	private static final Integer NUM_OF_EXPRESSIONS = 0;

	public ConstantCommand(String instruction, VariableManager variables, UserMethodManager methods) {
		super(instruction, variables, methods, NUM_OF_EXPRESSIONS);
	}

	@Override
	public Double getValue(List<Object> args, VariableManager vars) {
		return Double.parseDouble(getInstruction());
	}

}
