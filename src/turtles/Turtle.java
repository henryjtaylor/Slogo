package turtles;

import java.util.ArrayList;
import java.util.Observable;

import coordinate.Coordinate;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Turtle Object for the backend to control and for the front end to reflect. We put the turtle in the
 * backend because we wanted commands to have direct access to the turtle to manipulate certain values,
 * such as its pen properties or its coordinates in the UI. This class controls all properties of specific
 * turtles. It also performs error checking for updating coordinates to assure the turtle does not go out of
 * bounds
 * @author Henry
 *
 */
public class Turtle extends Observable implements TurtleAPI {

	private final String myTurtlePicture = "images/turtle.png";
	private ImageView myImage;
	private double myHeightBounds;
	private double myWidthBounds;
	private Pen myPen;
	private boolean showTurtle;
	private boolean myClear;
	private Double myID;
	private Double myImageIndex;
	private ImageView myStamp;

	/**
	 * @param width - width of turtle image
	 * @param height - height of turtle image
	 * @param widthBounds - width of UI display
	 * @param heightBounds - height of UI display
	 * @param id - the Id of the turtle (in the turtle Manager)
	 */
	public Turtle(double width, double height, double widthBounds, double heightBounds, Double id) {
		Image turtleView = new Image(getClass().getClassLoader().getResourceAsStream(myTurtlePicture));
		myHeightBounds = heightBounds - height;
		myWidthBounds = widthBounds - width;
		myImage = new ImageView(turtleView);
		myImage.setFitWidth(width);
		myImage.setFitHeight(height);
		myImage.setTranslateX(myWidthBounds / 2.0);
		myImage.setTranslateY(myHeightBounds / 2.0);
		myImage.setRotate(0);
		myPen = new Pen();
		showTurtle = true;
		myClear = false;
		myID = id;
	}

	/**
	 * returns image to be displayed in the UI
	 */
	public Node getImage() {
		return myImage;
	}

	/**
	 * Sets image to correct image picture and allows for changing of viewing image
	 */
	public void setImage(Image a) {
		myImage.setImage(a);
	}

	/**
	 * sets Image given a specific index ( map from index to image located in front end )
	 * @param index - index of image
	 */
	public void setImage(Double index) {
		myImageIndex = index;
		this.setFlag(myImageIndex);
	}

	/**
	 * Sets the X position of the turtle
	 * @param x - new x position of turtle (translated to javaFX coordinate)
	 * @return - new X position
	 */
	private Double setX(double x) {
		if (x > myWidthBounds) {
			x -= myWidthBounds;
		} else if (x < 0) {
			x += myWidthBounds;
		}
		myImage.setTranslateX(x);
		return x;
	}

	/**
	 * Sets the Y position of the turtle
	 * @param y - new y position of turtle (translated to javaFX coord)
	 * @return - new Y position
	 */
	private Double setY(double y) {
		if (y > myHeightBounds) {
			y -= myHeightBounds;
		} else if (y < 0) {
			y += myHeightBounds;
		}
		myImage.setTranslateY(y);
		return y;
	}
	
	/**
	 * Sets location of the turtle. If the passed in coordinates need to be re-adjusted to correct coordinate
	 * for javaFX UI, adjusted will be false (as in the coordinates are currently adjusted for (0,0) in the center) 
	 * @param coord - new coordinate
	 * @param ajusted - true if coord is already adjusted for javaFX coords
	 */
	public void setLocation(Coordinate coord, boolean ajusted) {
		if (!ajusted) {
			coord = setUnadjustedLocation(coord);
		}
		ArrayList<Coordinate> temp = new ArrayList<Coordinate>();
		temp.add(new Coordinate(myImage.getTranslateX() + myImage.getFitWidth() / 2,
				myImage.getTranslateY() + myImage.getFitHeight() / 2));
		setX(coord.getX());
		setY(coord.getY());
		coord.setX(coord.getX() + myImage.getFitWidth() / 2);
		coord.setY(coord.getY() + myImage.getFitHeight() / 2);
		temp.add(coord);
		this.setFlag(temp);

	}

	/**
	 * readjust for javaFX coord
	 * @param coord - new coord
	 * @return - javaFX adjusted coord
	 */
	private Coordinate setUnadjustedLocation(Coordinate coord) {
		coord.setX(coord.getX() + myWidthBounds / 2.0);
		coord.setY(coord.getY() + myHeightBounds / 2.0);
		return coord;
	}

	/**
	 * Returns location of turtle (either adjusted to javaFX coords (unadjust = false) or to (0,0)
	 * coord at center (unadjust = true)
	 * @param unadjust
	 * @return current coord
	 */
	public Coordinate getLocation(boolean unadjust) {
		Coordinate current = new Coordinate(myImage.getTranslateX(), myImage.getTranslateY());
		if (unadjust) {
			current = unadjust(current);
		}
		return current;
	}

	/**
	 * Get curent coordinate with (0,0) at the center of the screen
	 */
	@Override
	public Coordinate getLocation() {
		return getLocation(true);
	}

	private Coordinate unadjust(Coordinate coord) {
		coord.setX(coord.getX() - myWidthBounds / 2.0);
		coord.setY(coord.getY() - myHeightBounds / 2.0);
		return coord;
	}

	/**
	 * Set heading of turtle (0 - 359 degrees, with 0 being straight up)
	 * @param rotate - degrees to be set at
	 */
	public void setRotate(double rotate) {
		while (rotate >= 360) {
			rotate -= 360;
		}
		myImage.setRotate(rotate);
	}

	/**
	 * Get current turtle heading (0 - 359 degrees, with 0 being straigt up)
	 * return heading
	 */
	public Double getRotate() {
		return myImage.getRotate();
	}

	/**
	 * @return true if turtle image should be shown, false if not
	 */
	public boolean showTurtle() {
		return showTurtle;
	}

	/**
	 * Sets whether turtle image should be shown
	 * @param b - true if show, false if hide
	 */
	public void setShow(boolean b) {
		if (showTurtle != b) {
			this.setFlag(b);
		}
		showTurtle = b;
	}

	/**
	 * @return index of shape ( map of index to shape held in front end )
	 */
	public Double getShape() {
		return myImageIndex;
	}

	/**
	 * Set to clear the UI of pen marking
	 * @param b - true to clear / false to not clear
	 */
	public void setClear(boolean b) {
		myClear = b;
		if (b) {
			penChange();
		}
	}

	private void penChange() {
		this.setFlag(null);
		myClear = false;
	}

	/**
	 * @return turtle's pen object
	 */
	@Override
	public Pen getPen() {
		return myPen;
	}

	/**
	 * @return turtle's ID in turtleManager
	 */
	public Double getID() {
		return myID;
	}

	private void setFlag(Object o) {
		this.setChanged();
		this.notifyObservers(o);
	}
	
	/**
	 * Makes a new copy of the image view and adds it to the view
	 */
	public void stamp() {
		ImageView stamp = new ImageView(myImage.getImage());
		stamp.setRotate(myImage.getRotate());
		stamp.setTranslateX(myImage.getTranslateX());
		stamp.setTranslateY(myImage.getTranslateY());
		stamp.setFitHeight(myImage.getFitHeight());
		stamp.setFitWidth(myImage.getFitWidth());
		myStamp = stamp;
		this.setChanged();
		this.notifyObservers(stamp);
	}
	
	/**
	 * Removes the turtle's stamp
	 */
	public boolean clearStamp() {
		if (myStamp.getImage() == null) {
			return false;
		}
		myStamp.setImage(null);
		this.setChanged();
		this.notifyObservers(myStamp);
		return true;
	}
}
