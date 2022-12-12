package sk.upjs.paz1c.guideman.models;

import java.sql.Blob;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.text.DateFormatter;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sk.upjs.paz1c.guideman.storage.User;


public class UserFxModel {

	private Long id;
	private StringProperty name = new SimpleStringProperty();
	private StringProperty surname = new SimpleStringProperty();
	private StringProperty email = new SimpleStringProperty();
	private StringProperty telNumber = new SimpleStringProperty();
	private ObjectProperty<LocalDateTime> birthdate = new SimpleObjectProperty<>();
	private StringProperty login = new SimpleStringProperty();
	private StringProperty password = new SimpleStringProperty();
	private Blob image;

	private ObservableList<User> users;
	
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

	public UserFxModel() {
		users = FXCollections.observableArrayList();
	}

	public UserFxModel(User user) {
		this.id = user.getId();
		name.set(user.getName());
		surname.set(user.getSurname());
		email.set(user.getEmail());
		if (user.getTelNumber() != null) {
			telNumber.set(user.getTelNumber());
		}
		birthdate.set(LocalDateTime.parse(user.getBirthdate().toString(), formatter));
		login.set(user.getLogin());
		password.set(user.getPassword());
		if (user.getImage() != null) {
			this.image = user.getImage();
		}

	}

	public ObservableList<User> getUsersModel() {
		return users;
	}
	

	public StringProperty nameProperty() {
		return name;
	}
	
	public StringProperty surnameProperty() {
		return surname;
	}
	
	public StringProperty emailProperty() {
		return email;
	}
	
	public StringProperty telNumberProperty() {
		return name;
	}
	
	public ObjectProperty<LocalDateTime> birthdateProperty() {
		return birthdate;
	}
	
	public LocalDate getBirthdate() {
		return birthdate.get().toLocalDate();
	}
	
	public void setBirthdate(LocalDate localDate) {
		this.birthdate.set(LocalDateTime.parse(localDate.toString(), formatter));
	}

	public StringProperty loginProperty() {
		return login;
	}
	
	public StringProperty passwordProperty() {
		return password;
	}
	
}
