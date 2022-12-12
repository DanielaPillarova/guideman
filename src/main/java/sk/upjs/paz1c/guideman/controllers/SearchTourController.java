package sk.upjs.paz1c.guideman.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class SearchTourController {

    @FXML
    private Label countryLabel;

    @FXML
    private Button createTourButton;

    @FXML
    private ListView<?> filteredToursListView;

    @FXML
    private Label guidemanLabel;

    @FXML
    private Button logOutButton;

    @FXML
    private Label monthLabel;

    @FXML
    private Button myProfileButton;

    @FXML
    private Button myToursButton;

    @FXML
    private Label priceLabel;

    @FXML
    private Button searchTourButton;

    @FXML
    private Button showFilterTableButton;

    @FXML
    private Button showTourButton;


    @FXML
    void showFilterTableButtonAction(ActionEvent event) {

    }

    @FXML
    void showTourButtonAction(ActionEvent event) {

    }
    
    
    @FXML
    void myProfileButtonAction(ActionEvent event) {
		Menu.INSTANCE.openMyProfile(showFilterTableButton);
    }

    @FXML
    void myToursButtonAction(ActionEvent event) {
		Menu.INSTANCE.openMyTours(showFilterTableButton);
    }
    
    @FXML
    void searchTourButtonAction(ActionEvent event) {
    	System.out.println("search");
    }
    
    @FXML
    void createTourButtonAction(ActionEvent event) {
    	Menu.INSTANCE.openCreateTour(showFilterTableButton);
    }

    @FXML
    void logOutButtonAction(ActionEvent event) {
    	Menu.INSTANCE.logOut(showFilterTableButton);
    }
    
    @FXML
    void initialize() {
       
    }

}
