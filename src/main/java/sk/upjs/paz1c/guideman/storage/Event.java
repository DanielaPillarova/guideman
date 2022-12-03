package sk.upjs.paz1c.guideman.storage;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Event {
	
	public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.M.yyyy HH:mm");

	private Long id;
	private LocalDateTime dateOfTour;
	private LocalTime duration;
	private Double price;
	private Tour tour;
	private List<User> tourists;
	
	// added
	private List<Integer> ratings;
	private List<String> reviews;
	
	
	public Event() {
	}

	public Event(Tour tour) {
		this.tour = tour;
	}
	
	public Event(Long id) {
		this.id = id;
	}
	
	public Event(Long id, LocalDateTime dateOfTour, LocalTime duration, Double price, Tour tour) {
		this.id = id;
		this.dateOfTour = dateOfTour;
		this.duration = duration;
		this.price = price;
		this.tour = tour;
	}
		
	public Event(Long id, LocalDateTime dateOfTour, LocalTime duration, Double price, Tour tour, List<User> tourists) {
		this.id = id;
		this.dateOfTour = dateOfTour;
		this.duration = duration;
		this.price = price;
		this.tour = tour;
		if (tourists != null) {
			this.tourists = tourists;			
		}
	}

	public Event(Long id, LocalDateTime dateOfTour, LocalTime duration, Double price, Tour tour, List<User> tourists,
			List<Integer> ratings, List<String> reviews) {
		this.id = id;
		this.dateOfTour = dateOfTour;
		this.duration = duration;
		this.price = price;
		this.tour = tour;
		this.tourists = tourists;
		this.ratings = ratings;
		this.reviews = reviews;
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getDateOfTour() {
		return dateOfTour;
	}

	public void setDateOfTour(LocalDateTime dateOfTour) {
		this.dateOfTour = dateOfTour;
	}

	public LocalTime getDuration() {
		return duration;
	}

	public void setDuration(LocalTime duration) {
		this.duration = duration;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Tour getTour() {
		return tour;
	}

	public void setTour(Tour tour) {
		this.tour = tour;
	}

	public List<User> getTourists() {
		return tourists;
	}

	public void setTourists(List<User> tourists) {
		this.tourists = tourists;
	}

	public List<Integer> getRatings() {
		return ratings;
	}

	public void setRatings(List<Integer> ratings) {
		this.ratings = ratings;
	}

	public List<String> getReviews() {
		return reviews;
	}

	public void setReviews(List<String> reviews) {
		this.reviews = reviews;
	}

	@Override
	public String toString() {
		return dateOfTour.format(formatter);
	}

}
