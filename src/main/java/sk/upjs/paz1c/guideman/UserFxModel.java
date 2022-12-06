package sk.upjs.paz1c.guideman;

import java.sql.Blob;
import java.time.LocalDate;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sk.upjs.paz1c.guideman.storage.User;

// idk ci je to dobre
// najskor nie, nemam tusenia co tu robit

public class UserFxModel {

	private Long id;
	private StringProperty name = new SimpleStringProperty();
	private StringProperty surname = new SimpleStringProperty();
	private StringProperty email = new SimpleStringProperty();
	private StringProperty telNumber = new SimpleStringProperty();
	private LocalDate birthdate;
	private StringProperty login = new SimpleStringProperty();
	private StringProperty password = new SimpleStringProperty();
	private Blob image;

	private ObservableList<User> users;

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
		this.birthdate = user.getBirthdate();
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

}
