package sk.upjs.paz1c.guideman.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class ShowTourController {

	@FXML
	private Label bioLabel;

	@FXML
	private Button createTourButton;

	@FXML
	private Label dateAndTimeFillLabel;

	@FXML
	private Label dateAndTimeLabel;

	@FXML
	private Label durationFillLabel;

	@FXML
	private Label durationLabel;

	@FXML
	private ImageView imageImageView;

	@FXML
	private Button letsGoButton;

	@FXML
	private Button logOutButton;

	@FXML
	private Button myProfileButton;

	@FXML
	private Button myToursButton;

	@FXML
	private Label numberOfFreePlacesFillLabel;

	@FXML
	private Label numberOfFreePlacesLabel;

	@FXML
	private Label priceFillLabel;

	@FXML
	private Label priceLabel;

	@FXML
	private Label ratingFillLabel;

	@FXML
	private Label ratingLabel;

	@FXML
	private Button searchTourButton;

	@FXML
	private Button seeReviewsButton;

	@FXML
	private Label showTourLabel;

	@FXML
	private Label titleFillLabel;

	@FXML
	private Label titleLabel;

	@FXML
	private Label guidemanFillLabel;

	@FXML
	private Label countryFillLabel;

	@FXML
	private Label cityFillLabel;

	@FXML
	private Label streetFillLabel;

	@FXML
	private Label streetNumberFillLabel;

	@FXML
	void myProfileButtonAction(ActionEvent event) {
		Menu.INSTANCE.openMyProfile(seeReviewsButton);
	}

	@FXML
	void myToursButtonAction(ActionEvent event) {
		Menu.INSTANCE.openMyTours(seeReviewsButton);
	}

	@FXML
	void searchTourButtonAction(ActionEvent event) {
		Menu.INSTANCE.openSearchTour(seeReviewsButton);
	}

	@FXML
	void createTourButtonAction(ActionEvent event) {
		Menu.INSTANCE.openCreateTour(seeReviewsButton);
	}

	@FXML
	void logOutButtonAction(ActionEvent event) {
		Menu.INSTANCE.logOut(seeReviewsButton);
	}

	@FXML
	void letsGoButtonAction(ActionEvent event) {

	}

	@FXML
	void seeReviewsButtonAction(ActionEvent event) {

	}

	@FXML
	void initialize() {

	}

}
