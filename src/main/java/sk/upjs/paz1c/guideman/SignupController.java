package sk.upjs.paz1c.guideman;

import java.time.LocalDate;

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

	private UserFxModel userModel;

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
	private Button signUpNewMemberButton;

	@FXML
	private PasswordField passwordPasswordField;

	@FXML
	private CheckBox showPasswordCheckBox;

	public SignupController() {
		userModel = new UserFxModel();
	}

	public SignupController(User user) {
		userModel = new UserFxModel(user);
	}

	@FXML
	void initialize() {
		// vsetko je nato aby sa nedalo kliknut vytvorenie ked nie su fieldy
		nameTextField.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (newValue == null || newValue.isBlank()) {
					signUpNewMemberButton.setDisable(true);
				} else {
					signUpNewMemberButton.setDisable(false);

				}
			}
		});

		surnameTextField.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (newValue == null || newValue.isBlank()) {
					signUpNewMemberButton.setDisable(true);
				} else {
					signUpNewMemberButton.setDisable(false);
				}
			}
		});

		emailTextField.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (newValue == null || newValue.isBlank()) {
					signUpNewMemberButton.setDisable(true);
				} else {
					signUpNewMemberButton.setDisable(false);
				}
			}
		});

		birthdateTextField.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (newValue == null || newValue.isBlank()) {
					signUpNewMemberButton.setDisable(true);
				} else {
					signUpNewMemberButton.setDisable(false);
				}
			}
		});

		usernameTextField.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (newValue == null || newValue.isBlank()) {
					signUpNewMemberButton.setDisable(true);
				} else {
					signUpNewMemberButton.setDisable(false);
				}
			}
		});

		passwordPasswordField.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (newValue == null || newValue.isBlank()) {
					signUpNewMemberButton.setDisable(true);
				} else {
					signUpNewMemberButton.setDisable(false);
				}
			}
		});

	}

	@FXML
	void signUpNewMemberButton(ActionEvent event) {
		CreatingUserController controller = new CreatingUserController();
		signUp(controller);

	}

	@FXML
	void signUp(CreatingUserController controller) {
		System.out.println("Klik, mam ucet");

//		String name = nameTextField.getText().trim();
//		String surname = nameTextField.getText().trim();
//		String email = nameTextField.getText().trim();
//		String tel_number = telNumberTextField.getText().trim();
//		String birthdateString = nameTextField.getText().trim();
//		LocalDate birthdate = LocalDate.parse(birthdateString);
//		String username = nameTextField.getText().trim();
//		String password = nameTextField.getText().trim();
//
//		if (telNumberTextField == null) {
//			User newUser = new User(name, surname, email, birthdate, username, password);
//			userModel.getUsersModel().add(newUser);
//		} else {
//			User newUser = new User(name, surname, email, tel_number, birthdate, username, password);
//			userModel.getUsersModel().add(newUser);
//		}
	}

}
