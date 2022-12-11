package sk.upjs.paz1c.guideman.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;

public class MyToursController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

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
    void initialize() {
        
    }

}
