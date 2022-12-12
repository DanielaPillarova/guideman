package sk.upjs.paz1c.guideman.controllers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import sk.upjs.paz1c.guideman.storage.DaoFactory;
import sk.upjs.paz1c.guideman.storage.Event;
import sk.upjs.paz1c.guideman.storage.EventDao;
import sk.upjs.paz1c.guideman.storage.Tour;
import sk.upjs.paz1c.guideman.storage.TourDao;

public class MyToursController {

	private TourDao tourDao;
	private EventDao eventDao;
	private Long loggedUserId;
	
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

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
	private ListView<String> toursListView;

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

	private void showToursAtBeginning() {	
		List<String> allToursInListView = new ArrayList<>();
		List<Tour> allTours = tourDao.getAll();
		for (Tour t : allTours) {
			List<Event> allEventsByTourId = eventDao.getAllByTour(t.getId());
			for (Event e : allEventsByTourId ) {
				allToursInListView.add(parseDateAndTime(t, e, e.getDateOfTour()));
			}
		}
		toursListView.setItems(FXCollections.observableArrayList(allToursInListView));
	}
	
	private String parseDateAndTime(Tour t, Event e, LocalDateTime localDateTime) {
		String[] dt = localDateTime.toString().split("T");
		String[] date = dt[0].split("-");
		String time = dt[1];
		StringBuilder sbDate = new StringBuilder();
		sbDate.append(date[2]);
		sbDate.append(".");
		sbDate.append(date[1]);
		sbDate.append(".");
		sbDate.append(date[0]);
		return "Title : " + t.getTitle() + ",           date of tour : " 
		+ sbDate + ",          time of tour : " + time + ",          price : " + e.getPrice();
	}
	
	@FXML
	void pastToursChecked(ActionEvent event) {
		if (pastToursCheckBox.isSelected()) {
			System.out.println("selecteeeeeddd");
			List<String> allToursInListView = new ArrayList<>();
			List<Tour> tours = tourDao.getAllToursFromPast(loggedUserId);
			List<Event> events = eventDao.getAllEventsFromPast(loggedUserId);
			System.out.println(tours.toString());
			System.out.println(events.toString());
			int idx = 0;
			while (idx < events.size()) {
				String s = parseDateAndTime(tours.get(idx), events.get(idx), events.get(idx).getDateOfTour());
				allToursInListView.add(s);
				System.out.println(s);
				idx++;
			}
			toursListView.setItems(FXCollections.observableArrayList(allToursInListView));
			
			// TODO ked nema ziadne past
		}
	}

	@FXML
    void futureToursChecked(ActionEvent event) {

    }
	
	@FXML
    void toursWhereIAmGuidemanChecked(ActionEvent event) {

    }
	
//	private void metoda(List<Tour> tours, List<Event> events) {
//		
//	}


	@FXML
	void initialize() {
		tourDao = DaoFactory.INSTANCE.getTourDao();
		eventDao = DaoFactory.INSTANCE.getEventDao();
		loggedUserId = LoggedUser.INSTANCE.getLoggedUser().getId();
		showToursAtBeginning();

	}

}
