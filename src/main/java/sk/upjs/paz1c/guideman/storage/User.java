package sk.upjs.paz1c.guideman.storage;

import java.time.LocalDate;
import java.sql.Blob;

public class User {

	private Long id;
	private String name;
	private String surname;
	private String email;
	private String telNumber;
	private LocalDate birthdate;
	private String login;
	private String password;
	private Blob image;

	public User() {
	}

	public User(String login, String password) {
		this.login = login;
		this.password = password;
	}

	public User(Long id, String name, String surname, String email, String telNumber, LocalDate birthdate, Blob image) {
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.telNumber = telNumber;
		this.birthdate = birthdate;
		this.image = image;
	}

	public User(String name, String surname, String email, String telNumber, LocalDate birthdate, Blob image) {
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.telNumber = telNumber;
		this.birthdate = birthdate;
		this.image = image;
	}

	public User(String name, String surname, String email, String telNumber, LocalDate birthdate, String login,
			String password, Blob image) {
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.telNumber = telNumber;
		this.birthdate = birthdate;
		this.login = login;
		this.password = password;
		this.image = image;
	}

	public User(Long id, String name, String surname, String email, String telNumber, LocalDate birthdate, String login,
			String password, Blob image) {
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.telNumber = telNumber;
		this.birthdate = birthdate;
		this.login = login;
		this.password = password;
		this.image = image;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelNumber() {
		return telNumber;
	}

	public void setTelNumber(String telNumber) {
		this.telNumber = telNumber;
	}

	public LocalDate getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(LocalDate birthdate) {
		this.birthdate = birthdate;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Blob getImage() {
		return image;
	}

	public void setImage(Blob image) {
		this.image = image;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", surname=" + surname + ", email=" + email + ", telNumber="
				+ telNumber + ", birthdate=" + birthdate + ", login=" + login + ", password=" + password + ", image="
				+ image + "]";
	}

}
