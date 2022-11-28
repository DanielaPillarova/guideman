package sk.upjs.paz1c.guideman.storage;

public class Location {

	private Long id;
	private String country;
	private String city;
	private String street;
	private Long street_number;

	public Location() {

	}

	public Location(Long id, String country, String city, String street, Long street_number) {
		this.id = id;
		this.country = country;
		this.city = city;
		this.street = street;
		this.street_number = street_number;
	}

	public Location(String country, String city, String street, Long street_number) {
		this.country = country;
		this.city = city;
		this.street = street;
		this.street_number = street_number;
	}

	public Location(Long id, String country, String city, String street) {
		this.id = id;
		this.country = country;
		this.city = city;
		this.street = street;
	}

	public Location(String country, String city, String street) {
		this.country = country;
		this.city = city;
		this.street = street;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public Long getStreet_number() {
		return street_number;
	}

	public void setStreet_number(Long street_number) {
		this.street_number = street_number;
	}

	@Override
	public String toString() {
		return "Location [id=" + id + ", country=" + country + ", city=" + city + ", street=" + street
				+ ", street_number=" + street_number + "]";
	}

}
