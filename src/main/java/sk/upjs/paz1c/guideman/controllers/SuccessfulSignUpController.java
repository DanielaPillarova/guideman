package sk.upjs.paz1c.guideman.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

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
		okButton.getScene().getWindow().hide();
	}

	@FXML
	void initialize() {
	}
}
