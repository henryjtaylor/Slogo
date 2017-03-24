import controller.Controller;
import controller.ControllerAPI;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	public void start(Stage arg0) throws Exception {
		ControllerAPI controller = new Controller(arg0);
	}

	public static void main(String[] args) {
		launch(args);
	}

}