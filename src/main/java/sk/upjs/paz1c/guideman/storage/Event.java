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
	private float price;
	private Tour tour;
	private List<User> tourists;
	
	
	public Event() {
	}

	public Event(Tour tour) {
		this.tour = tour;
	}
	
	public Event(long id) {
		this.id = id;
	}
	
	public Event(Long id, LocalDateTime dateOfTour, LocalTime duration, float price, int rating, String review) {
		this.id = id;
		this.dateOfTour = dateOfTour;
		this.duration = duration;
		this.price = price;
	}
		
	public Event(Long id, LocalDateTime dateOfTour, LocalTime duration, float price, Tour tour, List<User> tourists) {
		super();
		this.id = id;
		this.dateOfTour = dateOfTour;
		this.duration = duration;
		this.price = price;
		this.tour = tour;
		if (tourists != null) {
			this.tourists = tourists;			
		}
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

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
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

	@Override
	public String toString() {
		return dateOfTour.format(formatter);
	}

}
