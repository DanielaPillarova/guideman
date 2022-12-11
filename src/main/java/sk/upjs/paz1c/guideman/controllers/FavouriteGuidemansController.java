package sk.upjs.paz1c.guideman.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class FavouriteGuidemansController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button createTourButton;

    @FXML
    private Button deleteGuidemanButton;

    @FXML
    private Label favouriteGuidemansLabel;

    @FXML
    private ListView<?> guidemansListView;

    @FXML
    private Button logOutButton;

    @FXML
    private Button myProfileButton;

    @FXML
    private Button myToursButton;

    @FXML
    private Button saveChangesButton;

    @FXML
    private Button searchTourButton;

    @FXML
    void initialize() {
    	
    }

}
