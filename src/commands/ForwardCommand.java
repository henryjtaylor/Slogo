package commands;


import java.util.List;

import backend.UserMethodManager;
import backend.VariableManager;
import coordinate.Coordinate;

/**
 * ForwardCommand, a subclass of MoveCommand
 * @author Yanbo Fang
 * @author Henry Taylor
 */
public class ForwardCommand extends MoveCommand {
	
	private static final long serialVersionUID = 8739243319491895439L;
	private static final Integer NUM_OF_EXPRESSIONS = 1;
	private Coordinate myCoord;

	public ForwardCommand(String instruction, VariableManager variables, UserMethodManager methods) {
		super(instruction, variables, methods, NUM_OF_EXPRESSIONS);
	}
	
	@Override
	public Double calculateValue(List<Object> args) {
		args.stream()
		.mapToDouble(d -> (Double) d)
		.forEach(d -> {
			myCoord = getNewCoord(d);
			getTurtle().setLocation(myCoord, false);
		});
		return (Double) args.get(args.size()-1);
	}

}
