package commands;

import java.util.List;
import backend.ParserException;
import backend.UserMethod;
import backend.UserMethodManager;
import backend.Variable;
import backend.VariableManager;

/**
 * UserMethodCommand, a subclass of AbstractCommand
 * @author Yanbo Fang
 * @author Henry Taylor
 */
public class UserMethodCommand extends AbstractCommand {

	private static final long serialVersionUID = -8919002370699505597L;
	private UserMethod myMethod;
	private static final Integer NUM_OF_EXPRESSIONS = 0;

	public UserMethodCommand(String instruction, VariableManager variables, UserMethodManager methods,
			UserMethod method) {
		super(instruction, variables, methods, method.getListOfVariables().size());
		myMethod = method;
		setRunAllTurtles(true);
	}

	public UserMethodCommand(String instruction, VariableManager variables, UserMethodManager methods) {
		super(instruction, variables, methods, NUM_OF_EXPRESSIONS);
		checkForMethod();
		setRunAllTurtles(true);
	}

	private boolean checkForMethod() {
		if (getUserMethods().getUserMethod(getInstruction()) != null) {
			myMethod = getUserMethods().getUserMethod(getInstruction());
			setNumOfExpressions(myMethod.getListOfVariables().size());
			return true;
		}
		return false;
	}

	@Override
	public Double getValue(List<Object> args, VariableManager vars) {
		if (checkForMethod() == false) {
			throw new ParserException(String.format("NOT A VALID USER METHOD %s", getInstruction()), new Exception());
		}

		Double returnValue = 0.0;
		VariableManager localVariables = new VariableManager();
		localVariables.addAll(vars.getVariableMap());

		for (int i = 0; i < this.getNumOfExpressions(); i++) {
			localVariables.addVariable(new Variable(myMethod.getListOfVariables().get(i), (Double) args.get(i)));
		}

		List<Command> commands = myMethod.getListOfCommands();
		for (Command c : commands) {
			c.resetCommand();
			while (!c.isFinished()) {
				returnValue = c.executeCommand(getTurtleManager(), localVariables, getTurtle().getID());
			}
		}
		return returnValue;
	}


}
