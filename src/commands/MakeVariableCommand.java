package commands;

import java.util.ArrayList;
import java.util.List;

import turtles.TurtleManagerCommandAPI;
import backend.UserMethodManager;
import backend.Variable;
import backend.VariableManager;

/**
 * MakeVariableCommand, a subclass of AbstractCommand
 * @author Yanbo Fang
 * @author Henry Taylor
 */
public class MakeVariableCommand extends AbstractCommand {

	private static final long serialVersionUID = -2565186453840421257L;
	private String myVariableName;
	private Variable myVariable;
	private static final Integer NUM_OF_EXPRESSIONS = 2;

	public MakeVariableCommand(String instruction, VariableManager variables, UserMethodManager methods) {
		super(instruction, variables, methods, NUM_OF_EXPRESSIONS);
	}

	/***
	 * NEED TO ADD THE VARIABLE THE SECOND IT IS CALLED, IN CASE THE CALL USES
	 * THE VARIABLE LATER
	 */
	@Override
	public Double getValue(List<Object> args, VariableManager vars) {
		setValue((Double) args.get(args.size()-1));
		int k = 0;
		while (k < args.size()) {
			checkVariable(vars, k, args);
			k += NUM_OF_EXPRESSIONS;
		}
		return getValue();
	}

	private void checkVariable(VariableManager vars, int k, List<Object> args) {
		String varName = (String) ((Command) args.get(k)).getInstruction();
		if (vars.get(varName) != null) {
			if (vars.get(varName).getValue() != (Double) args.get(k+1)) {
				vars.addVariable(new Variable(varName, getValue()));
				return;
			}
		} else {
			myVariable = new Variable(varName, getValue());
			vars.addVariable(myVariable);
		}
	}
	
	public Double executeCommand(TurtleManagerCommandAPI turtles, VariableManager vars, Double k) {
		setTurtleManager(turtles);
		VariableManager localVariables = vars;
		setTurtle(turtles.getTurtle(k));
		setConvertedArguments(argumentsToConvert(localVariables));
		this.changeToFinished();
		return this.getValue(getConvertedArguments(), localVariables);
	}
	
	protected ArrayList<Object> argumentsToConvert(VariableManager vars) {
		ArrayList<Object> convArgs = new ArrayList<Object>();
		getArguments().stream()
			.forEach(c -> {if (getArguments().indexOf(c)%2 == 0) {
				convArgs.add(c);
			} else  {
				convArgs.addAll(convertArguments(c, vars, true));
			}
			});
		return convArgs;
	}

}
