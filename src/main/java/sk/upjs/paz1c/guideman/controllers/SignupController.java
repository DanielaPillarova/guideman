package sk.upjs.paz1c.guideman.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;

import javax.sql.rowset.serial.SerialBlob;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.StringUtils;

import ch.qos.logback.core.recovery.ResilientSyslogOutputStream;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import sk.upjs.paz1c.guideman.models.UserFxModel;
import sk.upjs.paz1c.guideman.storage.DaoFactory;
import sk.upjs.paz1c.guideman.storage.User;
import sk.upjs.paz1c.guideman.storage.UserDao;

public class SignupController {

	private UserFxModel userModel;
	private User savedUser;
	private UserDao userDao;
	private Window owner;
	private File selectedFile;
	private String filePath;
	private String nameOfFile;
	private byte[] bytes; // obrazok v bytoch

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
	private PasswordField passwordPasswordField;

	@FXML
	private Button signUpNewMemberButton;

	@FXML
	private Button selectImageButton;

	@FXML
	private Label selectedFileLabel;

	public SignupController() {
		userModel = new UserFxModel();
		userDao = DaoFactory.INSTANCE.getUserDao();

	}

	public SignupController(User user) {
		userModel = new UserFxModel(user);
	}

	@FXML
	void initialize() {

		BooleanBinding bb = new BooleanBinding() {
			{
				super.bind(nameTextField.textProperty(), surnameTextField.textProperty(), emailTextField.textProperty(),
						birthdateTextField.textProperty(), usernameTextField.textProperty(),
						passwordPasswordField.textProperty());
			}

			@Override
			protected boolean computeValue() {
				return (nameTextField.getText().isEmpty() || surnameTextField.getText().isEmpty()
						|| emailTextField.getText().isEmpty() || birthdateTextField.getText().isEmpty()
						|| usernameTextField.getText().isEmpty() || passwordPasswordField.getText().isEmpty());
			}
		};

		signUpNewMemberButton.disableProperty().bind(bb);

	}

//	public static String parseDatum(String datum) {
//
//		String[] pole = datum.split("\\.");
//		int first = Integer.valueOf(pole[0]);
//		int second = Integer.valueOf(pole[1]);
//		int third = Integer.valueOf(pole[2]);
//
//		int vek = first + second * 31 + (third * 365);
//
//		if (!(first >= 1 && first <= 31)) {
//			infoBox("Try using date format -> DD.MM.YYYY", null, "Wrong date format");
//			return null;
//		}
//
//		if (!(second >= 1 && second <= 12)) {
//			infoBox("Try using date format -> DD.MM.YYYY", null, "Wrong date format");
//			// showAlert(Alert.AlertType.WARNING, owner, "Error!", "Please enter your
//			// name");
//			return null;
//		}
//
//		if (693500 < vek && vek < 732190) {
//			String newDate = "";
//			newDate = newDate + pole[2] + "-" + pole[1] + "-" + pole[0];
//			return newDate;
//		}
//
//		return null;
//
//	}

	@FXML
	void signUpNewMemberButton(ActionEvent event) throws SQLException, IOException {
		SuccessfulSignUpController controller = new SuccessfulSignUpController();
		signUp(controller);

	}

	// SIGN UP BUTTON
	@FXML
	void signUp(SuccessfulSignUpController controller) throws SQLException, IOException {
		owner = signUpNewMemberButton.getScene().getWindow();

		// ALERTY
		if (nameTextField.getText().isEmpty()) {
			showAlert(Alert.AlertType.WARNING, owner, "Error!", "Please enter your name");
			return;
		}

		if (surnameTextField.getText().isEmpty()) {
			showAlert(Alert.AlertType.WARNING, owner, "Error!", "Please enter your surname");
			return;
		}
		if (emailTextField.getText().isEmpty()) {
			showAlert(Alert.AlertType.WARNING, owner, "Error!", "Please enter your email");
			return;
		}
		if (birthdateTextField.getText().isEmpty()) {
			showAlert(Alert.AlertType.WARNING, owner, "Error!", "Please enter your birthdate");
			return;
		}
		if (usernameTextField.getText().isEmpty()) {
			showAlert(Alert.AlertType.WARNING, owner, "Error!", "Please enter a username");
			return;
		}
		if (passwordPasswordField.getText().isEmpty()) {
			showAlert(Alert.AlertType.WARNING, owner, "Error!", "Please enter a password");
			return;
		}

		String name = nameTextField.getText();
		String surname = surnameTextField.getText();
		String email = emailTextField.getText();
		String tel_number = telNumberTextField.getText();

		// parse tento birthdate
		String birthdate = birthdateTextField.getText();
		String username = usernameTextField.getText();
		String password = passwordPasswordField.getText();

		birthdate = birthdate.trim();

		String[] pole = birthdate.split("\\.");
		int first = Integer.valueOf(pole[0]);
		int second = Integer.valueOf(pole[1]);
		int third = Integer.valueOf(pole[2]);

		int vek = first + second * 31 + (third * 365);

		if (!(first >= 1 && first <= 31)) {
			showAlert(Alert.AlertType.WARNING, owner, "Warning!", "Day needs to be between 1 and 31");
			return;
		}

		if (!(second >= 1 && second <= 12)) {
			showAlert(Alert.AlertType.WARNING, owner, "Warning!", "Month needs to be between 1 and 12");
			return;
		}

		if (!(third <= 2006)) {
			showAlert(Alert.AlertType.WARNING, owner, "Warning!", "You need to be at least 16 years old");
			return;
		}

		if (!(third >= 1900 && third <= 2006)) {
			showAlert(Alert.AlertType.WARNING, owner, "Warning!", "Year needs to be between 1900 and 2006");
			return;
		}

		String newDate = "";

		if (693500 < vek && vek < 732190) {
			newDate = newDate + pole[2] + "-" + pole[1] + "-" + pole[0];
		}

		LocalDate birthdateParsed = null;
		if (!(newDate.equals(""))) {
			try {
				birthdateParsed = LocalDate.parse(newDate);
			} catch (Exception e) {
				showAlert(Alert.AlertType.WARNING, owner, "Wrong date format!", "Try using date format -> DD.MM.YYYY");
				return;
			}
			System.out.println(birthdateParsed);
		}

		User user = null;

		Blob blobisko = null;
		if (bytes != null) {
			blobisko = new SerialBlob(bytes);
		}

		if (tel_number != "") {
			user = new User(name, surname, email, tel_number, birthdateParsed, username, password, blobisko);
		} else {
			user = new User(name, surname, email, null, birthdateParsed, username, password, blobisko);
		}

		int sizeBe4 = userDao.getAll().size();

		try {
			savedUser = userDao.save(user);
		} catch (DuplicateKeyException e) {
			showAlert(Alert.AlertType.WARNING, owner, "Warning!",
					"Username already exists ! \nPlease enter different username !");
		}

		int sizeAfter = userDao.getAll().size();
		if (sizeBe4 + 1 == sizeAfter) {
			System.out.println("new user has been made");
			infoBox("Sign up has been successful !", null, "Successful sign up");
			signUpNewMemberButton.getScene().getWindow().hide();

			// RIP moj mrtvy button

		}
	}

	// IMAGE V SIGN UP-E

	@FXML
	void selectImageButtonClick(ActionEvent event) throws IOException {
		// sranie sa fr fr

		bytes = null;
		selectedFileLabel.setText("No selected file");

		if (event.getSource() == selectImageButton) {

			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
			FileNameExtensionFilter filter = new FileNameExtensionFilter("*.image", "jpg", "png");
			fileChooser.addChoosableFileFilter(filter);

			int response = fileChooser.showSaveDialog(null); // select file to open

			if (response == JFileChooser.APPROVE_OPTION) {
				selectedFile = new File(fileChooser.getSelectedFile().getAbsolutePath()); // File , jeho cesta
				filePath = selectedFile.getAbsolutePath(); // string
				if (filePath.endsWith(".jpg") || filePath.endsWith(".JPG") || filePath.endsWith(".PNG")
						|| filePath.endsWith(".png")) {
					System.out.println(filePath);
					bytes = Files.readAllBytes(Paths.get(filePath));
					nameOfFile = selectedFile.getName();
					selectedFileLabel.setText(nameOfFile);
				} else {
					infoBox("Please Select Image File", null, "Warning !");
				}
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
