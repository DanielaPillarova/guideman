package sk.upjs.paz1c.guideman.models;

import java.sql.Blob;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import sk.upjs.paz1c.guideman.storage.Tour;

public class TourFxModel {
	
	private Long id;
	private StringProperty title = new SimpleStringProperty();
	private StringProperty bio = new SimpleStringProperty();
	private StringProperty maxSlots = new SimpleStringProperty();
	private Long locationId;
	private Long guidemanId;
	private Blob image;
	
	public TourFxModel(Tour tour) {
		this.id = tour.getId();
		title.set(tour.getTitle());
		bio.set(tour.getBio());
		maxSlots.set(tour.getMaxSlots().toString());

	}
}
