package sk.upjs.paz1c.guideman.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;

public class WritingRatingController {
	
	private int rating;
	private String review;


    @FXML
    private RadioButton fiveRadioButton;

    @FXML
    private RadioButton fourRadioButton;

    @FXML
    private RadioButton oneRadioButton;

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
    void zeroRadioButtonAction(ActionEvent event) {
    	if (zeroRadioButton.isSelected()) {
    		oneRadioButton.setDisable(true);
    		twoRadioButton.setDisable(true);
    		threeRadioButton.setDisable(true);
    		fourRadioButton.setDisable(true);
    		fiveRadioButton.setDisable(true);
    		rating = 0;
    		System.out.println(rating);
    	}
    	if (!zeroRadioButton.isSelected()) {
    		oneRadioButton.setDisable(false);
    		twoRadioButton.setDisable(false);
    		threeRadioButton.setDisable(false);
    		fourRadioButton.setDisable(false);
    		fiveRadioButton.setDisable(false);
    	}

    }
    
    @FXML
    void oneRadioButtonAction(ActionEvent event) {
    	if (oneRadioButton.isSelected()) {
    		zeroRadioButton.setDisable(true);
    		twoRadioButton.setDisable(true);
    		threeRadioButton.setDisable(true);
    		fourRadioButton.setDisable(true);
    		fiveRadioButton.setDisable(true);
    		rating = 1;
    		System.out.println(rating);

    	}
    	if (!oneRadioButton.isSelected()) {
    		zeroRadioButton.setDisable(false);
    		twoRadioButton.setDisable(false);
    		threeRadioButton.setDisable(false);
    		fourRadioButton.setDisable(false);
    		fiveRadioButton.setDisable(false);
    	}
    	
    }
    
    @FXML
    void twoRadioButtonAction(ActionEvent event) {
    	if (twoRadioButton.isSelected()) {
    		zeroRadioButton.setDisable(true);
    		oneRadioButton.setDisable(true);
    		threeRadioButton.setDisable(true);
    		fourRadioButton.setDisable(true);
    		fiveRadioButton.setDisable(true);
    		rating = 2;
    		System.out.println(rating);
    	}
    	if (!twoRadioButton.isSelected()) {
    		zeroRadioButton.setDisable(false);
    		oneRadioButton.setDisable(false);
    		threeRadioButton.setDisable(false);
    		fourRadioButton.setDisable(false);
    		fiveRadioButton.setDisable(false);
    	}
    }
    
    @FXML
    void threeRadioButtonAction(ActionEvent event) {
    	if (threeRadioButton.isSelected()) {
    		zeroRadioButton.setDisable(true);
    		oneRadioButton.setDisable(true);
    		twoRadioButton.setDisable(true);
    		fourRadioButton.setDisable(true);
    		fiveRadioButton.setDisable(true);
    		rating = 3;
    		System.out.println(rating);
    	}
    	if (!threeRadioButton.isSelected()) {
    		zeroRadioButton.setDisable(false);
    		oneRadioButton.setDisable(false);
    		twoRadioButton.setDisable(false);
    		fourRadioButton.setDisable(false);
    		fiveRadioButton.setDisable(false);
    	}
    }

    @FXML
    void fourRadioButtonAction(ActionEvent event) {
    	if (fourRadioButton.isSelected()) {
    		zeroRadioButton.setDisable(true);
    		oneRadioButton.setDisable(true);
    		twoRadioButton.setDisable(true);
    		threeRadioButton.setDisable(true);
    		fiveRadioButton.setDisable(true);
    		rating = 4;
    		System.out.println(rating);
    	}
    	if (!fourRadioButton.isSelected()) {
    		zeroRadioButton.setDisable(false);
    		oneRadioButton.setDisable(false);
    		twoRadioButton.setDisable(false);
    		threeRadioButton.setDisable(false);
    		fiveRadioButton.setDisable(false);
    	}
    }
    
    @FXML
    void fiveRadioButtonAction(ActionEvent event) {
    	if (fiveRadioButton.isSelected()) {
    		zeroRadioButton.setDisable(true);
    		oneRadioButton.setDisable(true);
    		twoRadioButton.setDisable(true);
    		threeRadioButton.setDisable(true);
    		fourRadioButton.setDisable(true);
    		rating = 5;
    		System.out.println(rating);
    	}
    	if (!fiveRadioButton.isSelected()) {
    		zeroRadioButton.setDisable(false);
    		oneRadioButton.setDisable(false);
    		twoRadioButton.setDisable(false);
    		threeRadioButton.setDisable(false);
    		fourRadioButton.setDisable(false);
    	}
    }

  
    @FXML
    void saveButtonAction(ActionEvent event) {
    	review = reviewTextArea.getText();
    	System.out.println(review);

    }

   

   

   

    @FXML
    void initialize() {
       
    }

}

