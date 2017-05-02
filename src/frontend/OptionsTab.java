package frontend;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;

import frontend.API.SubcomponentAPI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.collections.ObservableSet;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 * Class to manage workspace options
 * @authors Gordon and Faith
 *
 */
public class OptionsTab implements SubcomponentAPI {

	private ResourceBundle resource;
	private View view;
	private HBox buttonPanel;
	private ViewSelector viewSelector;

	private Stage s;
	private Scene scene;
	private String url;//= getClass().getClassLoader().getResource("help.html").toExternalForm();
	private WebEngine webEngine;
	
	private ObservableList<String> files;

	// Constructor
	public OptionsTab(View viewIn, ObservableList<String> filesIn, ViewObservable<String> views) {
		view = viewIn;
		files = filesIn;
		resource = ResourceBundle.getBundle(view.RESOURCE_BUNDLE);
		buttonPanel = new HBox();
		WebView browser = new WebView();
		webEngine = browser.getEngine();
		s = new Stage();
		scene = new Scene(browser);

		createNewWorkspaceButton();
		createViewsButton(views);
		createSaveButton();
		createLoadCombo();
		createDeleteCombo();
		createSetButton();
		createLanguageCombo();
		createHelpButton();
	}

	public Parent getParent() {
		return buttonPanel;
	}
	
	/**
	 * Create the Set Default Workspace button
	 */
	private void createSetButton(){
		Button helpBtn = createButton(resource.getString("Set"));
		helpBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				view.setDefaultWorkspace();
			}
		});
	}

	/**
	 * Create the delete workspace combo box
	 */
	private void createDeleteCombo() {
		if(!files.contains(resource.getString("Blank"))){
			files.add(0,resource.getString("Blank"));
		}
		ComboBox deleteCombo = new ComboBox(files);
		deleteCombo.setPromptText(resource.getString("Delete"));
		deleteCombo.setOnAction(e -> deleteWorkspace((String) deleteCombo.getValue()));
		buttonPanel.getChildren().add(deleteCombo);
	}

	/**
	 * Listener method to delete a workspace, can't delete default workspace
	 * @param s
	 * The name of the workspace to be deleted
	 */
	private void deleteWorkspace(String s) {
		if (s!=null&&!s.equals(resource.getString("Blank"))) {
			if (s.equals(resource.getString("Default"))) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle(resource.getString("DeleteWarningTitle"));
				alert.setHeaderText(resource.getString("DeleteWarningHeader"));
				alert.setContentText(resource.getString("DeleteWarningContent"));
				alert.showAndWait();
			} else {
				view.deleteWorkspace(s);
			}
		}
	}

	/**
	 * Create load workspace combo box
	 */
	private void createLoadCombo() {
		ComboBox loadCombo = new ComboBox(files);
		loadCombo.setPromptText(resource.getString("Load"));
		loadCombo.setOnAction(e -> view.loadWorkspace((String) loadCombo.getValue()));
		buttonPanel.getChildren().add(loadCombo);
	}

	/**
	 * Create button to save current workspace to file
	 */
	private void createSaveButton() {
		Button saveButton = createButton(resource.getString("Save"));
		saveButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				TextInputDialog dialog = new TextInputDialog(resource.getString("Name"));
				dialog.setTitle(resource.getString("SaveDialogTitle"));
				dialog.setContentText(resource.getString("SaveDialogContent"));
				Optional<String> output = dialog.showAndWait();
				if (output.isPresent()) {
					view.saveWorkspace(output.get());
				}
			}
		});
	}

	/**
	 * Open a new workspace with default parameters
	 */
	private void createNewWorkspaceButton() {
		Button newWorkSpace = createButton(resource.getString("NewWS"));
		newWorkSpace.setOnAction(e -> {
			try {
				view.newWorkspace();
			} catch (Exception e1) {
				view.showError(resource.getString("WSError"));
			}
		});
	}

	/**
	 * Create button to prompt user to select views to display
	 * @param views
	 * List of strings containing the name of views
	 */
	private void createViewsButton(ViewObservable<String> views) {
		viewSelector = new ViewSelector(views);
		Button openViews = createButton(resource.getString("ViewButton"));
		openViews.setOnAction(e -> viewSelector.run());
	}
	
	/**
	 * Create combo box to select a language for commands to be interpreted with
	 */
	private void createLanguageCombo() {
		ComboBox<String> langCombo = new ComboBox<String>();
		langCombo.setPromptText(resource.getString("lang"));
		langCombo.getItems().addAll(resource.getString("Languages").split(","));
		langCombo.setOnAction(e -> view.changeLanguage(langCombo.getValue()));
		buttonPanel.getChildren().add(langCombo);
	}

	/**
	 * Create a help button to display html formatted help window
	 */
	private void createHelpButton() {
		Button helpBtn = createButton(resource.getString("help"));
		helpBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				webEngine.load(url);
				s.setScene(scene);
				s.show();
			}
		});
	}

	/**
	 * Create a generic button that is automatically added to parent
	 * @param s
	 * text for the button
	 * @return
	 * button
	 */
	private Button createButton(String s) {
		Button ret = new Button(s);
		ret.setId("option");
		buttonPanel.getChildren().add(ret);
		return ret;
	}

}
