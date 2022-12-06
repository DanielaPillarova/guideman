package sk.upjs.paz1c.guideman;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.security.crypto.bcrypt.BCrypt;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import sk.upjs.paz1c.guideman.storage.DaoFactory;
import sk.upjs.paz1c.guideman.storage.User;

public class LoginController {

	boolean hideWindow = false;

	private User currentUser;

	@FXML
	private TextField usernameTextField;

	@FXML
	private PasswordField passwordPasswordField;

	@FXML
	private Button logInButton;

	@FXML
	private CheckBox showPasswordCheckBox;

	private Button button;

	public LoginController(Button button) {
		this.button = button;
	}

	@FXML
	void initialize() {
		// ide iba na jeden field, idk ako to zmenit na dva
		logInButton.disableProperty().bind(Bindings.isEmpty(usernameTextField.textProperty())
				.and(Bindings.isEmpty(passwordPasswordField.textProperty())));
	}

	// button na LOGIN ked uz sa prihlasujes

	@FXML
	void logInButtonClick(ActionEvent event) throws SQLException {
		IntoAppController controller = new IntoAppController();
		logIntoApp(controller);
	}

	@FXML
	void logIntoApp(IntoAppController controller) throws SQLException {

		Window owner = logInButton.getScene().getWindow();

		if (usernameTextField.getText().isEmpty()) {
			showAlert(Alert.AlertType.ERROR, owner, "Error", "Please enter username");
			return;
		}

		if (passwordPasswordField.getText().isEmpty()) {
			showAlert(Alert.AlertType.ERROR, owner, "Error", "Please enter password");
			return;
		}

		String login = usernameTextField.getText();
		String password = passwordPasswordField.getText();

		currentUser = DaoFactory.INSTANCE.getUserDao().getUserByUsername(login);
		// || BCrypt.checkpw(password, currentUser.getPassword())
		if (currentUser == null || !(password.equals(currentUser.getPassword()))) {
			infoBox("Please enter correct Login and Password", null, "Failed login");
		} else {
			infoBox("Login Successful!", null, "Successful login");
			hideWindow = true;

			if (hideWindow = true) {
				usernameTextField.getScene().getWindow().hide();
				System.out.println(usernameTextField.getParent());
				button.getScene().getWindow().hide();
				// treba zavriet hlavne okno
			}

			try {
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("mainScene.fxml"));
				fxmlLoader.setController(controller);
				Parent parent = fxmlLoader.load();
				Scene scene = new Scene(parent);
				Stage stage = new Stage();
				stage.setScene(scene);

				stage.setTitle("Main menu");
				// stage.initModality(Modality.APPLICATION_MODAL);
				stage.showAndWait();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void infoBox(String infoMessage, String headerText, String title) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setContentText(infoMessage);
		alert.setTitle(title);
		alert.setHeaderText(headerText);
		alert.showAndWait();
	}

	private static void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.initOwner(owner);
		alert.show();
	}

}
