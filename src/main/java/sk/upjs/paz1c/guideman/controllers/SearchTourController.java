package sk.upjs.paz1c.guideman.controllers;

import java.io.IOException;
import java.util.List;

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
import sk.upjs.paz1c.guideman.storage.DaoFactory;
import sk.upjs.paz1c.guideman.storage.Event;
import sk.upjs.paz1c.guideman.storage.EventDao;
import sk.upjs.paz1c.guideman.storage.Location;
import sk.upjs.paz1c.guideman.storage.LocationDao;

public class SearchTourController {

	private String country;
	private String month;
	private String guideman;
	private String price;
	
	private LocationDao locationDao;
	private EventDao eventDao;
	
    @FXML
    private Label countryLabel;

    @FXML
    private Button createTourButton;

    @FXML
    private ListView<String> filteredToursListView;

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
    void initialize() {
    	locationDao = DaoFactory.INSTANCE.getLocationDao();
    	eventDao = DaoFactory.INSTANCE.getEventDao();
    }


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
		
		if (Filter.INSTANCE.getNewFilters()) {
			fillLabels();
			country = Filter.INSTANCE.getCountry();
			month = Filter.INSTANCE.getMonth();
			guideman = Filter.INSTANCE.getGuideman();
			price = Filter.INSTANCE.getPrice();
			
			System.out.println(country);
			System.out.println(month);
			System.out.println(guideman);
			System.out.println(price);
			
			// fill listview
		}
    }
    
    private void fillLabels() {
    	countryLabel.setText(Filter.INSTANCE.getCountry());
    	monthLabel.setText(Filter.INSTANCE.getMonth());
    	guidemanLabel.setText(Filter.INSTANCE.getGuideman());
    	priceLabel.setText(Filter.INSTANCE.getPrice());
    }
    
    private int parseMonthToInt(String monthStr) {
    	if (monthStr.equals("January")) {
    		return 1;
    	}
    	if (monthStr.equals("February")) {
    		return 2;
    	}
    	if (monthStr.equals("March")) {
    		return 3;
    	}
    	if (monthStr.equals("April")) {
    		return 4;
    	}
    	if (monthStr.equals("May")) {
    		return 5;
    	}
    	if (monthStr.equals("June")) {
    		return 6;
    	}
    	if (monthStr.equals("July")) {
    		return 7;
    	}
    	if (monthStr.equals("August")) {
    		return 8;
    	}
    	if (monthStr.equals("September")) {
    		return 9;
    	}
    	if (monthStr.equals("October")) {
    		return 10;
    	}
    	if (monthStr.equals("November")) {
    		return 11;
    	}
    	if (monthStr.equals("December")) {
    		return 12;
    	}
    	return 0;
    }
    
    private void fillListView() {
    	List<Location> locations = locationDao.getAllByCountry(country);
    	List<Event> events = eventDao.getAllByMonth(parseMonthToInt(month));
    }

    @FXML
    void showTourButtonAction(ActionEvent event) {
    	try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("showTour.fxml"));
			fxmlLoader.setController(new ShowTourController());
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
    
   

}
