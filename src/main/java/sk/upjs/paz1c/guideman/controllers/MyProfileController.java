package sk.upjs.paz1c.guideman.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class MyProfileController {

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
	private Label myProfileLabel;

	@FXML
	private Label nameLabel;

	@FXML
	private Label surnameLabel;

	@FXML
	private Label emailLabel;

	@FXML
	private Label phoneNumberLabel;

	@FXML
	private Label dateOfBirthLabel;

	@FXML
	private TextField nameTextField;

	@FXML
	private TextField surnameTextField;

	@FXML
	private TextField emailTextField;

	@FXML
	private TextField phoneNumberTextField;

	@FXML
	private TextField dateOfBirthTextField;

	@FXML
	private Button editAndSaveButton;

	@FXML
	private Button showFavouriteGuidemansButton;

	@FXML
	private Label imageLabel;

	@FXML
	private ImageView imageImageView;

	@FXML
	private Button changeImageButton;

	@FXML
	void changeImageButtonAction(ActionEvent event) {
		System.out.println("change");
	}

	@FXML
	void createTourButtonAction(ActionEvent event) {
		System.out.println("create");

	}

	@FXML
	void editAndSaveButtonAction(ActionEvent event) {
		System.out.println("save");
	}

	@FXML
	void logOutButtonAction(ActionEvent event) {
		System.out.println("lougout");
	}

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
	void showFavouriteGuidemansButtonAction(ActionEvent event) {
		System.out.println("guidemans");

	}

	@FXML
	void initialize() {
		System.out.println("halo");
	}

}
