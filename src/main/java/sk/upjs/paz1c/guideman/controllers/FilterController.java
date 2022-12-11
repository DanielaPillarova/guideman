package sk.upjs.paz1c.guideman.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

public class FilterController {


    @FXML
    private ComboBox<?> filterByCountryComboBox;

    @FXML
    private ComboBox<?> filterByGuidemanComboBox;

    @FXML
    private ComboBox<?> filterByMonthComboBox;

    @FXML
    private Slider filterByPriceSlider;

    @FXML
    private Label priceLabel;

    @FXML
    private Button saveAndFilterButton;

    @FXML
    void saveAndFilterButtonAction(ActionEvent event) {

    }

    @FXML
    void initialize() {
        
    }

}
