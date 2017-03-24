package commands;

import java.util.List;

import backend.UserMethodManager;
import backend.VariableManager;
import coordinate.Coordinate;

/**
 * BackwardCommand, a subclass of MoveCommand
 * @author Yanbo Fang
 * @author Henry Taylor
 */
public class BackwardCommand extends MoveCommand {
	
	private static final long serialVersionUID = 3231308603871665816L;
	private static final Integer NUM_OF_EXPRESSIONS = 1;
	private Coordinate myCoord;
	
	public BackwardCommand(String instruction, VariableManager variables, UserMethodManager methods) {
		super(instruction, variables, methods, NUM_OF_EXPRESSIONS);
	}
	
	@Override
	public Double calculateValue(List<Object> args) {
		args.stream()
			.mapToDouble(d -> (Double) d)
			.forEach(d -> {
				myCoord = getNewCoord(d*-1);
				getTurtle().setLocation(myCoord, false);
			});
		return (Double) args.get(args.size()-1);
	}

}
