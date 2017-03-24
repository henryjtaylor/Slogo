package commands;

import java.util.ArrayList;
import java.util.List;

import turtles.TurtleManagerCommandAPI;
import backend.UserMethod;
import backend.UserMethodManager;
import backend.VariableManager;

/**
 * MakeUserInstructionCommand, a subclass of AbstractCommand
 * @author Yanbo Fang
 * @author Henry Taylor
 */
public class MakeUserInstructionCommand extends AbstractCommand {

	private static final long serialVersionUID = -5208260251750849740L;
	private static final Integer NUM_OF_EXPRESSIONS = 3;

	public MakeUserInstructionCommand(String instruction, VariableManager variables, UserMethodManager methods) {
		super(instruction, variables, methods, NUM_OF_EXPRESSIONS);
	}

	@Override
	public Double getValue(List<Object> args, VariableManager vars) {
		return getValue();
	}

	@Override
	public void performBeforeExecution() {
		int k = 0;
		UserMethod method = null;
		while (k < getArguments().size()) {
			method = makeMethod(getArguments().get(k + 2).getArguments(), k);
			k += NUM_OF_EXPRESSIONS;
		}
		setValue(method.getMethodName().isEmpty() ? 0.0 : 1.0);
		return;
	}

	@Override
	public Double executeCommand(TurtleManagerCommandAPI turtles, VariableManager vars, Double k) {
		this.changeToFinished();
		return getValue();
	}

	@Override
	public void add(Command... each) {
		super.add(each);
		if ((getArguments().size() + 1) % NUM_OF_EXPRESSIONS == 0) {
			makeMethod(null, getArguments().size() - 2);
		}
	}

	private UserMethod makeMethod(List<Command> function, int k) {
		int numOfVariables = 0;
		List<String> variablesNameList = new ArrayList<String>();
		for (Command c : getArguments().get(k + 1).getArguments()) {
			numOfVariables++;
			variablesNameList.add(c.getInstruction());
		}
		String name = getArguments().get(k).getInstruction();
		// Create the UserMethod as a command, instruction is the name of the
		// method
		UserMethod method = new UserMethod(name, function, variablesNameList);
		getUserMethods().add(name, method);
		return method;
	}

}
