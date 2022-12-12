package sk.upjs.paz1c.guideman.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class CreateTourController {

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
    void selectImageButtonAction(ActionEvent event) {

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
       
    }

}
