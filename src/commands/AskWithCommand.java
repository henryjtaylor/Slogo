package commands;

import java.util.ArrayList;
import java.util.List;

import turtles.Turtle;
import backend.UserMethodManager;
import backend.VariableManager;

/**
 * AskWithCommand, a subclass of LoopCommand
 * @author Yanbo Fang
 * @author Henry Taylor
 */
public class AskWithCommand extends LoopCommand {

	private static final long serialVersionUID = -8958314693164883784L;

	public AskWithCommand(String instruction, VariableManager variables, UserMethodManager methods) {
		super(instruction, variables, methods);
	}

	@Override
	protected ArrayList<Object> argumentsToConvert(VariableManager vars) {
		ArrayList<Object> convArgs = new ArrayList<Object>();
		getArguments().stream().forEach(c -> convArgs.add(c));
		return convArgs;
	}

	private List<Double> getPossibleTurtles(Command condition, VariableManager vars) {
		List<Double> myIDList = new ArrayList<Double>();
		for (Turtle t : getTurtleManager().allTurtles()) {
			if (condition.executeCommand(getTurtleManager(), vars, t.getID()) == 1) {
				myIDList.add(t.getID());
			}
		}
		return myIDList;
	}

	@Override
	protected Double calculate(List<Object> args, VariableManager vars) {
		Command condition = ((Command) args.get(0));
		List<Double> possibleTurtles = getPossibleTurtles(condition, vars);
		Double returnValue = 0.0;
		for (Double d : possibleTurtles) {
			returnValue = runCommands(1.0, 1.0, 1.0, null, vars, d);
		}
		return returnValue;
	}

}
