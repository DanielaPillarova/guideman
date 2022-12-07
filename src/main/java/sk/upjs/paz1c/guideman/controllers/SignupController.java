package sk.upjs.paz1c.guideman.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import sk.upjs.paz1c.guideman.JdbcSignupDao;
import sk.upjs.paz1c.guideman.models.UserFxModel;
import sk.upjs.paz1c.guideman.storage.DaoFactory;
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
	
	void closeWelcomeScene() {
		signUpNewMemberButton.getScene().getWindow().hide();
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
	void signUpNewMemberButton(ActionEvent event) throws SQLException {
		CreatingUserController controller = new CreatingUserController();
		signUp(controller);

	}

	@FXML
	void signUp(CreatingUserController controller) throws SQLException {
		System.out.println("Klik, mam ucet");

		Window owner = signUpNewMemberButton.getScene().getWindow();

		// ALERTY
		if (nameTextField.getText().isEmpty()) {
			showAlert(Alert.AlertType.ERROR, owner, "Error!", "Please enter your name");
			return;
		}

		if (surnameTextField.getText().isEmpty()) {
			showAlert(Alert.AlertType.ERROR, owner, "Error!", "Please enter your surname");
			return;
		}
		if (emailTextField.getText().isEmpty()) {
			showAlert(Alert.AlertType.ERROR, owner, "Error!", "Please enter your email");
			return;
		}
		if (birthdateTextField.getText().isEmpty()) {
			showAlert(Alert.AlertType.ERROR, owner, "Error!", "Please enter your birthdate");
			return;
		}
		if (usernameTextField.getText().isEmpty()) {
			showAlert(Alert.AlertType.ERROR, owner, "Error!", "Please enter a username");
			return;
		}
		if (passwordPasswordField.getText().isEmpty()) {
			showAlert(Alert.AlertType.ERROR, owner, "Error!", "Please enter a password");
			return;
		}

		String name = nameTextField.getText();
		String surname = surnameTextField.getText();
		String email = emailTextField.getText();
		String tel_number = null;
		if (telNumberTextField.getText() != null) {
			tel_number = telNumberTextField.getText();
		}
		String birthdate = birthdateTextField.getText();
		String username = usernameTextField.getText();
		String password = passwordPasswordField.getText();
		
//		User user = DaoFactory.INSTANCE.getUserDao().getUserByUsername(username);
//		
//		if (user != null) {
//			showAlert(Alert.AlertType.ERROR, owner, "Error!", "Username already exists !");
//			return;
//		}

		JdbcSignupDao jdbcDao = new JdbcSignupDao();

		jdbcDao.insertRecord(name, surname, email, tel_number, birthdate, username, password);

		// TU CHCEM NORMALNE OKNO ZE SOM SIGNUP SUCCESSFULLY

		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("signUpHasBeenSuccessful.fxml"));
			fxmlLoader.setController(controller);
			Parent parent = fxmlLoader.load();
			Scene scene = new Scene(parent);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.setTitle("Congratulations");
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// showAlert(Alert.AlertType.CONFIRMATION, owner, "Registration Successful!",
	// "Welcome " + name + surname);

	// usernameTextField.getScene().getWindow().hide();

	private static void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.initOwner(owner);
		alert.show();
	}

}
