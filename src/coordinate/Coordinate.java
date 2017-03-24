package coordinate;

/**
 * Wrapper for coordinate
 * @author Yanbo Fang
 *
 */
public class Coordinate {

	private Double myX;
	private Double myY;

	public Coordinate(Double x, Double y) {
		myX = x;
		myY = y;
	}

	public Double getX() {
		return myX;
	}

	public Double getY() {
		return myY;
	}

	public void setX(Double x) {
		myX = x;
	}

	public void setY(Double y) {
		myY = y;
	}
}
