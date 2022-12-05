package sk.upjs.paz1c.guideman.storage;

import java.sql.Blob;

public class Tour {

	private Long id;
	private String title;
	private String bio;
	private Integer maxSlots;
	private Long locationId;
	private Long guidemanId;
	private Blob image;

	public Tour() {

	}

	public Tour(Long id, String title, String bio, Integer maxSlots, Long location, Long guideman, Blob image) {
		this.id = id;
		this.title = title;
		this.bio = bio;
		this.maxSlots = maxSlots;
		this.locationId = location;
		this.guidemanId = guideman;
		this.image = image;
	}

	public Tour(String title, String bio, Integer maxSlots, Long location, Long guideman, Blob image) {
		this.title = title;
		this.bio = bio;
		this.maxSlots = maxSlots;
		this.locationId = location;
		this.guidemanId = guideman;
		this.image = image;
	}

	public Tour(Long id, String title, String bio, Integer maxSlots, Long location, Long guideman) {
		this.id = id;
		this.title = title;
		this.bio = bio;
		this.maxSlots = maxSlots;
		this.locationId = location;
		this.guidemanId = guideman;
	}

	public Tour(Long id, String title, Integer maxSlots, Long location, Long guideman) {
		this.id = id;
		this.title = title;
		this.maxSlots = maxSlots;
		this.locationId = location;
		this.guidemanId = guideman;
	}

	public Tour(Long id, String title, Integer maxSlots) {
		this.id = id;
		this.title = title;
		this.maxSlots = maxSlots;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public Integer getMaxSlots() {
		return maxSlots;
	}

	public void setMaxSlots(Integer maxSlots) {
		this.maxSlots = maxSlots;
	}

	public Blob getImage() {
		return image;
	}

	public void setImage(Blob image) {
		this.image = image;
	}

	public Long getGuideman() {
		return guidemanId;
	}

	public void setGuideman(Long guideman) {
		this.guidemanId = guideman;
	}

	public Long getLocation() {
		return locationId;
	}

	public void setLocation(Long location) {
		this.locationId = location;
	}

	@Override
	public String toString() {
		return "Tour [id=" + id + ", title=" + title + ", bio=" + bio + ", maxSlots=" + maxSlots + ", location="
				+ locationId + ", guideman=" + guidemanId + ", image=" + image + "]";
	}
}
