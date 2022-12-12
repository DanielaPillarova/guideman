package sk.upjs.paz1c.guideman.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class CreateTourController {

	private byte[] bytes = null;
	private String nameOfFile;
	private String filePath;
	private File selectedFile;

	@FXML
	private TextArea bioTextArea;

	@FXML
	private ComboBox<?> chooseTourComboBox;

	@FXML
	private TextField cityTextField;

	@FXML
	private TextField countryTextField;

	@FXML
	private Button createButton;

	@FXML
	private Button createTourButton;

	@FXML
	private TextField dateAndTimeOfTourTextField;

	@FXML
	private TextField durationTextField;

	@FXML
	private Button logOutButton;

	@FXML
	private Button myProfileButton;

	@FXML
	private Button myToursButton;

	@FXML
	private Label noSelectedImageLabel;

	@FXML
	private TextField numberOfPeopleTextField;

	@FXML
	private TextField priceTextField;

	@FXML
	private Button searchTourButton;

	@FXML
	private Button selectImageButton;

	@FXML
	private TextField streetNumberTextField;

	@FXML
	private TextField streetTextField;

	@FXML
	private TextField titleTextField;

	@FXML
	void createButtonAction(ActionEvent event) {

	}

	@FXML
	void selectImageButtonAction(ActionEvent event) throws IOException {

		bytes = null;
		noSelectedImageLabel.setText("No selected file");

		if (event.getSource() == selectImageButton) {

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
				selectedFile = new File(fileChooser.getSelectedFile().getAbsolutePath()); // File , jeho cesta
				filePath = selectedFile.getAbsolutePath(); // string
				if (filePath.endsWith(".jpg") || filePath.endsWith(".JPG") || filePath.endsWith(".PNG")
						|| filePath.endsWith(".png")) {
					System.out.println(filePath);
					bytes = Files.readAllBytes(Paths.get(filePath));
					nameOfFile = selectedFile.getName();
					noSelectedImageLabel.setText(nameOfFile);
				} else {
					infoBox("Please Select Image File", null, "Warning !");
				}
			}
		}
	}

	@FXML
	void myProfileButtonAction(ActionEvent event) {
		Menu.INSTANCE.openMyProfile(createButton);
	}

	@FXML
	void myToursButtonAction(ActionEvent event) {
		Menu.INSTANCE.openMyTours(createButton);
	}

	@FXML
	void searchTourButtonAction(ActionEvent event) {
		Menu.INSTANCE.openSearchTour(createButton);
	}

	@FXML
	void createTourButtonAction(ActionEvent event) {
		System.out.println("create");
	}

	@FXML
	void logOutButtonAction(ActionEvent event) {
		Menu.INSTANCE.logOut(createButton);
	}

	@FXML
	void initialize() {

		BooleanBinding bb = new BooleanBinding() {
			{
				super.bind(titleTextField.textProperty(), numberOfPeopleTextField.textProperty(),
						countryTextField.textProperty(), cityTextField.textProperty(), streetTextField.textProperty(),
						dateAndTimeOfTourTextField.textProperty(), durationTextField.textProperty(),
						priceTextField.textProperty());
			}

			@Override
			protected boolean computeValue() {
				return (titleTextField.getText().isEmpty() || numberOfPeopleTextField.getText().isEmpty()
						|| countryTextField.getText().isEmpty() || cityTextField.getText().isEmpty()
						|| streetTextField.getText().isEmpty() || dateAndTimeOfTourTextField.getText().isEmpty()
						|| durationTextField.getText().isEmpty() || priceTextField.getText().isEmpty());
			}
		};

		createButton.disableProperty().bind(bb);

	}

	public static void infoBox(String infoMessage, String headerText, String title) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setContentText(infoMessage);
		alert.setTitle(title);
		alert.setHeaderText(headerText);
		alert.showAndWait();
	}

}
