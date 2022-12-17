package sk.upjs.paz1c.guideman.controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

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
    	try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("filter.fxml"));
			fxmlLoader.setController(new FilterController());
			Parent parent = fxmlLoader.load();
			Scene scene = new Scene(parent);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.setTitle("Guideman");
			stage.getIcons().add(new Image("sk/upjs/paz1c/guideman/controllers/G-logo light.png"));
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}

    	System.out.println(Filter.INSTANCE.getCountry());
		System.out.println(Filter.INSTANCE.getMonth());
		System.out.println(Filter.INSTANCE.getGuideman());
		System.out.println(Filter.INSTANCE.getPrice());
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
