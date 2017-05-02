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
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

import controller.Controller;
import controller.ControllerAPI;
import coordinate.Coordinate;
import frontend.API.SubcomponentAPI;
import frontend.subcomponents.MethodsView;
import frontend.subcomponents.PaletteView;
import frontend.subcomponents.PenView;
import frontend.subcomponents.StateView;
import frontend.subcomponents.TurtleVisualView;
import frontend.subcomponents.VariablesView;
import frontend.API.ExternalViewAPI;
import frontend.API.InternalViewAPI;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import turtles.ColorPalette;
import turtles.SingleColor;
import turtles.Turtle;
import turtles.TurtleManager;

public class View implements ExternalViewAPI, InternalViewAPI, Observer {
	private static final int HEIGHT = 600;
	private static final int WIDTH = 1000;
	private static final int FRAMES_PER_SECOND = 60;
	private static final int MILLISECOND_DELAY = 10000 / FRAMES_PER_SECOND;
	private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
	private static final String SER_FILEPATH = "src/resources/";
	private static final String DEFAULT_SER = "src/resources/default.ser";
	public static final String RESOURCE_BUNDLE = "resources/Display";
	public static final String CSS_STYLESHEET = "resources/UI.css";

	private Stage stage;
	private Scene scene;
	private GridPane root;
	private Timeline timeline;
	private VBox views;

	private ControllerAPI controller;
	private ResourceBundle resource;
	private TurtleManager turtleManager;
	private OptionsTab optionsTab;

	private TurtleView turtleView;
	private MethodsView methodsView;
	private VariablesView variablesView;
	private PromptView promptView;
	private StateView stateView;
	private PaletteView paletteView;
	private PenView penView;
	private TurtleVisualView turtleVisualView;

	private WorkSpace workSpace;
	private ViewObservable<String> activeViews;
	private Map<String, String> filePath;
	private ObservableList<String> fileName;
	private ColorPalette colorPalette;

	// Constructor
	public View(Stage stageIn, Controller controllerIn) {
		stage = stageIn;
		controller = controllerIn;
		timeline = createTimeline();
		resource = ResourceBundle.getBundle(RESOURCE_BUNDLE);
		this.initializeCore();
		this.getFilePaths();
		this.parseWorkspace(DEFAULT_SER);

		stage.sizeToScene();
		timeline.play();
	}

	// *************************External API
	// Methods******************************
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

	@Override
	public void updateUMethod(String a) {
		methodsView.updateUMethods(a);
	}

	@Override
	public void changeVariable(String a, String b) {
		controller.updateVariable(a, b);
	}

	public void setTurtleManager(TurtleManager tmIn) {
		turtleManager = tmIn;
		turtleManager.addActiveTurtles(workSpace.getTurtles());
		tmIn.addObserver(this);
		for (Turtle t : turtleManager.allTurtles()) {
			turtleView.placeTurtle(t.getImage());
			t.addObserver(this);
		}
		stateView.setTurtleManager(tmIn);
		turtleVisualView.setTurtleManager(tmIn);
		turtleManager.setPalette(colorPalette);
	}

	public void update(Observable arg0, Object arg1) {
		if (arg0 instanceof ViewObservable) {
			updateView((String) arg1);
			workSpace.setViews(activeViews.getList());
		}
		if (arg0 instanceof TurtleManager) {
			if (arg1 == null) {
				turtleVisualView.updateActive();
			} else if (arg1 instanceof Turtle) {
				Turtle t = (Turtle) arg1;
				turtleView.placeTurtle(t.getImage());
				t.addObserver(this);
				stateView.updateStatus(t.getID());
			}
		}
		if (arg0 instanceof Turtle) {
			Turtle t = (Turtle) arg0;
			if (arg1 == null) {
				this.clearLines();
			} else if (arg1 instanceof ArrayList<?>) {
				ArrayList<Coordinate> temp = (ArrayList<Coordinate>) arg1;
				turtleView.changePosition(temp.get(0), temp.get(1), t.getPen(), t);
				stateView.updateStatus(t.getID());
			} else if (arg1 instanceof Boolean) {
				boolean b = (boolean) arg1;
				if (b) {
					this.addTurtle(t.getImage());
				} else {
					this.removeTurtle(t.getImage());
				}
			} else if (arg1 instanceof Double) {
				double d = (double) arg1;
				Image temp = paletteView.getImageOf(d);
				if (temp != null) {
					t.setImage(temp);
				}
			} else if (arg1 instanceof ImageView) {
				ImageView stamp = (ImageView) arg1;
				if (stamp.getImage() == null) {
					turtleView.removeTurtle(stamp);
				} else {
					turtleView.placeTurtle((ImageView) arg1);
				}
			}
		}
		if (arg0 instanceof ColorPalette) {
			ColorPalette cp = (ColorPalette) arg0;
			if (arg1 == null) {
				turtleView.setBackgroundColor(cp.getBackgroundColor());
			}
			if (arg1 instanceof SingleColor) {
				SingleColor temp = (SingleColor) arg1;
				turtleView.updateColor(temp.getIndex(), temp.getColor());
				paletteView.updateColorPalette(temp.getColor(), temp.getIndex());
			}
		}
	}

	// *****************************Internal API********************************
	@Override
	public void runCommand(String a) {
		controller.handleInput(a);
	}

	public void addTurtle(Node n) {
		turtleView.placeTurtle(n);
	}

	public void removeTurtle(Node n) {
		turtleView.removeTurtle(n);
	}

	public boolean containsTurtle(Node n) {
		return turtleView.containsTurtle(n);
	}

	@Override
	public void changeBackground(Double a) {
		turtleView.setBackgroundColor(a);
	}

	@Override
	public void changeImage(Image a) {
		for (double d : turtleManager.getActiveTurtleIDs()) {
			turtleManager.setImage(a, d);
		}
	}

	public void changePenColor(double color) {
		if (colorPalette.checkValid(color)) {
			for (double d : turtleManager.getActiveTurtleIDs()) {
				turtleManager.setPenColor(color, d);
			}
		}
	}

	public void setPenSize(double size) {
		for (double d : turtleManager.getActiveTurtleIDs()) {
			turtleManager.setPenSize(size, d);
		}
	}

	public void setPenState(boolean b) {
		for (double d : turtleManager.getActiveTurtleIDs()) {
			turtleManager.setPenState(b, d);
		}
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
		workSpace.setLanguage(a);
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

	public TurtleManager getTurtleManager() {
		return turtleManager;
	}

	public void setDefaultWorkspace() {
		saveWorkspace(resource.getString("Default"));
	}

	public void deleteWorkspace(String s) {
		String fp = filePath.get(s);
		fileName.remove(s);
		filePath.remove(s);
		File file = new File(fp);
		file.delete();
	}

	public void saveWorkspace(String s) {
		workSpace.setTurtles(turtleManager.getAllTurtleIDs());
		workSpace.setBackground(turtleView.getBackgroundColor());
		controller.saveWorkspace(s, filePath, fileName, resource, workSpace);
	}

	public void loadWorkspace(String s) {
		views.getChildren().clear();
		parseWorkspace(filePath.get(s));
	}

	public void newWorkspace() throws Exception {
		new Controller(new Stage());
	}

	// *********************Private Methods***********************

	/**
	 * Create the timeline for animations to run off of
	 * 
	 * @return
	 */
	private Timeline createTimeline() {
		Timeline ret = new Timeline();
		KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(SECOND_DELAY));
		ret.setCycleCount(Animation.INDEFINITE);
		ret.getKeyFrames().add(frame);
		return ret;
	}

	/**
	 * executes on each frame
	 * 
	 * @param dt
	 *            Change in time
	 */
	private void step(double dt) {
		controller.runCommand();
	}

	/**
	 * Creates the skeleton UI of the workspace
	 */
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
		root.add(scrollPane, 0, 1, 1, 1);
		root.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
		scene.getStylesheets().add(CSS_STYLESHEET);
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * Find all workspace files saved
	 */
	private void getFilePaths() {
		fileName = FXCollections.observableArrayList();
		filePath = new HashMap<String, String>();
		File dir = new File(SER_FILEPATH);
		for (File file : dir.listFiles()) {
			if (file.getName().endsWith(".ser")) {
				String tempName = file.getName().replaceAll(".ser", "");
				fileName.add(tempName);
				filePath.put(tempName, file.getPath());
			}
		}
	}

	/**
	 * Parse the workspace save file
	 * 
	 * @param fp
	 *            filepath of the workspace save file
	 */
	private void parseWorkspace(String fp) {
		try {
			FileInputStream fileIn = new FileInputStream(fp);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			workSpace = (WorkSpace) in.readObject();
			in.close();
			fileIn.close();
			updateWorkspace();
			updateVariablesAndMethods();
		} catch (IOException i) {
			if (!fp.equals(DEFAULT_SER)) {
				showError(resource.getString("FileNotFound"));
			}
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

	/**
	 * default workspace not found, create new default file from bundle
	 */
	private void makeNewDefault() {
		workSpace = new WorkSpace();
		workSpace.setLanguage(resource.getString("DefaultLanguage"));
		workSpace.setBackground(Double.parseDouble(resource.getString("DefaultBackground")));
		workSpace.setViews(new ArrayList<String>(Arrays.asList(resource.getString("DefaultViews").split(","))));
		workSpace.setColorPalette(createMap("defaultColors"));
		workSpace.setImagePalette(createMap("defaultImages"));
		String[] temp = resource.getString("DefaultTurtles").split(",");
		workSpace.setTurtles(new ArrayList<Double>());

		for (String s : temp) {
			workSpace.getTurtles().add(Double.parseDouble(s));
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
			getFilePaths();
			updateWorkspace();
		} catch (IOException i) {
			showError(resource.getString("SavingError"));
		}
	}

	/**
	 * Create a map for default values to be saved in workspace
	 * 
	 * @param keysAndValues
	 *            Single string of keys and values as: key,value,key,value
	 * @return map of double to string
	 */
	private Map<Double, String> createMap(String keysAndValues) {
		Map<Double, String> map = new HashMap<Double, String>();
		String[] defaults = (String[]) (Arrays.asList(resource.getString(keysAndValues).split(";"))).toArray();
		for (String defaultChoice : defaults) {
			String[] tempChoice = (String[]) (Arrays.asList(defaultChoice.split(","))).toArray();
			map.put(Double.parseDouble(tempChoice[1]), tempChoice[0]);
		}
		return map;
	}

	/**
	 * Display views in UI
	 * 
	 * @param myViews
	 *            Views to be displayed in the UI
	 */
	private void showInitialViews(List<String> myViews) {
		for (String s : myViews) {
			updateView(s);
		}
	}

	/**
	 * Add or remove parents of subcomponents to UI
	 * 
	 * @param viewName
	 *            the subcomponent to add or remove
	 */
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
		optionsTab = new OptionsTab(this, fileName, activeViews);
		promptView = new PromptView(this);
		turtleView = new TurtleView(this, colorPalette.getPalette(), workSpace.getBackground());
		methodsView = new MethodsView(this);
		variablesView = new VariablesView(this);
		stateView = new StateView(this);
		paletteView = new PaletteView(this, colorPalette.getPalette(), workSpace.getImagePalette());
		penView = new PenView(this);

		turtleVisualView = new TurtleVisualView(this, workSpace.getBackground());

		root.add(optionsTab.getParent(), 0, 0, 3, 1);
		root.add(turtleView.getParent(), 1, 1, 1, 1);
		root.add(promptView.getParent(), 2, 1, 1, 1);

		if (turtleManager != null) {
			stateView.setTurtleManager(turtleManager);
			turtleVisualView.setTurtleManager(turtleManager);
			turtleManager.addActiveTurtles(workSpace.getTurtles());
		}
	}

	private void updateWorkspace() {
		// TODO finish updates i.e. background
		activeViews = new ViewObservable<String>(workSpace.getViews());
		activeViews.addObserver(this);
		colorPalette = new ColorPalette(workSpace.getColorPalette());
		colorPalette.addObserver(this);
		if (turtleManager != null) {
			turtleManager.reset();
			turtleManager.setPalette(colorPalette);
		}
		this.initializeViews();
		this.showInitialViews(workSpace.getViews());
		changeLanguage(workSpace.getLanguage());
	}

	/**
	 * Have backend load libraries of variables and methods from workspace
	 */
	private void updateVariablesAndMethods() {
		controller.loadVariablesandMethods(workSpace);
	}
	/**
	 * Allow user to move the turtle through the UI
	 * 
	 * @param code
	 *            key pressed
	 * @return
	 */
	private Object handleKeyInput(KeyCode code) {
		if (code == KeyCode.L) {
			controller.handleInput("left 5");
		}
		if (code == KeyCode.R) {
			controller.handleInput("right 5");
		}
		if (code == KeyCode.F) {
			controller.handleInput("forward 5");
		}
		if (code == KeyCode.B) {
			controller.handleInput("back 5");
		}
		return null;
	}

}