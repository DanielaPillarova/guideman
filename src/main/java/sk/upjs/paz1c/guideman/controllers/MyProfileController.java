package sk.upjs.paz1c.guideman.controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import sk.upjs.paz1c.guideman.App;
import sk.upjs.paz1c.guideman.storage.User;

public class MyProfileController {
	
	private User loggedUser;

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
	void initialize() {
		loggedUser = LoggedUser.INSTANCE.getLoggedUser();
		System.out.println(loggedUser);
		
		nameTextField.setText(loggedUser.getName());
		surnameTextField.setText(loggedUser.getSurname());
		emailTextField.setText(loggedUser.getEmail());
//		if (loggedUser.getTelNumber())
		phoneNumberTextField.setText(loggedUser.getTelNumber());
		// parsnut datum
		dateOfBirthTextField.setText(loggedUser.getBirthdate().toString());
		
		// ak image neni null, zobrazi image... ak image je null zobrazi G.png
//		if (loggedUser.getImage() != null) {
//			imageImageView.setImage(loggedUser.getImage());
//		}

		

	}

}