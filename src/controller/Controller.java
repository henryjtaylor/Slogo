package controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Map;
import java.util.ResourceBundle;

import coordinate.Coordinate;
import turtles.TurtleManager;
import backend.Model;
import backend.VariableManager;
import backend.API.ModelAPI;
import backend.UserMethodManager;
import frontend.View;
import frontend.WorkSpace;
import frontend.API.ExternalViewAPI;

import javafx.collections.ObservableList;
import javafx.stage.Stage;

/**
 * This class provides the concrete implementation of the ControllerAPI.
 * @author Yanbo Fang
 *
 */
public class Controller implements ControllerAPI {

	private static final String SER_FILEPATH = "src/resources/";
	private static final String INITIAL_LANGUAGE = "English";
	private ModelAPI model;
	private ExternalViewAPI view;
	private VariableManager variables;
	private VariableManagerObserver variablesObserver;
	private TurtleManager turtle;
	private UserMethodManager userMethods;
	private UserMethodObserver userMethodsObserver;

	public Controller(Stage arg0) throws Exception {
		variables = new VariableManager();
		view = new View(arg0, this);
		turtle = new TurtleManager(new Coordinate(view.getBounds().getX(), view.getBounds().getY()));
		view.setTurtleManager(turtle);
		userMethods = new UserMethodManager();
		changeLanguage(INITIAL_LANGUAGE);
		variablesObserver = new VariableManagerObserver(variables, view);
		addVariableManagerObserver();
		userMethodsObserver = new UserMethodObserver(userMethods, view);
		addUserMethodsObserver();
	}

	public void handleInput(String input) {
		model.handleInput(input);
	}

	public void runCommand() {
		model.getNextPos();
	}

	public void updateVariable(String var, String value) {
		model.updateVariable(var, value);
	}

	public void changeLanguage(String newLanguage) throws Exception {
		String[] newSyntax = new String[] { "resources/languages/" + newLanguage, "resources/languages/Syntax" };
		model = new Model(newSyntax, variables, userMethods, turtle);
	}

	public void saveWorkspace(String s, Map<String, String> filePath, ObservableList<String> fileName,
			ResourceBundle resource, WorkSpace workspace) {
		String fp = SER_FILEPATH + s + ".ser";
		filePath.put(s, fp);
		if (!fileName.contains(s)) {
			fileName.add(s);
		}
		workspace.setVariables(variables.getVariableMap());
		workspace.setUserMethods(userMethods.getMethodMap());
		try {
			File file = new File(fp);
			file.getParentFile().mkdirs();
			file.createNewFile();
			FileOutputStream fileOut = new FileOutputStream(file);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(workspace);
			out.close();
			fileOut.close();
		} catch (IOException i) {
			view.showError(resource.getString("SavingError"));
		}
	}

	private void addVariableManagerObserver() {
		variables.addObserver(variablesObserver);
	}

	private void addUserMethodsObserver() {
		userMethods.addObserver(userMethodsObserver);
	}

	@Override
	public void loadVariablesandMethods(WorkSpace workspace) {
		if (workspace.getVariables() != null && workspace.getVariables().size() != 0) {
			variables.addAll(workspace.getVariables());
		}
		if (workspace.getUserMethods() != null && workspace.getUserMethods().size() != 0) {
			userMethods.addAll(workspace.getUserMethods());
		}
	}

}
