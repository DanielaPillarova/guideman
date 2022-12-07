package sk.upjs.paz1c.guideman.controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class SuccessfulSignUpController {

	@FXML
	private Button okButton;

	private Button button;

	public SuccessfulSignUpController(Button button) {
		this.button = button;
	}

	@FXML
	void signUpHasBeenSuccessfulButton(ActionEvent event) {
		CreatingUserController controller = new CreatingUserController();
		closeWindow(controller);
	}

	void closeWindow(CreatingUserController controller) {
		button.getScene().getWindow().hide();
		okButton.getScene().getWindow().hide();
	}

	@FXML
	void initialize() {

	}
}
