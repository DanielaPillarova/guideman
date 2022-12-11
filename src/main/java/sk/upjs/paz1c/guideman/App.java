package sk.upjs.paz1c.guideman;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sk.upjs.paz1c.guideman.controllers.WelcomeController;

public class App extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		// String iconImagePath =
		// "C:\\Users\\RomanRapco\\git\\guideman\\src\\main\\resources\\sk\\upjs\\paz1c\\guideman\\arthas2.jpg";
		WelcomeController controller = new WelcomeController();
		FXMLLoader loader = new FXMLLoader(App.class.getResource("logInSignUp.fxml"));
		loader.setController(controller);
		Parent parent = loader.load();
		Scene scene = new Scene(parent);
		stage.setScene(scene);
		stage.setTitle("Become or find Guideman");
		// stage.getIcons().add(new Image(iconImagePath));
		stage.show();
	}
}
