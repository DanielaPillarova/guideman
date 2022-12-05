package sk.upjs.paz1c.guideman;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class LoginSceneController {

	public static final Logger logger = LoggerFactory.getLogger(LoginSceneController.class);

	@FXML
	private ImageView arthas;

	@FXML
	private Button loginButton;

	@FXML
	private Button signupButton;

	public void showImage() {
		try {
			Image image = new Image(
					"C:\\Users\\Roman Rapco\\git\\guideman\\src\\main\\resources\\sk\\upjs\\paz1c\\guideman\\arthas3.png");
			arthas.setImage(image);
			arthas.setCache(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	void initialize() {
		logger.debug("inicialize running");
	}

	@FXML
	void loginButtonClick(ActionEvent event) {
		LoginController controller = new LoginController();
		showLogin(controller);
	}

	@FXML
	void signupButtonClick(ActionEvent event) {

	}

	void showLogin(LoginController controller) {
		System.out.println("Som v logine");

		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("logIn.fxml"));
			fxmlLoader.setController(controller);
			Parent parent = fxmlLoader.load();
			Scene scene = new Scene(parent);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.setTitle("Login");
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
