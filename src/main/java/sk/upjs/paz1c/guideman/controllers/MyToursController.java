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
//	private List<String> displayedTours;

//	private boolean allIsSelected = false;
//	private boolean allIsNotSelected = true;

	private TourFxModel model;

	public MyToursController() {
		model = new TourFxModel();
	}

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

	private void showTours(List<Tour> tours, List<Event> events) {
		toursListView.setMouseTransparent(false);

		List<String> allTours = new ArrayList<>();
		int idx = 0;
		while (idx < events.size()) {
			String s = parseDateAndTime(tours.get(idx), events.get(idx), events.get(idx).getDateOfTour());
			allTours.add(s);
			System.out.println(s);
			idx++;
		}
		if (allTours.size() == 0) {
			allTours.add("No tours found");
			toursListView.setMouseTransparent(true);
		}
		toursListView.setItems(FXCollections.observableArrayList(allTours));

//		if (allTours.size() != 0) {
//			if (displayedTours.size() == allTours.size()) {
//				boolean areTheSame = true;
//				int idx2 = 0;
//				while (idx2 < allTours.size()) {
//					if (!displayedTours.get(idx2).equals(allTours.get(idx2))) {
//						areTheSame = false;
//						break;
//					}
//					idx2++;
//				}
//				if (areTheSame == false) {
//					displayedTours.addAll(allTours);
//
//				} 
//				
//			}
//			
//			if (displayedTours.size() == 0 || displayedTours.get(0).equals("No tours found")) {
//				displayedTours = new ArrayList<>();
//				displayedTours.addAll(allTours);
//			}
//			
//		} 
//		
//		toursListView.setItems(FXCollections.observableArrayList(displayedTours));

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

	private boolean allIsNotSelected() {
		if (!pastToursCheckBox.isSelected() && !futureToursCheckBox.isSelected()
				&& !toursWhereIAmGuidemanCheckBox.isSelected()) {
			List<Tour> tours = tourDao.getAllLetsGoTours(loggedUserId);
			List<Event> events = eventDao.getAllLetsGoEvents(loggedUserId);
			showTours(tours, events);
			return true;
		}
		return false;
	}

//	private boolean allIsSelected() {
//		if (pastToursCheckBox.isSelected() && futureToursCheckBox.isSelected()
//				&& toursWhereIAmGuidemanCheckBox.isSelected()) {
//			List<Tour> tours = tourDao.getAllLetsGoTours(loggedUserId);
//			List<Event> events = eventDao.getAllLetsGoEvents(loggedUserId);
//			tours.addAll(tourDao.getAllToursWhereIAmGuideman(loggedUserId));
//			events.addAll(eventDao.getAllEventsWhereIAmGuideman(loggedUserId));
//			showTours(tours, events);
//			return true;
//		}
//		return false;
//	}

	// TODO poriesit ked su zakliknute 2 a 3 a aj ked sa odklikne jedno alebo vsetky

	@FXML
	void pastToursChecked(ActionEvent event) {
		if (pastToursCheckBox.isSelected() == true) {

			futureToursCheckBox.setMouseTransparent(true);
			toursWhereIAmGuidemanCheckBox.setMouseTransparent(true);

			List<Tour> tours = tourDao.getAllToursFromPast(loggedUserId);
			List<Event> events = eventDao.getAllEventsFromPast(loggedUserId);
			showTours(tours, events);

		}
		if (pastToursCheckBox.isSelected() == false) {
			futureToursCheckBox.setMouseTransparent(false);
			toursWhereIAmGuidemanCheckBox.setMouseTransparent(false);
			allIsNotSelected();
		}

	}

	@FXML
	void futureToursChecked(ActionEvent event) {
		if (futureToursCheckBox.isSelected() == true) {

			pastToursCheckBox.setMouseTransparent(true);
			toursWhereIAmGuidemanCheckBox.setMouseTransparent(true);

			List<Tour> tours = tourDao.getAllToursFromFuture(loggedUserId);
			List<Event> events = eventDao.getAllEventsFromFuture(loggedUserId);
			showTours(tours, events);

		}
		if (futureToursCheckBox.isSelected() == false) {
			pastToursCheckBox.setMouseTransparent(false);
			toursWhereIAmGuidemanCheckBox.setMouseTransparent(false);
			allIsNotSelected();
		}

	}

	@FXML
	void toursWhereIAmGuidemanChecked(ActionEvent event) {
		if (toursWhereIAmGuidemanCheckBox.isSelected() == true) {

			pastToursCheckBox.setMouseTransparent(true);
			futureToursCheckBox.setMouseTransparent(true);
			
			List<Tour> tours = tourDao.getAllToursWhereIAmGuideman(loggedUserId);
			List<Event> events = eventDao.getAllEventsWhereIAmGuideman(loggedUserId);
			showTours(tours, events);

		}
		if (toursWhereIAmGuidemanCheckBox.isSelected() == false) {
			pastToursCheckBox.setMouseTransparent(false);
			futureToursCheckBox.setMouseTransparent(false);
			allIsNotSelected();
		}

	}

	@FXML
	void initialize() {
		tourDao = DaoFactory.INSTANCE.getTourDao();
		eventDao = DaoFactory.INSTANCE.getEventDao();
		loggedUserId = LoggedUser.INSTANCE.getLoggedUser().getId();
//		displayedTours = new ArrayList<>();
		allIsNotSelected();

	}

}
