package sk.upjs.paz1c.guideman.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;

public class WritingRatingController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private RadioButton fiveRadioButton;

    @FXML
    private RadioButton fourRadioButton;

    @FXML
    private RadioButton oneRadioButton;

    @FXML
    private Label pleaseRateLabel;

    @FXML
    private Label pleaseReviewLabel;

    @FXML
    private TextArea reviewTextArea;

    @FXML
    private Button saveButton;

    @FXML
    private RadioButton threeRadioButton;

    @FXML
    private RadioButton twoRadioButton;

    @FXML
    private RadioButton zeroRadioButton;

    @FXML
    void initialize() {
        
    }

}
