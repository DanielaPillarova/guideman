package sk.upjs.paz1c.guideman;

import java.sql.Blob;
import java.time.LocalDate;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sk.upjs.paz1c.guideman.storage.User;

// idk ci je to dobre

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
	}
	
	
	

}
