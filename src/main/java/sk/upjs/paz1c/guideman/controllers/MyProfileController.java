package sk.upjs.paz1c.guideman.controllers;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Blob;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.Window;
import sk.upjs.paz1c.guideman.App;
import sk.upjs.paz1c.guideman.storage.User;

public class MyProfileController {

	private User loggedUser;
	private Window owner;

	// obrazok
	private File selectedFile;
	private String filePath;
	private String nameOfFile;
	private byte[] bytes = null; // obrazok v bytoch

	@FXML
	private Button changeImageButton;

	@FXML
	private Label dateOfBirthLabel;

	@FXML
	private TextField dateOfBirthTextField;

	@FXML
	private Button editAndSaveButton;

	@FXML
	private Label emailLabel;

	@FXML
	private TextField emailTextField;

	@FXML
	private ImageView imageImageView;

	@FXML
	private Label myProfileLabel;

	@FXML
	private Label nameLabel;

	@FXML
	private TextField nameTextField;

	@FXML
	private Label phoneNumberLabel;

	@FXML
	private TextField phoneNumberTextField;

	@FXML
	private Button showFavouriteGuidemansButton;

	@FXML
	private Label surnameLabel;

	@FXML
	private TextField surnameTextField;

	@FXML
	void changeImageButtonAction(ActionEvent event) throws IOException, SerialException, SQLException {
		System.out.println("change");

		owner = changeImageButton.getScene().getWindow();
		if (event.getSource() == changeImageButton) {

			JFileChooser fileChooser = new JFileChooser();
			// System.setProperty("apple.awt.fileDialogForDirectories", "true");
			// Danke mac nejde
			// System.getProperty("os.name")
			// https://community.oracle.com/tech/developers/discussion/2508757/jfilechooser-problem-on-mac-os
			fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
			FileNameExtensionFilter filter = new FileNameExtensionFilter("*.image", "jpg", "png");
			fileChooser.addChoosableFileFilter(filter);

			int response = fileChooser.showSaveDialog(null); // select file to open

			if (response == JFileChooser.APPROVE_OPTION) {
				bytes = null;
				selectedFile = new File(fileChooser.getSelectedFile().getAbsolutePath()); // File , jeho cesta
				filePath = selectedFile.getAbsolutePath(); // string
				if (filePath.endsWith(".jpg") || filePath.endsWith(".JPG") || filePath.endsWith(".PNG")
						|| filePath.endsWith(".png")) {
					System.out.println(filePath);
					bytes = Files.readAllBytes(Paths.get(filePath));
					nameOfFile = selectedFile.getName();

				} else {
					infoBox("Please Select Image File", null, "Warning !");
				}
			}

			Blob blobisko = null;
			if (bytes != null) {
				blobisko = new SerialBlob(bytes);
				System.out.println(blobisko.length() + " velkost blobu");

				// velke nez 16 mb
				if (blobisko.length() > 16000000L) {
					System.out.println("Error");
					showAlert(Alert.AlertType.ERROR, owner, "Error", "Please upload smaller image !");
					return;
				}
				Blob usersBlob = blobisko;
				InputStream in = usersBlob.getBinaryStream();
				Image image = new Image(in);

				imageImageView.setImage(image);
			}
		}

	}

	@FXML
	void editAndSaveButtonAction(ActionEvent event) {
		System.out.println("save");

	}

	@FXML
	void showFavouriteGuidemansButtonAction(ActionEvent event) {
		System.out.println("guideman");

	}

	//////////////// menu

	@FXML
	private Button myProfileButton;

	@FXML
	private Button myToursButton;

	@FXML
	private Button searchTourButton;

	@FXML
	private Button createTourButton;

	@FXML
	private Button logOutButton;

	@FXML
	void myProfileButtonAction(ActionEvent event) {
		System.out.println("profile");
	}

	@FXML
	void myToursButtonAction(ActionEvent event) {
		System.out.println("tours");
	}

	@FXML
	void searchTourButtonAction(ActionEvent event) {
		System.out.println("search");
	}

	@FXML
	void createTourButtonAction(ActionEvent event) {
		System.out.println("create");
	}

	@FXML
	void logOutButtonAction(ActionEvent event) {
		System.out.println("logout");

//		if (event.getSource() == logOutButton) {

//		}

		logOutButton.getScene().getWindow().hide();

		try {
			FXMLLoader fxmlLoader = new FXMLLoader(WelcomeController.class.getResource("logInSignUp.fxml"));
			Stage stage = new Stage();
			fxmlLoader.setController(new WelcomeController());
			Scene scene = new Scene(fxmlLoader.load());
			stage.setTitle("Guideman");
			stage.setScene(scene);
//        stage.getIcons().add(new Image("sk/upjs/favicon.png")); // danko tak ma icon
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	///////////////

	@FXML
	void initialize() throws SQLException {
		loggedUser = LoggedUser.INSTANCE.getLoggedUser();
		System.out.println(loggedUser);

		nameTextField.setText(loggedUser.getName());
		surnameTextField.setText(loggedUser.getSurname());
		emailTextField.setText(loggedUser.getEmail());
//		if (loggedUser.getTelNumber())
		phoneNumberTextField.setText(loggedUser.getTelNumber());
		// parsnut datum
		String dateOfBirth = loggedUser.getBirthdate().toString();
		String pole[] = dateOfBirth.split("-");
		String newDate = pole[2] + "." + pole[1] + "." + pole[0];
		dateOfBirthTextField.setText(newDate);

		// ak image neni null, zobrazi image... ak image je null zobrazi G.png
//		if (loggedUser.getImage() != null) {
//			imageImageView.setImage(loggedUser.getImage());
//		}

		Blob usersBlob = loggedUser.getImage();
		InputStream in = usersBlob.getBinaryStream();
		Image image = new Image(in);

		imageImageView.setImage(image);

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