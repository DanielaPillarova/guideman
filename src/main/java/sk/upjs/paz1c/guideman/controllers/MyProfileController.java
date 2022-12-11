package sk.upjs.paz1c.guideman.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class MyProfileController {

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
	void changeImageButtonAction(ActionEvent event) {
		System.out.println("change");

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
	}

	///////////////

	@FXML
	void initialize() {

	}

}