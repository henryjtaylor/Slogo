package commands;

import java.util.List;

import backend.UserMethodManager;
import backend.VariableManager;
import coordinate.Coordinate;

/**
 * MoveCommand, a subclass of AbstractCommand
 * @author Yanbo Fang
 * @author Henry Taylor
 */
public abstract class MoveCommand extends AbstractCommand {
	
	private static final long serialVersionUID = -2746325331777941415L;
	protected int myQuadrant;

	public MoveCommand(String instruction, VariableManager variables, UserMethodManager methods,
			int numberOfExpressions) {
		super(instruction, variables, methods, numberOfExpressions);
		setRunAllTurtles(true);
	}

	@Override
	public Double getValue(List<Object> args, VariableManager vars) {
		setValue(0.0);
		try {
			setValue(calculateValue(args));
		} catch (Exception e) {
			return null;
		}
		return getValue();
	}

	public abstract Double calculateValue(List<Object> args);

	protected Coordinate getNewCoord(Double movement) {
		double rotate = getTurtle().getRotate();
		myQuadrant = 1;
		while (rotate - 90 >= 0) {
			rotate -= 90;
			myQuadrant += 1;
		}
		if (myQuadrant == 1 || myQuadrant == 3) {
			rotate = 90 - rotate;
		}
		Coordinate currLocation = getTurtle().getLocation(true);
		Coordinate movementCoord = new Coordinate(movement * Math.cos(Math.toRadians(rotate)),
				movement * Math.sin(Math.toRadians(rotate)));
		updateCoords(movementCoord, myQuadrant);
		Double newX = movementCoord.getX() + currLocation.getX();
		Double newY = movementCoord.getY() + currLocation.getY();

		Coordinate coord = new Coordinate(newX, newY);
		return coord;
	}

	protected void updateCoords(Coordinate coord, int quadrant) {
		switch (quadrant) {
		case 1:
			coord.setY(coord.getY() * -1);
			break;
		case 2:
			break;
		case 3:
			coord.setX(coord.getX() * -1);
			break;
		case 4:
			coord.setX(coord.getX() * -1);
			coord.setY(coord.getY() * -1);
			break;
		}
	}

	protected Double calcDistance(Coordinate firstCoord, Coordinate secondCoord) {
		Double xDiff = new Double(firstCoord.getX() - secondCoord.getX());
		Double yDiff = new Double(firstCoord.getY() - secondCoord.getY());
		Double distance = Math.sqrt((xDiff * xDiff) + (yDiff * yDiff));
		return distance;
	}

	protected Coordinate toHome() {
		Coordinate coord = new Coordinate(0.0, 0.0);
		setValue(calcDistance(coord, getTurtle().getLocation(true)));
		getTurtle().setLocation(coord, false);
		return coord;
	}

}
