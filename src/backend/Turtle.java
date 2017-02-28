package backend;

import java.util.ArrayList;
import java.util.Observable;

import coordinate.Coordinate;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Turtle extends Observable{
	
	private final String myTurtlePicture = "images/turtle.png";
	
	private ImageView myImage;
	private double myHeightBounds;
	private double myWidthBounds;
	private double myFutureRotate;
	private Coordinate myFutureLocation;
	private boolean myPen;
	private boolean showTurtle;
	

	
	public Turtle(double width, double height, double heightBounds, double widthBounds) {
		//myImage = new ImageView();
		Image turtleView = new Image(getClass().getClassLoader().getResourceAsStream(myTurtlePicture));
		myHeightBounds = heightBounds;
		myWidthBounds = widthBounds;
		myImage = new ImageView(turtleView);
		myImage.setFitWidth(width);
		myImage.setFitHeight(height);
		myImage.setTranslateX(myHeightBounds/2.0);
		myImage.setTranslateY(myWidthBounds/2.0);
		myFutureRotate = myImage.getRotate();
		myFutureLocation = new Coordinate(0.0, 0.0);
		myPen = true;
		showTurtle = true;
	}
	
	public Node getImage() {
		return myImage;
	}
	
	private void setX(double x) {
		if (x > myWidthBounds) {
			x -= myWidthBounds;
		} else if (x < 0) {
			x += myHeightBounds;
		}
		System.out.println("NEW X VALUE");
		System.out.println(x);
		myImage.setTranslateX(x);
	}
	
	private void setY(double y) {
		if (y > myWidthBounds) {
			y -= myWidthBounds;
		} else if (y < 0) {
			y += myWidthBounds;
		}
		System.out.println("NEW Y VALUE");
		System.out.println(y);
		myImage.setTranslateY(y);
	}
	
	public void setLocation(Coordinate coord, boolean ajusted) {
		if (!ajusted) {
			coord = setUnadjustedLocation(coord);
		}
		ArrayList<Coordinate> temp = new ArrayList<Coordinate>();
		temp.add(getLocation());
		setX(coord.getX());
		setY(coord.getY());
		temp.add(getLocation());
		setChanged();
		notifyObservers(temp);
	}
	
	public void setFutureRotate(double rotate) {
		myFutureRotate = rotate;
	}
	
	public Double getFutureRotate() {
		return myFutureRotate;
	}
	
	private Coordinate setUnadjustedLocation(Coordinate coord) {
		coord.setX(coord.getX()+myWidthBounds/2.0);
		coord.setY(coord.getY()+myHeightBounds/2.0);
		return coord;
	}
	
	public Coordinate getFutureLocation() {
		System.out.println("FUTURE LOCATION");
		System.out.println(myFutureLocation.getX());
		System.out.println(myFutureLocation.getY());
		return myFutureLocation;
	}
	
	public void setFutureLocation(Coordinate newFuture, boolean adjusted) {
		myFutureLocation = newFuture;
	}
	
	public Coordinate getLocation() {
		return new Coordinate(myImage.getTranslateX() + myHeightBounds/2.0,
				myImage.getTranslateY() + myWidthBounds/2.0);
	}
	
	public void setRotate(double rotate) {
		myImage.setRotate(rotate);
	}
	
	public Double getRotate() {
		return myImage.getRotate();
	}
	
	public boolean showTurtle() {
		return showTurtle;
	}
	
	public boolean showPen() {
		return myPen;
	}
	
	public Coordinate getHome() {
		return new Coordinate(myWidthBounds/2.0, myHeightBounds/2.0);
	}
	
}
