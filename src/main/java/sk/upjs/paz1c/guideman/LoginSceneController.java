package sk.upjs.paz1c.guideman;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class LoginSceneController {

	public static final Logger logger = LoggerFactory.getLogger(LoginSceneController.class);

	@FXML
	private Button loginButton;

	@FXML
	private Button registerButton;

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
	void registerButtonClick(ActionEvent event) {

	}

	void showLogin(LoginController controller) {
		System.out.println("Som v logine");
		
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(null));
		
	}

}
