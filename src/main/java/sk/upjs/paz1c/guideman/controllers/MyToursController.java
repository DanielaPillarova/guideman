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
import sk.upjs.paz1c.guideman.models.TourFxModel;
import sk.upjs.paz1c.guideman.storage.DaoFactory;
import sk.upjs.paz1c.guideman.storage.Event;
import sk.upjs.paz1c.guideman.storage.EventDao;
import sk.upjs.paz1c.guideman.storage.Tour;
import sk.upjs.paz1c.guideman.storage.TourDao;

public class MyToursController {

	private TourDao tourDao;
	private EventDao eventDao;
	private Long loggedUserId;
	private List<String> displayedTours;
	
	private TourFxModel model;

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
		List<Tour> allTours = tourDao.getAllLetsGoTours(loggedUserId);
		List<Event> allEvents = eventDao.getAllLetsGoEvents(loggedUserId);
		int idx = 0;
		while (idx < allEvents.size()) {
			String s = parseDateAndTime(allTours.get(idx), allEvents.get(idx), allEvents.get(idx).getDateOfTour());
			allToursInListView.add(s);
			System.out.println(s);
			idx++;
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
		return "Title : " + t.getTitle() + ",           date of tour : " + sbDate + ",          time of tour : " + time
				+ ",          price : " + e.getPrice();
	}

	private void showToursAfterCheck(List<Tour> tours, List<Event> events, List<String> displayedTours,
			CheckBox checkBox) {
		List<String> allTours = new ArrayList<>();
		if (checkBox.isSelected()) {
			System.out.println("selecteeeeeddd");
//			System.out.println(tours.toString());
//			System.out.println(events.toString());
			int idx = 0;
			while (idx < events.size()) {
				String s = parseDateAndTime(tours.get(idx), events.get(idx), events.get(idx).getDateOfTour());
				allTours.add(s);
				System.out.println(s);
				idx++;
			}
			if (allTours.size() == 0) {
				if (displayedTours.size() == 0) {
					displayedTours.add("No tours found");
					toursListView.setItems(FXCollections.observableArrayList(displayedTours));
					toursListView.setMouseTransparent(true);
				}
			} else {
				toursListView.setMouseTransparent(false);
			}
		}

	}

	// TODO poriesit ked su zakliknute 2 a 3 a aj ked sa odklikne jedno alebo vsetky

	@FXML
	void pastToursChecked(ActionEvent event) {
		List<Tour> tours = tourDao.getAllToursFromPast(loggedUserId);
		List<Event> events = eventDao.getAllEventsFromPast(loggedUserId);
		showToursAfterCheck(tours, events, displayedTours, pastToursCheckBox);

//		if (pastToursCheckBox.isSelected()) {
//			System.out.println("selecteeeeeddd");
//			List<String> allToursInListView = new ArrayList<>();
//			List<Tour> tours = tourDao.getAllToursFromPast(loggedUserId);
//			List<Event> events = eventDao.getAllEventsFromPast(loggedUserId);
//			System.out.println(tours.toString());
//			System.out.println(events.toString());
//			int idx = 0;
//			while (idx < events.size()) {
//				String s = parseDateAndTime(tours.get(idx), events.get(idx), events.get(idx).getDateOfTour());
//				allToursInListView.add(s);
//				System.out.println(s);
//				idx++;
//			}
//			if (allToursInListView.size() == 0) {
//				allToursInListView.add("No past tours");
//				toursListView.setMouseTransparent(true);
//			}
//			toursListView.setItems(FXCollections.observableArrayList(allToursInListView));
//
//			// TODO ked nema ziadne past
//		}
	}

	@FXML
	void futureToursChecked(ActionEvent event) {
		List<Tour> tours = tourDao.getAllToursFromFuture(loggedUserId);
		List<Event> events = eventDao.getAllEventsFromFuture(loggedUserId);
		showToursAfterCheck(tours, events, displayedTours, futureToursCheckBox);
	}

	@FXML
	void toursWhereIAmGuidemanChecked(ActionEvent event) {
		List<Tour> tours = tourDao.getAllToursWhereIAmGuideman(loggedUserId);
		List<Event> events = eventDao.getAllEventsWhereIAmGuideman(loggedUserId);
		showToursAfterCheck(tours, events, displayedTours, toursWhereIAmGuidemanCheckBox);

	}

//	private void pastAndFutureChecked() {
//		if (pastToursCheckBox.isSelected() && futureToursCheckBox.isSelected()
//				&& !toursWhereIAmGuidemanCheckBox.isSelected()) {
//			displayedTours.addAll(showToursAfterCheck(tourDao.getAllToursFromPast(loggedUserId),
//					eventDao.getAllEventsFromPast(loggedUserId)));
//			displayedTours.addAll(showToursAfterCheck(tourDao.getAllToursFromFuture(loggedUserId),
//					eventDao.getAllEventsFromFuture(loggedUserId)));
//		}
//		toursListView.setItems(FXCollections.observableArrayList(displayedTours));
//
//	}
//
//	private void metoda(List<String> displayedTours, CheckBox checkBox) {
//		if (checkBox.isSelected()) {
//
//		}
//
//	}

	@FXML
	void initialize() {
		tourDao = DaoFactory.INSTANCE.getTourDao();
		eventDao = DaoFactory.INSTANCE.getEventDao();
		loggedUserId = LoggedUser.INSTANCE.getLoggedUser().getId();
		displayedTours = new ArrayList<>();
		showToursAtBeginning();

	}

}
