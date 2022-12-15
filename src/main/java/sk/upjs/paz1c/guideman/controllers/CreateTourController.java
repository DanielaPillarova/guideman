package sk.upjs.paz1c.guideman.controllers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Blob;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import sk.upjs.paz1c.guideman.models.TourFxModel;
import sk.upjs.paz1c.guideman.storage.DaoFactory;
import sk.upjs.paz1c.guideman.storage.EntityNotFoundException;
import sk.upjs.paz1c.guideman.storage.Event;
import sk.upjs.paz1c.guideman.storage.EventDao;
import sk.upjs.paz1c.guideman.storage.Location;
import sk.upjs.paz1c.guideman.storage.LocationDao;
import sk.upjs.paz1c.guideman.storage.Tour;
import sk.upjs.paz1c.guideman.storage.TourDao;
import sk.upjs.paz1c.guideman.storage.User;
import sk.upjs.paz1c.guideman.storage.UserDao;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Window;

public class CreateTourController {

	private byte[] bytes = null;
	private String nameOfFile;
	private String filePath;
	private File selectedFile;
	private Location savedLocation;
	private Event savedEvent;
	private Tour savedTour;
	private Window owner;

	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

	private UserDao userDao = DaoFactory.INSTANCE.getUserDao();
	private TourDao tourDao = DaoFactory.INSTANCE.getTourDao();
	private LocationDao locationDao = DaoFactory.INSTANCE.getLocationDao();
	private EventDao eventDao = DaoFactory.INSTANCE.getEventDao();

	private TourFxModel tourModel;
	private ObservableList<Tour> comboBoxModel;
	private Tour tourToFill = null;

	public CreateTourController() {
		this.tourModel = new TourFxModel();
	}

	@FXML
	private TextArea bioTextArea;

	@FXML
	private ComboBox<String> chooseTourComboBox;

	@FXML
	private TextField cityTextField;

	@FXML
	private TextField countryTextField;

	@FXML
	private Button createButton;

	@FXML
	private Button createTourButton;

	@FXML
	private TextField dateAndTimeOfTourTextField;

	@FXML
	private TextField durationTextField;

	@FXML
	private Button logOutButton;

	@FXML
	private Button myProfileButton;

	@FXML
	private Button myToursButton;

	@FXML
	private Label noSelectedImageLabel;

	@FXML
	private TextField numberOfPeopleTextField;

	@FXML
	private TextField priceTextField;

	@FXML
	private Button searchTourButton;

	@FXML
	private Button selectImageButton;

	@FXML
	private TextField streetNumberTextField;

	@FXML
	private TextField streetTextField;

	@FXML
	private TextField titleTextField;

	@FXML
	void createButtonAction(ActionEvent event) throws SerialException, SQLException {
		Blob blobisko = null;
		if (bytes != null) {
			blobisko = new SerialBlob(bytes);

			System.out.println(blobisko.length() + " velkost blobu");

			// velke nez 16 mb
			if (blobisko.length() > 16000000L) {
				System.out.println("Error");
				showAlert(Alert.AlertType.ERROR, owner, "Error", "Please upload smaller image !");
				// owner neexistuje, robil problem, ak toto bude robit problem , tak treba tu
				// dat infobox
				return;
			}
		}

		String title = titleTextField.getText();
		String bio = bioTextArea.getText();
		String maxPeopleString = numberOfPeopleTextField.getText(); // int
		String country = countryTextField.getText();
		String city = cityTextField.getText();
		String street = streetTextField.getText();
		String streetNumberString = streetNumberTextField.getText(); // int
		String dateAndTimeOfTourString = dateAndTimeOfTourTextField.getText();
		String durationString = durationTextField.getText();
		String priceString = priceTextField.getText();

		Integer streetNumber = null;
		if (streetNumberString != "") {
			try {
				streetNumber = Integer.parseInt(streetNumberString);
			} catch (NumberFormatException e) {
				infoBox("Warning", null, "Wrong format, check your street number");
				return;
			}
		}
		Location location = new Location(country, city, street, streetNumber);
		// este upravit ked uz taka existuje

		// ulozime location najprv potom tour a tak event

		boolean sameLocation = false;

		List<Location> locations = locationDao.getAll();

		// chcem tu z databazy ak vytvaram nejaku ktora uz je v databaze
		Location locationFromDB = null;

		for (Location eachLocation : locations) {
			String locationCountry = eachLocation.getCountry();
			String locationCity = eachLocation.getCity();
			String locationStreet = eachLocation.getStreet();
			Integer locationStreetNumber = eachLocation.getStreet_number();

			if (locationCountry.equals(location.getCountry()) && locationCity.equals(location.getCity())
					&& locationStreet.equals(location.getStreet())
					&& locationStreetNumber == location.getStreet_number()) {
				System.out.println("su rovnake");
				locationFromDB = eachLocation;
				sameLocation = true;
			}

		}

		try {
			// location
			Long idLocation;
			if (sameLocation == false) {
				savedLocation = locationDao.save(location);
				System.out.println(savedLocation + " = saved location");
				idLocation = savedLocation.getId();
			} else {
				savedLocation = locationFromDB;
				System.out.println(savedLocation + " = saved location");
				idLocation = locationFromDB.getId();
			}
			// tour
			if (bio == "") {
				bio = "No bio";
			}
			Long maxPeople = (long) Integer.parseInt(maxPeopleString);
			User user = userDao.getById(LoggedUser.INSTANCE.getLoggedUser().getId());
			Tour tour = new Tour(title, bio, maxPeople, idLocation, user.getId(), blobisko);
			savedTour = tourDao.save(tour);
			//
			// event
			LocalDateTime dateAndTimeOfTour = LocalDateTime.parse(dateAndTimeOfTourString, formatter);
			LocalDateTime now = LocalDateTime.now();
			if (now.isAfter(dateAndTimeOfTour)) {
				throw new DateTimeException("Date needs to be set in the future"); // ?
			}

			LocalTime duration = LocalTime.parse(durationString);
			double price = Double.parseDouble(priceString);
			Event newEvent = new Event(dateAndTimeOfTour, duration, price, savedTour.getId());

			savedEvent = eventDao.save(newEvent);
			//
			// infoBox("Tour has been successfuly created", null, "Congratulations");

			System.out.println(tour);
			System.out.println(tour.getMaxSlots() + " max sloty");
			showAlert(Alert.AlertType.CONFIRMATION, owner, "Success", "Tour has been successfully created !");

			// reset vsetkeho
			titleTextField.setText("");
			bioTextArea.setText("");
			numberOfPeopleTextField.setText(""); // int
			countryTextField.setText("");
			cityTextField.setText("");
			streetTextField.setText("");
			streetNumberTextField.setText(""); // int
			dateAndTimeOfTourTextField.setText("");
			durationTextField.setText("");
			priceTextField.setText("");

			bytes = null;
			noSelectedImageLabel.setText("No selected file");

		} catch (NullPointerException e) {
			infoBox("Wrong format", null, "Warning");
			if (savedEvent != null) {
				e.printStackTrace();
				eventDao.delete(savedEvent.getId());
				savedEvent = null;
			}
			if (savedTour != null) {
				e.printStackTrace();
				tourDao.delete(savedTour.getId());
				savedTour = null;
			}
			if (savedLocation != null && sameLocation == false) {
				e.printStackTrace();
				System.out.println(savedLocation.getId());
				locationDao.delete(savedLocation.getId());
				savedLocation = null;
			}

			return;
		} catch (NumberFormatException e) {
			infoBox("Wrong number format", null, "Warning");
			if (savedEvent != null) {
				eventDao.delete(savedEvent.getId());
				savedEvent = null;
			}
			if (savedTour != null) {
				tourDao.delete(savedTour.getId());
				savedTour = null;
			}
			if (savedLocation != null && sameLocation == false) {
				System.out.println(savedLocation.getId());
				locationDao.delete(savedLocation.getId());
				savedLocation = null;
			}

			return;
		} catch (DateTimeParseException e) {
			infoBox("Wrong date format", null, "Warning");
			if (savedEvent != null) {
				eventDao.delete(savedEvent.getId());
				savedEvent = null;
			}
			if (savedTour != null) {
				tourDao.delete(savedTour.getId());
				savedTour = null;
			}
			if (savedLocation != null && sameLocation == false) {
				System.out.println(savedLocation.getId());
				locationDao.delete(savedLocation.getId());
				savedLocation = null;
			}

			return;
		} catch (DateTimeException e) {
			infoBox("Date needs to be set in the future ", null, "Warning");
			if (savedEvent != null) {
				eventDao.delete(savedEvent.getId());
				savedEvent = null;
			}
			if (savedTour != null) {
				tourDao.delete(savedTour.getId());
				savedTour = null;
			}
			if (savedLocation != null && sameLocation == false) {
				System.out.println(savedLocation.getId());
				locationDao.delete(savedLocation.getId());
				savedLocation = null;
			}
			return;
		}

	}

	@FXML
	void selectImageButtonAction(ActionEvent event) throws IOException {

		bytes = null;
		noSelectedImageLabel.setText("No selected file");

		if (event.getSource() == selectImageButton) {

			JFileChooser fileChooser = new JFileChooser();
			// System.setProperty("apple.awt.fileDialogForDirectories", "true");
			// Danke mac nejde
			// System.getProperty("os.name")
			// https://community.oracle.com/tech/developers/discussion/2508757/jfilechooser-problem-on-mac-os
			fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
			FileNameExtensionFilter filter = new FileNameExtensionFilter("*.image", "jpg", "png");
			fileChooser.addChoosableFileFilter(filter);

			int response = fileChooser.showSaveDialog(null); // select file to open

			if (response == JFileChooser.APPROVE_OPTION) {
				selectedFile = new File(fileChooser.getSelectedFile().getAbsolutePath()); // File , jeho cesta
				filePath = selectedFile.getAbsolutePath(); // string
				if (filePath.endsWith(".jpg") || filePath.endsWith(".JPG") || filePath.endsWith(".PNG")
						|| filePath.endsWith(".png")) {
					System.out.println(filePath);
					bytes = Files.readAllBytes(Paths.get(filePath));
					nameOfFile = selectedFile.getName();
					noSelectedImageLabel.setText(nameOfFile);
				} else {
					infoBox("Please Select Image File", null, "Warning !");
				}
			}
		}
	}

	@FXML
	void myProfileButtonAction(ActionEvent event) {
		Menu.INSTANCE.openMyProfile(createButton);
	}

	@FXML
	void myToursButtonAction(ActionEvent event) {
		Menu.INSTANCE.openMyTours(createButton);
	}

	@FXML
	void searchTourButtonAction(ActionEvent event) {
		Menu.INSTANCE.openSearchTour(createButton);
	}

	@FXML
	void createTourButtonAction(ActionEvent event) {
		System.out.println("create");
	}

	@FXML
	void logOutButtonAction(ActionEvent event) {
		Menu.INSTANCE.logOut(createButton);
	}

	@FXML
	void initialize() {

		BooleanBinding bb = new BooleanBinding() {
			{
				super.bind(titleTextField.textProperty(), numberOfPeopleTextField.textProperty(),
						countryTextField.textProperty(), cityTextField.textProperty(), streetTextField.textProperty(),
						dateAndTimeOfTourTextField.textProperty(), durationTextField.textProperty(),
						priceTextField.textProperty());
			}

			@Override
			protected boolean computeValue() {
				return (titleTextField.getText().isEmpty() || numberOfPeopleTextField.getText().isEmpty()
						|| countryTextField.getText().isEmpty() || cityTextField.getText().isEmpty()
						|| streetTextField.getText().isEmpty() || dateAndTimeOfTourTextField.getText().isEmpty()
						|| durationTextField.getText().isEmpty() || priceTextField.getText().isEmpty());
			}
		};

		createButton.disableProperty().bind(bb);

		// comboBox
		// ObservableList<Tour> comboBoxTours =
		// tourModel.getToursWhereIAmGuidemanModel();
		Long idLoggedUser = LoggedUser.INSTANCE.getLoggedUser().getId();
		// comboBoxModel =
		// FXCollections.observableArrayList(tourDao.getAllToursWhereIAmGuideman(idLoggedUser));
		List<Tour> toursTemp = tourDao.getAllToursByGuideman(idLoggedUser);
		List<String> titles = new ArrayList<>();
		for (Tour tour : toursTemp) {
			titles.add(tour.getTitle());
		}

		chooseTourComboBox.setItems(FXCollections.observableArrayList(titles));
		chooseTourComboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
			tourToFill = null;

			String chosen = chooseTourComboBox.getValue();
			for (Tour tour : toursTemp) {
				String tourString = tour.getTitle();

				if (chosen == tourString) {
					tourToFill = tour;
				}
			}
			System.out.println(tourToFill + " vybraty tour");
			if (tourToFill != null) {
				selectImageButton.setDisable(true);
				
				String chosenTitle = tourToFill.getTitle();
				titleTextField.setText(chosenTitle);
				titleTextField.setMouseTransparent(true);
				titleTextField.setStyle("-fx-background-color: #f2f2f2;");
				
				String chosenBio = tourToFill.getBio();
				if (!chosenBio.equals("No bio")) {
					bioTextArea.setText(chosenBio);
				} else {
					bioTextArea.setText("");
				}
				bioTextArea.setMouseTransparent(true);
				bioTextArea.lookup(".content").setStyle("-fx-background-color: #f2f2f2;");
				bioTextArea.setStyle("-fx-background-color: #f2f2f2;");

				String chosenMaxSlots = tourToFill.getMaxSlots().toString();
				numberOfPeopleTextField.setText(chosenMaxSlots);
				numberOfPeopleTextField.setMouseTransparent(true);
				numberOfPeopleTextField.setStyle("-fx-background-color: #f2f2f2;");

				Location locationToFill = locationDao.getById(tourToFill.getLocationId());

				String chosenCountry = locationToFill.getCountry();
				countryTextField.setText(chosenCountry);
				countryTextField.setMouseTransparent(true);
				countryTextField.setStyle("-fx-background-color: #f2f2f2;");

				String chosenCity = locationToFill.getCity();
				cityTextField.setText(chosenCity);
				cityTextField.setMouseTransparent(true);
				cityTextField.setStyle("-fx-background-color: #f2f2f2;");

				String chosenStreet = locationToFill.getStreet();
				streetTextField.setText(chosenStreet);
				streetTextField.setMouseTransparent(true);
				streetTextField.setStyle("-fx-background-color: #f2f2f2;");

				String chosenStreetNumber = locationToFill.getStreet_number().toString();
				streetNumberTextField.setText(chosenStreetNumber);
				streetNumberTextField.setMouseTransparent(true);
				streetNumberTextField.setStyle("-fx-background-color: #f2f2f2;");

			}

		});

	}

	public static void infoBox(String infoMessage, String headerText, String title) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setContentText(infoMessage);
		alert.setTitle(title);
		alert.setHeaderText(headerText);
		alert.showAndWait();
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
