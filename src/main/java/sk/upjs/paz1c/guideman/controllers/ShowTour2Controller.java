package sk.upjs.paz1c.guideman.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import sk.upjs.paz1c.guideman.storage.Tour;

public class ShowTour2Controller {

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
    private Label guidemanFillLabel;

    @FXML
    private ImageView imageImageView;

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
    void seeReviewsButtonAction(ActionEvent event) {

    }

    @FXML
    void initialize() {
      Tour loggedTour = ShowTour.INSTANCE.getLoggedTour();

    }

}
