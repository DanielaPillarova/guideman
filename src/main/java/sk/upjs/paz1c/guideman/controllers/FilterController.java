package sk.upjs.paz1c.guideman.controllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.stage.Window;
import sk.upjs.paz1c.guideman.storage.DaoFactory;
import sk.upjs.paz1c.guideman.storage.Location;
import sk.upjs.paz1c.guideman.storage.LocationDao;
import sk.upjs.paz1c.guideman.storage.User;
import sk.upjs.paz1c.guideman.storage.UserDao;

public class FilterController {

	private LocationDao locationDao;
	private UserDao userDao;
	private Window owner;

	@FXML
	private ComboBox<String> filterByCountryComboBox;

	@FXML
	private ComboBox<String> filterByGuidemanComboBox;

	@FXML
	private ComboBox<String> filterByMonthComboBox;

	@FXML
	private Slider filterByPriceSlider;

	@FXML
	private Label priceLabel;

	@FXML
	private Button saveAndFilterButton;

	@FXML
	void initialize() {
		locationDao = DaoFactory.INSTANCE.getLocationDao();
		userDao = DaoFactory.INSTANCE.getUserDao();
		fillCountryComboBox();
		filterByCountryComboBox.getSelectionModel().selectFirst();
		fillMonthComboBox();
		filterByMonthComboBox.getSelectionModel().selectFirst();
		fillGuidemanComboBox();
		filterByGuidemanComboBox.getSelectionModel().selectFirst();

		filterByPriceSlider.valueProperty()
				.addListener((ObservableValue<? extends Number> num, Number oldVal, Number newVal) -> {
					Integer roundedNewVal = (int) Math.round(Double.valueOf(newVal.toString()));
					priceLabel.setText(roundedNewVal.toString());
				});

		Filter.INSTANCE.setCountry("null");
		Filter.INSTANCE.setMonth("null");
		Filter.INSTANCE.setGuideman("null");
		Filter.INSTANCE.setPrice("0");
	}

	private void fillCountryComboBox() {
		List<Location> locationsFromDB = locationDao.getAll();
		List<String> locations = new ArrayList<>();
		locations.add("ALL");
		for (Location l : locationsFromDB) {
			if (!locations.contains(l.getCountry())) {
				locations.add(l.getCountry());
			}
		}
		filterByCountryComboBox.getSelectionModel().selectFirst();
		filterByCountryComboBox.setItems(FXCollections.observableArrayList(locations));

	}

	private void fillMonthComboBox() {
		List<String> months = new ArrayList<>();
		months.add("ALL");
		months.add("January");
		months.add("February");
		months.add("March");
		months.add("April");
		months.add("May");
		months.add("June");
		months.add("July");
		months.add("August");
		months.add("September");
		months.add("October");
		months.add("November");
		months.add("December");
		filterByMonthComboBox.getSelectionModel().selectFirst();
		filterByMonthComboBox.setItems(FXCollections.observableArrayList(months));

	}

	private void fillGuidemanComboBox() {
		List<User> guidemans = userDao.getAllGuidemans();
		List<String> nameAndSurname = new ArrayList<>();
		nameAndSurname.add("ALL");
		for (User u : guidemans) {
			nameAndSurname.add(u.getName() + " " + u.getSurname());
		}
		filterByGuidemanComboBox.getSelectionModel().selectFirst();
		filterByGuidemanComboBox.setItems(FXCollections.observableArrayList(nameAndSurname));

	}

	@FXML
	void saveAndFilterButtonAction(ActionEvent event) {
//		System.out.println("###### logged");
//		System.out.println(Filter.INSTANCE.getCountry());
//		System.out.println(Filter.INSTANCE.getMonth());
//		System.out.println(Filter.INSTANCE.getGuideman());
//		System.out.println(Filter.INSTANCE.getPrice());
//		System.out.println("####");

		String country = filterByCountryComboBox.getSelectionModel().getSelectedItem();
		String month = filterByMonthComboBox.getSelectionModel().getSelectedItem();
		String guideman = filterByGuidemanComboBox.getSelectionModel().getSelectedItem();
		String price = priceLabel.getText();

//		System.out.println("###### from selection model");
//		System.out.println(country);
//		System.out.println(month);
//		System.out.println(guideman);
//		System.out.println(price);
//		System.out.println("####");

		String countryLogged = Filter.INSTANCE.getCountry();
		String monthLogged = Filter.INSTANCE.getMonth();
		String guidemanLogged = Filter.INSTANCE.getGuideman();
		String priceLogged = Filter.INSTANCE.getPrice();

		String countryNew = "";
		String monthNew = "";
		String guidemanNew = "";
		String priceNew = "";

		if (countryLogged.equals("null")) {
			if (country != null) {
				countryNew = country;
			}
			if (country == null) {
				countryNew = "null";
			}
		}
		if (!countryLogged.equals("null")) {
			if (country != null) {
				if (countryLogged.equals(country)) {
					System.out.println("SAME COUNTRY JE TRUE");
					countryNew = country;
				}
			}
			if (country == null) {
				countryNew = "null";
			}
		}
		/////////////

		if (monthLogged.equals("null")) {
			if (month != null) {
				monthNew = month;
			}
			if (month == null) {
				monthNew = "null";
			}
		}
		if (!monthLogged.equals("null")) {
			if (month != null) {
				if (monthLogged.equals(month)) {
					System.out.println("SAME MONTH JE TRUE");
					monthNew = month;
				}
			}
			if (month == null) {
				monthNew = "null";
			}
		}
		////////////

		if (guidemanLogged.equals("null")) {
			if (guideman != null) {
				guidemanNew = guideman;
			}
			if (guideman == null) {
				guidemanNew = "null";
			}
		}
		if (!guidemanLogged.equals("null")) {
			if (guideman != null) {
				if (guidemanLogged.equals(guideman)) {
					System.out.println("SAME GUIDEMAN JE TRUE");
					guidemanNew = guideman;
				}
			}
			if (guideman == null) {
				guidemanNew = "null";
			}
		}
		//////////////

		if (priceLogged.equals("0")) {
			if (price != null) {
				priceNew = price;
			}
			if (price.equals("0")) {
				priceNew = "0";
			}
		}
		if (!priceLogged.equals("0")) {
			if (price != null) {
				if (priceLogged.equals(price)) {
					System.out.println("SAME PRICE JE TRUE");
					priceNew = price;
				}
			}

			if (price.equals("0")) {
				priceNew = "0";

			}
		}

//		System.out.println("###### new");
//		System.out.println(countryNew);
//		System.out.println(monthNew);
//		System.out.println(guidemanNew);
//		System.out.println(priceNew);
//		System.out.println("####");

		if (countryNew.equals(countryLogged) && monthNew.equals(monthLogged) && guidemanNew.equals(guidemanLogged)
				&& priceNew.equals(priceLogged)) {
			showAlert(Alert.AlertType.WARNING, owner, "Warning !", "No change has been made !");
			System.out.println("ALERT - NO CHANGE HAS BEEN MADE");
		} else {
			Filter.INSTANCE.setCountry(countryNew);
			Filter.INSTANCE.setMonth(monthNew);
			Filter.INSTANCE.setGuideman(guidemanNew);
			Filter.INSTANCE.setPrice(priceNew);
			showAlert(Alert.AlertType.INFORMATION, owner, "Success !", "Filter have been saved successfully !");
		}
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
