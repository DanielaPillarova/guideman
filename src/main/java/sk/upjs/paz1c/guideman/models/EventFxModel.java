package sk.upjs.paz1c.guideman.models;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;
import sk.upjs.paz1c.guideman.storage.Event;
import sk.upjs.paz1c.guideman.storage.Tour;

public class EventFxModel {
	
	private Long id;
	private ObjectProperty<LocalDateTime> dateOfour;
	private ObjectProperty<LocalDate> duration;
	private Double price;
	private Long tourId;
	
	private ObservableList<Tour> myEvents;
	private ObservableList<Tour> pastEvents;
	private ObservableList<Tour> futureEvents;
	private ObservableList<Tour> eventsWhereIAmGuideman;
	
	public EventFxModel(Event event) {
		this.id = event.getId();
		
		
	}


}
