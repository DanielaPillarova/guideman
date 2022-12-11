package sk.upjs.paz1c.guideman.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MenuController {

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

	@FXML
	void initialize() {

	}

}
