package controller;

import java.util.Map;
import java.util.ResourceBundle;

import frontend.WorkSpace;
import javafx.collections.ObservableList;

/**
 * API for Controller, to be used by View
 * @author Yanbo Fang
 *
 */
public interface ControllerAPI {
	void handleInput(String input);

	void runCommand();

	void updateVariable(String var, String value);

	void changeLanguage(String newLanguage) throws Exception;

	void saveWorkspace(String s, Map<String, String> filePath, ObservableList<String> fileName, ResourceBundle resource,
			WorkSpace workspace);

	void loadVariablesandMethods(WorkSpace workspace);
}
