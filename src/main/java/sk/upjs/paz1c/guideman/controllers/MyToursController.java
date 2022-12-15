package sk.upjs.paz1c.guideman.controllers;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
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

//	private TourFxModel model;
//
//	public MyToursController() {
//		model = new TourFxModel();
//	}

	@FXML
	void initialize() {
		tourDao = DaoFactory.INSTANCE.getTourDao();
		eventDao = DaoFactory.INSTANCE.getEventDao();
		loggedUserId = LoggedUser.INSTANCE.getLoggedUser().getId();
		allIsNotSelected();
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

	@FXML
	void addRatingOrReviewButtonAction(ActionEvent event) {
		Event e1 = getEventFromListView();
		ShowTour.INSTANCE.setLoggedEvent(e1);
		System.out.println(e1);
		
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("writingRating.fxml"));
			fxmlLoader.setController(new WritingRatingController());
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
	void deleteTourButtonAction(ActionEvent event) {

	}
	
	private Event getEventFromListView() {
		String s = toursListView.getSelectionModel().getSelectedItem();
		String[] temp1 = s.split(" ");
		String eventIdString = temp1[temp1.length - 1];
		Long eventIdLong = Long.parseLong(eventIdString);
		return eventDao.getById(eventIdLong);
	}

	@FXML
	void showTourButtonAction(ActionEvent event) {
		Event e1 = getEventFromListView();
		Tour t1 = tourDao.getById(e1.getTourId());
		ShowTour.INSTANCE.setLoggedEvent(e1);
		ShowTour.INSTANCE.setLoggedTour(t1);
		System.out.println(t1);
		System.out.println(e1);

		try {
			FXMLLoader fxmlLoader = new FXMLLoader(ShowTourController.class.getResource("showTour.fxml"));
			Stage stage = new Stage();
			fxmlLoader.setController(new ShowTourController());
			Scene scene = new Scene(fxmlLoader.load());
			stage.setTitle("Guideman");
			stage.getIcons().add(new Image("sk/upjs/paz1c/guideman/controllers/G-logo light.png"));
			stage.setScene(scene);
			stage.show();
			addRatingOrReviewButton.getScene().getWindow().hide();
		} catch (IOException e) {
			e.printStackTrace();
		}

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
		return "Title : " + t.getTitle() + ",  date of tour : " + sbDate + ",  time of tour : " + time + ",  price : "
				+ e.getPrice() + ",  event id : " + e.getId();
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
			addRatingOrReviewButton.setDisable(true);
		}
		toursListView.setItems(FXCollections.observableArrayList(allTours));
	}

	private boolean allIsNotSelected() {
		if (!pastToursCheckBox.isSelected() && !futureToursCheckBox.isSelected()
				&& !toursWhereIAmGuidemanCheckBox.isSelected()) {
			addRatingOrReviewButton.setDisable(true);
			List<Tour> tours = tourDao.getAllLetsGoTours(loggedUserId);
			List<Event> events = eventDao.getAllLetsGoEvents(loggedUserId);
			showTours(tours, events);
			return true;
		}
		return false;
	}

	@FXML
	void pastToursChecked(ActionEvent event) {
		if (pastToursCheckBox.isSelected() == true) {
			addRatingOrReviewButton.setDisable(false);
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
			addRatingOrReviewButton.setDisable(true);
			pastToursCheckBox.setMouseTransparent(true);
			toursWhereIAmGuidemanCheckBox.setMouseTransparent(true);
			List<Tour> tours = tourDao.getAllToursFromFuture(loggedUserId);
			List<Event> events = eventDao.getAllEventsFromFuture(loggedUserId);
			showTours(tours, events);
		}
		if (futureToursCheckBox.isSelected() == false) {
			addRatingOrReviewButton.setDisable(false);
			pastToursCheckBox.setMouseTransparent(false);
			toursWhereIAmGuidemanCheckBox.setMouseTransparent(false);
			allIsNotSelected();
		}
	}

	@FXML
	void toursWhereIAmGuidemanChecked(ActionEvent event) {
		if (toursWhereIAmGuidemanCheckBox.isSelected() == true) {
			addRatingOrReviewButton.setDisable(true);
			pastToursCheckBox.setMouseTransparent(true);
			futureToursCheckBox.setMouseTransparent(true);
			List<Tour> tours = tourDao.getAllToursWhereIAmGuideman(loggedUserId);
			List<Event> events = eventDao.getAllEventsWhereIAmGuideman(loggedUserId);
			showTours(tours, events);
		}
		if (toursWhereIAmGuidemanCheckBox.isSelected() == false) {
			addRatingOrReviewButton.setDisable(false);
			pastToursCheckBox.setMouseTransparent(false);
			futureToursCheckBox.setMouseTransparent(false);
			allIsNotSelected();
		}
	}

}
