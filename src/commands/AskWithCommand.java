package commands;

import java.util.ArrayList;
import java.util.List;

import turtles.Turtle;
import turtles.TurtleManagerCommandAPI;
import backend.UserMethodManager;
import backend.VariableManager;

public class AskWithCommand extends LoopCommand {

	private static final int NUM_OF_EXPRESSIONS = 2;

	public AskWithCommand(String instruction, VariableManager variables, UserMethodManager methods) {
		super(instruction, variables, methods, NUM_OF_EXPRESSIONS);
	}

	@Override
	protected ArrayList<Object> argumentsToConvert(VariableManager vars) {
		ArrayList<Object> convArgs = new ArrayList<Object>();
		convArgs.add(myArguments.get(0));
		convArgs.add(myArguments.get(1));
		return convArgs;
	}

	private List<Double> getPossibleTurtles(Command condition, VariableManager vars) {
		List<Double> myIDList = new ArrayList<Double>();
		for (Turtle t : myTurtleManager.allTurtles()) {
			if (condition.executeCommand(myTurtleManager, vars, t.getID()) == 1) {
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
