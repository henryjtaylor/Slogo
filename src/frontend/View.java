package frontend;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

import controller.Controller;
import coordinate.Coordinate;
import frontend.API.SubcomponentAPI;
import frontend.API.ViewAPI;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class View implements ViewAPI, Observer {
	private static final int HEIGHT = 600;
	private static final int WIDTH = 1000;
	private static final int FRAMES_PER_SECOND = 60;
	private static final int MILLISECOND_DELAY = 10000 / FRAMES_PER_SECOND;
	private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
	private static final String DEFAULT_SER = "src/resources/default.ser";
	public static final String RESOURCE_BUNDLE = "resources/Display";
	public static final String CSS_STYLESHEET = "resources/UI.css";

	private Stage stage;
	private Scene scene;
	private GridPane root;
	private Controller controller;
	private Timeline timeline;
	private ResourceBundle resource;
	private WorkSpace workSpace;

	private OptionsTab optionsTab;
	private TurtleView turtleView;
	private MethodsView methodsView;
	private OptionsView optionsView;
	private VariablesView variablesView;
	private PromptView promptView;
	private StateView stateView;

	private VBox views;

	private boolean penDown;

	public View(Stage stageIn, Controller controllerIn) {
		stage = stageIn;
		resource = ResourceBundle.getBundle(RESOURCE_BUNDLE);
		this.parseWorkspace(DEFAULT_SER);
		controller = controllerIn;
		timeline = createTimeline();

		this.initializeViews();
		this.initializeCore();
		this.showInitialViews(workSpace.views);
		penDown = true;
		timeline.play();
	}

	@Override
	public Coordinate getBounds() {
		return turtleView.getBounds();
	}

	@Override
	public void updateVar(String a, String b) {
		variablesView.updateVar(a, b);
	}

	@Override
	public void showError(String a) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText("Error Found");
		alert.setContentText(a);
		alert.showAndWait();
	}

	public void setTurtle(Node node) {
		turtleView.placeTurtle(node);
	}

	public void updateTurtle(Coordinate oldC, Coordinate newC) {
		if (penDown) {
			turtleView.changePosition(oldC, newC);
		}
	}

	@Override
	public void updateUMethod(String a) {
		methodsView.updateUMethods(a);
	}

	@Override
	public void changeVariable(String a, String b) {
		controller.updateVariable(a, b);
	}

	@Override
	public void useUMethod(String a) {
		controller.handleInput(a);
	}

	@Override
	public void changeBackground(String a) {
		turtleView.setBackgroundColor(a);
	}

	@Override
	public void changeImage(Image a) {
		controller.changeImage(a);
	}

	@Override
	public void changePenColor(String a) {
		turtleView.setPenColor(a);
	}

	@Override
	public void changeLanguage(String a) {
		try {
			controller.changeLanguage(a);
		} catch (Exception e) {
			this.showError(resource.getString("WrongLanguage"));
		}
		this.clearLines();
		this.clearVariables();
		this.clearMethods();
		this.clearHistory();
	}

	@Override
	public void runCommand(String a) {
		controller.handleInput(a);
	}

	public void clearVariables() {
		variablesView.clearVars();
	}

	public void clearMethods() {
		methodsView.clearMethods();
	}

	public void clearHistory() {
		promptView.clearHistory();
	}

	public void clearLines() {
		turtleView.clear();
	}

	@Override
	public void setPen(Boolean penIn) {
		penDown = penIn;
	}
	
	public void newWorkSpace() throws Exception{
		new Controller(new Stage());
	}

	private void step(double dt) {
		controller.runCommand();
	}

	private Timeline createTimeline() {
		Timeline ret = new Timeline();
		KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(SECOND_DELAY));
		ret.setCycleCount(Animation.INDEFINITE);
		ret.getKeyFrames().add(frame);
		return ret;
	}

	private void parseWorkspace(String fp) {
		try {
			FileInputStream fileIn = new FileInputStream(fp);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			workSpace = (WorkSpace) in.readObject();
			in.close();
			fileIn.close();
		} catch (IOException i) {
			showError(resource.getString("FileNotFound"));
			if (fp.equals(DEFAULT_SER)) {
				showError(resource.getString("DefaultMissing"));
				makeNewDefault();
			} else {
				parseWorkspace(DEFAULT_SER);
			}
		} catch (ClassNotFoundException c) {
			showError(resource.getString("ClassNotFound"));
		}
	}

	private void makeNewDefault() {
		workSpace = new WorkSpace();
		workSpace.language = resource.getString("DefaultLanguage");
		workSpace.background = Integer.parseInt(resource.getString("DefaultBackground"));
		workSpace.views = new ArrayList<String>(Arrays.asList(resource.getString("DefaultViews").split(",")));
		workSpace.files = new HashMap<String, String>();
		String[] temp = resource.getString("DefaultFiles").split(",");
		for (int i = 0; i < temp.length; i = i + 2) {
			workSpace.files.put(temp[i], temp[i + 1]);
		}
		try {
			File file = new File(DEFAULT_SER);
			file.getParentFile().mkdirs();
			file.createNewFile();
			FileOutputStream fileOut = new FileOutputStream(file);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(workSpace);
			out.close();
			fileOut.close();
		} catch (IOException i) {
			showError(resource.getString("help"));
			i.printStackTrace();
		}
	}

	private void initializeCore() {
		root = new GridPane();
		scene = new Scene(root, WIDTH, HEIGHT);
		ColumnConstraints column1 = new ColumnConstraints();
		column1.setPercentWidth(25);
		ColumnConstraints column2 = new ColumnConstraints();
		column2.setPercentWidth(50);
		ColumnConstraints column3 = new ColumnConstraints();
		column3.setPercentWidth(25);
		root.getColumnConstraints().addAll(column1, column2, column3);
		RowConstraints row1 = new RowConstraints();
		row1.setPercentHeight(10);
		RowConstraints row2 = new RowConstraints();
		row2.setPercentHeight(90);
		root.getRowConstraints().addAll(row1, row2);

		ScrollPane scrollPane = new ScrollPane();
		views = new VBox();
		scrollPane.setContent(views);
		scrollPane.setFitToWidth(true);

		root.add(optionsTab.getParent(), 0, 0, 3, 1);
		root.add(turtleView.getParent(), 1, 1, 1, 1);
		root.add(promptView.getParent(), 2, 1, 1, 1);
		root.add(scrollPane, 0, 1, 1, 1);

		scene.getStylesheets().add(CSS_STYLESHEET);
		stage.setScene(scene);
		stage.show();
	}
	
	private Object handleKeyInput(KeyCode code) {
		double x = 50;
		double y = 60;
		Coordinate current = new Coordinate(x, y);	
		if (code == KeyCode.L) {
			controller.handleInput("left 5");	
		}
		if (code == KeyCode.R) {
			controller.handleInput("right 5");		
		}
		if (code == KeyCode.F) {
			controller.handleInput("fd 5");		
		}
		if (code == KeyCode.B) {
			controller.handleInput("back 5");		
		}
		return null;
	}

	private void showInitialViews(List<String> myViews) {
		for (String s : myViews) {
			updateView(s);
		}
	}

	private void updateView(String viewName) {
		try {
			Field f = this.getClass().getDeclaredField(viewName);
			try {
				SubcomponentAPI temp = (SubcomponentAPI) f.get(this);
				Parent toAdd = temp.getParent();
				if (views.getChildren().contains(toAdd)) {
					views.getChildren().remove(toAdd);
				} else {
					views.getChildren().add(temp.getParent());
				}
			} catch (IllegalArgumentException e) {
				// Shouldn't happen
				showError("Illegal Argument Exception");
			} catch (IllegalAccessException e) {
				// Shouldn't happen
				showError("Illegal Access Exception");
			}
		} catch (NoSuchFieldException e) {
			showError(resource.getString("BadField") + viewName);
		} catch (SecurityException e) {
			// Shouldn't happen
			showError("Security Exception");
		}
	}

	private void initializeViews() {
		ViewObservable<String> temp = new ViewObservable<String>(workSpace.views);
		temp.addObserver(this);
		turtleView = new TurtleView(this, workSpace.background);
		methodsView = new MethodsView(this);
		optionsView = new OptionsView(this);
		variablesView = new VariablesView(this);
		promptView = new PromptView(this);
		stateView = new StateView(this);
		optionsTab = new OptionsTab(this, workSpace.files, temp);
	}

	public void update(Observable arg0, Object arg1) {
		if (arg0 instanceof ViewObservable) {
			updateView((String) arg1);
		}
	}
	
}
