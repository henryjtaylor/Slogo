package commands;

import java.util.List;

import backend.UserMethodManager;
import backend.VariableManager;
import coordinate.Coordinate;

/**
 * SetPositionCommand, a subclass of MoveCommand
 * @author Yanbo Fang
 * @author Henry Taylor
 */
public class SetPositionCommand extends MoveCommand {

	private static final long serialVersionUID = -7474008870493729212L;
	private static final Integer NUM_OF_EXPRESSIONS = 2;
	private Coordinate myCoord;

	public SetPositionCommand(String instruction, VariableManager variables, UserMethodManager methods) {
		super(instruction, variables, methods, NUM_OF_EXPRESSIONS);
	}
	
	@Override
	public Double calculateValue(List<Object> args) {
		Double returnValue = 0.0;
		int k = 0;
		while (k < args.size()) {
			Double updatedX = (Double) args.get(k);
			Double updatedY = (Double) args.get(k+1);
			myCoord = new Coordinate(updatedX, updatedY*-1);
			updateCoords(myCoord, myQuadrant);
			returnValue = calcDistance(myCoord, getTurtle().getLocation(true));
			getTurtle().setLocation(myCoord, false);
			k += NUM_OF_EXPRESSIONS;
		}
		return returnValue;
	}

}
