package sk.upjs.paz1c.guideman.controllers;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
import sk.upjs.paz1c.guideman.storage.DaoFactory;
import sk.upjs.paz1c.guideman.storage.User;
import sk.upjs.paz1c.guideman.storage.UserDao;

public class MyProfileController {

	private User loggedUser;
	private Window owner;
	private UserDao userDao = DaoFactory.INSTANCE.getUserDao();

	// obrazok
	private File selectedFile;
	private String filePath = null;
	private String oldFilePath = null;
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
	void editAndSaveButtonAction(ActionEvent event) throws IOException, SerialException, SQLException {
		owner = editAndSaveButton.getScene().getWindow();
		System.out.println("save");
		boolean changed = false;

		String changedName = nameTextField.getText();
		String changedSurname = surnameTextField.getText();
		String changedEmail = emailTextField.getText();
		String changedPhone = phoneNumberTextField.getText();

		String changedDateBirth = dateOfBirthTextField.getText();
		System.out.println(changedDateBirth);
		String pole[] = changedDateBirth.split("\\.");
		String finalDateBirth = pole[2] + "-" + pole[1] + "-" + pole[0];

		LocalDate birthdateParsed = null;

		try {
			birthdateParsed = LocalDate.parse(finalDateBirth);
		} catch (Exception e1) {
			showAlert(Alert.AlertType.WARNING, owner, "Wrong date format!", "Try using date format -> DD.MM.YYYY");
			return;
		}

		Blob blobisko = null;
		if (filePath != null) {
			BufferedImage image = ImageIO.read(new File(filePath));
			ByteArrayOutputStream outStreamObj = new ByteArrayOutputStream();
			if (filePath.contains(".jpg")) {
				ImageIO.write(image, "jpg", outStreamObj);
			}

			byte[] byteArray = outStreamObj.toByteArray();

			blobisko = null;

			if (byteArray != null) {
				blobisko = new SerialBlob(byteArray);

				if (blobisko.length() > 16000000L) {
					System.out.println("Error");
					showAlert(Alert.AlertType.ERROR, owner, "Error", "Please upload smaller image !");
					return;
				}
			}
			filePath = null;
		} else {
			blobisko = loggedUser.getImage();
		}

		// ci su prazdne
		if (changedName == "") {
			showAlert(Alert.AlertType.WARNING, owner, "Warning!", "Please enter name");
			return;
		}

		if (changedSurname == "") {
			showAlert(Alert.AlertType.WARNING, owner, "Warning!", "Please enter surname");
			return;
		}

		if (changedEmail == "") {
			showAlert(Alert.AlertType.WARNING, owner, "Warning!", "Please enter email");
			return;
		}

		if (changedDateBirth == "") {
			showAlert(Alert.AlertType.WARNING, owner, "Warning!", "Please enter date of birth");
			return;
		}

		// telcislo treba poriesit null alebo ""
		System.out.println(changedPhone);
		if (changedPhone == null) {

		} else {
			if (changedPhone.equals("")) {
				changedPhone = null;
			} else {
				if (!(changedPhone.equals(loggedUser.getTelNumber()))) {
					changed = true;
					System.out.println("DALO SA NA TRUE PRI TEL");
				}
			}
		}

//		if (!(changedName.equals(loggedUser.getName())) || !(changedSurname.equals(loggedUser.getSurname()))
//				|| !(changedEmail.equals(loggedUser.getEmail())) || !(birthdateParsed == (loggedUser.getBirthdate()))
//				|| !(blobisko == (loggedUser.getImage()))) {
//			changed = true;
//			System.out.println("DALO SA NA TRUE");
//		}

		if (!(changedName.equals(loggedUser.getName()))) {
			System.out.println("chyba v name");
			changed = true;
		}

		if (!(changedSurname.equals(loggedUser.getSurname()))) {
			System.out.println("chyba v surname");
			changed = true;
		}

		if (!(changedEmail.equals(loggedUser.getEmail()))) {
			System.out.println("chyba v mail");
			changed = true;
		}

		if (!(birthdateParsed.equals(LocalDate.parse(loggedUser.getBirthdate().toString())))) {
			System.out.println("chyba v date");
			changed = true;
		}

		if (blobisko != (loggedUser.getImage())) {
			System.out.println("chyba v blobe");
			changed = true;
		}

		if (changed) {
			changed = false;
			User user = new User(loggedUser.getId(), changedName, changedSurname, changedEmail, changedPhone,
					birthdateParsed, loggedUser.getLogin(), loggedUser.getPassword(), blobisko);

			int sizeBe4 = userDao.getAll().size();

			try {
				userDao.save(user);
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
			int sizeAfter = userDao.getAll().size();
			if (sizeBe4 == sizeAfter) {
				System.out.println("Edited and Saved successfuly");
				infoBox("Success", null, "Edited and Saved successfuly");
			}
			System.out.println(loggedUser + " stary");
			LoggedUser.INSTANCE.setLoggedUser(user);

			// loggedUser = user;

			loggedUser = LoggedUser.INSTANCE.getLoggedUser();
			// loggedUser = user;
			System.out.println(loggedUser + " novy");
			// System.out.println(LoggedUser.INSTANCE.getLoggedUser().toString());
		}
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
		Menu.INSTANCE.openMyTours(changeImageButton);
	}

	@FXML
	void searchTourButtonAction(ActionEvent event) {
		Menu.INSTANCE.openSearchTour(changeImageButton);
	}

	@FXML
	void createTourButtonAction(ActionEvent event) {
		Menu.INSTANCE.openCreateTour(changeImageButton);
	}

	@FXML
	void logOutButtonAction(ActionEvent event) {
		Menu.INSTANCE.logOut(changeImageButton);
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
		if (usersBlob != null) {
			System.out.println(usersBlob);
		}
		if (usersBlob != null) {
			InputStream in = usersBlob.getBinaryStream();
			Image image = new Image(in);
			imageImageView.setImage(image);
		}

//		nameTextField.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<User>() {
//
//			@Override
//			public void changed(ObservableValue<? extends User> observable, User oldValue, User newValue) {
//				if (newValue != null)
//					sessionModel.setSession(newValue);
//			}
//		});

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