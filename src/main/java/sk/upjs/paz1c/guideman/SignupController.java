package sk.upjs.paz1c.guideman;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import sk.upjs.paz1c.guideman.storage.User;

public class SignupController {

	private UserFxModel model;

	@FXML
	private TextField nameTextField;

	@FXML
	private TextField surnameTextField;

	@FXML
	private TextField emailTextField;

	@FXML
	private TextField telNumberTextField;

	@FXML
	private TextField birthdateTextField;

	@FXML
	private TextField usernameTextField;

	@FXML
	private Button signupNewMemberButton;

	@FXML
	private PasswordField passwordPasswordField;

	@FXML
	private CheckBox showPasswordCheckBox;

	public SignupController() {
		model = new UserFxModel();
	}

	public SignupController(User user) {
		model = new UserFxModel(user);
	}

	@FXML
	void initialize() {
		nameTextField.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				signupNewMemberButton.setDisable(true);
				if (newValue == null || newValue.isBlank()) {
					signupNewMemberButton.setDisable(true);
				} else {
					signupNewMemberButton.setDisable(false);
				}
			}
		});

		surnameTextField.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (newValue == null || newValue.isBlank()) {
					signupNewMemberButton.setDisable(true);
				} else {
					signupNewMemberButton.setDisable(false);
				}
			}
		});
	}

	@FXML
	void signupNewMemberButton(ActionEvent event) {

	}

}
