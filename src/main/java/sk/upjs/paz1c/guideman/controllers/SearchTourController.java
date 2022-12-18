package sk.upjs.paz1c.guideman.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import sk.upjs.paz1c.guideman.storage.DaoFactory;
import sk.upjs.paz1c.guideman.storage.Event;
import sk.upjs.paz1c.guideman.storage.EventDao;
import sk.upjs.paz1c.guideman.storage.Location;
import sk.upjs.paz1c.guideman.storage.LocationDao;
import sk.upjs.paz1c.guideman.storage.Tour;
import sk.upjs.paz1c.guideman.storage.TourDao;
import sk.upjs.paz1c.guideman.storage.User;
import sk.upjs.paz1c.guideman.storage.UserDao;

public class SearchTourController {

	private String country;
	private String month;
	private String guideman;
	private String price;

	private EventDao eventDao;
	private TourDao tourDao;
	private UserDao userDao;
	private Long loggedUserId;
	private Window owner;

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
		eventDao = DaoFactory.INSTANCE.getEventDao();
		tourDao = DaoFactory.INSTANCE.getTourDao();
		userDao = DaoFactory.INSTANCE.getUserDao();
		loggedUserId = LoggedUser.INSTANCE.getLoggedUser().getId();
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

			fillListView();
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

	private List<Event> checkIfICanSignForTour(List<Event> events) {
		List<Event> allEvents = new ArrayList<>();
		for (Event event : events) {
			Tour tour = tourDao.getById(event.getTourId());
			if (tour.getGuidemanId() != loggedUserId) {
				List<User> tourists = userDao.getAllTourists(event.getId());
				for (User u : tourists) {
					if (u.getId() != loggedUserId) {
						allEvents.add(event);
					}
				}

			}
		}
		return allEvents;
	}

	private void fillListView() {
		System.out.println("#### SME VO fill list view");

		// Set<Event> allEvents = new HashSet<>();
		// List<Event> allTours = new ArrayList<>();
		Set<String> displayedTours = new HashSet<>();

		List<Event> temp = new ArrayList<>();
		List<Event> allEvents = new ArrayList<>();

		// country
		System.out.println("COUNTRY : " + country);
		System.out.println("ALL EVENTS : " + eventDao.getAll());

		if (country.equals("ALL")) {

			if (eventDao.getAll().size() > 0) {
//				System.out.println(eventDao.getAll() + "   ALL EVENTS");
//				temp = checkIfICanSignForTour(eventDao.getAll());
				temp = eventDao.getAll();
				System.out.println("ALL EVENTS AFTER CHECK : " + temp);
				allEvents = temp;
//				for (Event e : eventDao.getAll()) {
//					if (checkIfICanSignForTour(e) == true) {
//						temp.add(e);
//						allEvents.add(e);
//					}
//				}
			}

		} else {
			List<Event> eventsByCountry = eventDao.getAllEventsByCountry(country);
			System.out.println(eventDao.getAll() + "   ALL EVENTS V ELSE");
			if (eventsByCountry.size() > 0) {
				temp = eventsByCountry;
//				temp = checkIfICanSignForTour(eventsByCountry);
//				for (Event e : eventsByCountry) {
//					if (checkIfICanSignForTour(e) == true) {
//						temp.add(e);
//						allEvents.add(e);
//					}
//				}
//				allEvents = temp;
//				allEvents = eventsByCountry;
			}
		}
		System.out.println("TEMP after country :   " + temp);

		// month
		List<Event> eventsByMonth = new ArrayList<>();
//		if (month.equals("ALL")) {
//			eventsByMonth = eventDao.getAll();
//			System.out.println("MONTH JE 'ALL'");
//
//		}
//		System.out.println("MONTH : " + month);
		if (!month.equals("ALL")) {
			eventsByMonth = eventDao.getAllByMonth(parseMonthToInt(month));
//			System.out.println("MONTH : " + month);
			System.out.println("EVENTS BY MONTH :   " + eventsByMonth);
			if (temp.size() > 0 && eventsByMonth.size() > 0) {

				for (Event e : temp) {
					if (!eventsByMonth.contains(e)) {
						allEvents.remove(e);
						System.out.println("E :   " + e);
					}
				}

				temp = allEvents;
			}
		}

		System.out.println("TEMP after month :   " + temp);

		// guideman
		List<Event> eventsByGuideman = new ArrayList<>();
//		if (guideman.equals("ALL")) {
//			eventsByGuideman = eventDao.getAll();
//		}
		System.out.println("GUIDEMAN : " + guideman);
		if (!guideman.equals("ALL")) {
			String[] nAs = guideman.split(" ");
			System.out.println(nAs[0]);
			System.out.println(nAs[1]);
			eventsByGuideman = eventDao.getAllEventsByGuideman(nAs[0], nAs[1]);
			System.out.println("GUIDEMAN : " + guideman);
			System.out.println("EVENTS BY GUIDEMAN :   " + eventsByGuideman);
			if (temp.size() > 0 && eventsByGuideman.size() > 0) {
				for (Event e : temp) {
					if (!eventsByGuideman.contains(e)) {
						allEvents.remove(e);
					}
				}

				temp = allEvents;
			}
		}

		System.out.println("TEMP after guideman :   " + temp);

		// price
		List<Event> eventsByPrice = new ArrayList<>();
//		if (price.equals("100")) {
//			eventsByPrice = eventDao.getAll();
//		}
		System.out.println("PRICE : " + price);
		if (!price.equals("100")) {
			eventsByPrice = eventDao.getAllEventsWithPriceLowerThan(Integer.parseInt(price));
			System.out.println("PRICE : " + price);
			System.out.println("EVENTS BY PRICE :   " + eventsByPrice);
			if (temp.size() > 0 && eventsByPrice.size() > 0) {
				for (Event e : temp) {
					if (!eventsByPrice.contains(e)) {
						allEvents.remove(e);
					}
				}

				temp = allEvents;
			}
		}
		System.out.println("TEMP after price :   " + temp);

		///////

		if (temp.size() > 0) {
			// allEvents = checkIfICanSignForTour(temp); // asi daco zle s tou metodou alebo ja som jelen
			System.out.println("AFTER CHECK : " + allEvents);
			for (Event e : allEvents) {
				// allEvents.add(e);
				Tour t = tourDao.getById(e.getTourId());
				String[] dt = e.getDateOfTour().toString().split("T");
				String[] date = dt[0].split("-");
				String time = dt[1];
				StringBuilder sbDate = new StringBuilder();
				sbDate.append(date[2]);
				sbDate.append(".");
				sbDate.append(date[1]);
				sbDate.append(".");
				sbDate.append(date[0]);
				String s = "Title : " + t.getTitle() + ",        date of tour : " + sbDate + ",        time of tour : "
						+ time + ",        price : " + e.getPrice() + ",        event id : " + e.getId();
				displayedTours.add(s);
				filteredToursListView.setMouseTransparent(false);
				showTourButton.setDisable(false);
			}
		} else {
			displayedTours.add("No tours found");
			filteredToursListView.setMouseTransparent(true);
			showTourButton.setDisable(true);
		}

		filteredToursListView.setItems(FXCollections.observableArrayList(displayedTours));

	}

	private Event getEventFromListView() {
		String s;
		s = filteredToursListView.getSelectionModel().getSelectedItem();
		String[] temp1 = s.split(" ");
		String eventIdString = temp1[temp1.length - 1];
		Long eventIdLong = Long.parseLong(eventIdString);
		return eventDao.getById(eventIdLong);
	}

	@FXML
	void showTourButtonAction(ActionEvent event) {
		Event e1 = new Event();
		try {
			e1 = getEventFromListView();
		} catch (NullPointerException ex) {
			showAlert(Alert.AlertType.WARNING, owner, "Warning!", "Please select row from list !");
			return;
		}
		Tour t1 = tourDao.getById(e1.getTourId());
		ShowTour.INSTANCE.setLoggedEvent(e1);
		ShowTour.INSTANCE.setLoggedTour(t1);
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(ShowTourController.class.getResource("showTour.fxml"));
			Stage stage = new Stage();
			fxmlLoader.setController(new ShowTourController());
			Scene scene = new Scene(fxmlLoader.load());
			stage.setTitle("Guideman");
			stage.getIcons().add(new Image("sk/upjs/paz1c/guideman/controllers/G-logo light.png"));
			stage.setScene(scene);
			stage.show();
			showFilterTableButton.getScene().getWindow().hide();
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

	private static void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.initOwner(owner);
		alert.show();
	}

}
