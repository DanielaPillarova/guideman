package sk.upjs.paz1c.guideman;

import java.util.List;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import sk.upjs.paz1c.guideman.storage.DaoFactory;
import sk.upjs.paz1c.guideman.storage.Location;
import sk.upjs.paz1c.guideman.storage.LocationDao;
import sk.upjs.paz1c.guideman.storage.User;
import sk.upjs.paz1c.guideman.storage.UserDao;

public class App extends Application {

	public static void main(String[] args) {
		// Users

//		UserDao userDao = DaoFactory.INSTANCE.getUserDao();
//		List<User> users = userDao.getAll();
//		System.out.println(users);
//		System.out.println("halo");

		// Location

//		LocationDao locationDao = DaoFactory.INSTANCE.getLocationDao();
//		List<Location> locations = locationDao.getAll();
//		System.out.println(locations);
//		System.out.println("lok�cie sheeeesh");

		launch(args);

	}

	@Override
	public void start(Stage stage) throws Exception {
		String iconImagePath = "C:\\Users\\Roman Rapco\\git\\guideman\\src\\main\\resources\\sk\\upjs\\paz1c\\guideman\\arthas2.jpg";
		LogInSignUpSceneController controller = new LogInSignUpSceneController();
		FXMLLoader loader = new FXMLLoader(App.class.getResource("logInSignUp.fxml"));
		loader.setController(controller);
		Parent parent = loader.load();
		Scene scene = new Scene(parent);
		stage.setScene(scene);
		stage.setTitle("Guideman - find your man");
		stage.getIcons().add(new Image(iconImagePath));
		stage.show();
	}
}
