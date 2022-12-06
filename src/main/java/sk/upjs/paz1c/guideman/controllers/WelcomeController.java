package sk.upjs.paz1c.guideman.controllers;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.palexdev.materialfx.controls.MFXButton;
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

public class WelcomeController {

	public static final Logger logger = LoggerFactory.getLogger(WelcomeController.class);

	@FXML
	private MFXButton logInButton;

	@FXML
	private MFXButton signUpButton;

	@FXML
	void initialize() {
		logger.debug("inicialize running");
	}

	@FXML
	void loginButtonClick(ActionEvent event) {
		LoginController controller = new LoginController(signUpButton);
		showLogin(controller);
	}

	@FXML
	void signupButtonClick(ActionEvent event) {
		SignupController controller = new SignupController();
		showSignup(controller);
	}

	@FXML
	void showLogin(LoginController controller) {
		System.out.println("Som v logine");

		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("loginGridPane.fxml"));
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

	@FXML
	void showSignup(SignupController controller) {
		System.out.println("Som v signe");

		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("signUp.fxml"));
			fxmlLoader.setController(controller);
			Parent parent = fxmlLoader.load();
			Scene scene = new Scene(parent);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.setTitle("Sign up");
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	void closeWelcomeScene() {
		logInButton.getScene().getWindow().hide();
	}

}
