package sk.upjs.paz1c.guideman.controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class MyToursController {

    @FXML
    private Button addRatingOrReviewButton;

    @FXML
    private Button createTourButton;

    @FXML
    private Button deleteTourButton;

    @FXML
    private CheckBox futureToursCheckBox;

    @FXML
    private Button logOutButton;

    @FXML
    private Button myProfileButton;

    @FXML
    private Button myToursButton;

    @FXML
    private Label myToursLabel;

    @FXML
    private CheckBox pastToursCheckBox;

    @FXML
    private Button searchTourButton;

    @FXML
    private Button showTourButton;

    @FXML
    private CheckBox toursWhereIAmGuidemanCheckBox;

    @FXML
    void addRatingOrReviewButtonAction(ActionEvent event) {

    }
    
    @FXML
    void deleteTourButtonAction(ActionEvent event) {

    }
    
    @FXML
    void showTourButtonAction(ActionEvent event) {

    }

    @FXML
    void myProfileButtonAction(ActionEvent event) {
		Menu.INSTANCE.openMyProfile(addRatingOrReviewButton);
    }

    @FXML
    void myToursButtonAction(ActionEvent event) {
    	System.out.println("tours");
    }

    @FXML
    void searchTourButtonAction(ActionEvent event) {
    	Menu.INSTANCE.openSearchTour(addRatingOrReviewButton);
    }
    
    @FXML
    void createTourButtonAction(ActionEvent event) {
    	Menu.INSTANCE.openCreateTour(addRatingOrReviewButton);
    }
    
    @FXML
    void logOutButtonAction(ActionEvent event) {
    	Menu.INSTANCE.logOut(addRatingOrReviewButton);
    }
   

    @FXML
    void initialize() {
        
    }

}
