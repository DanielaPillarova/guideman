package sk.upjs.paz1c.guideman.storage;

import java.sql.Blob;

public class Tour {

	private Long id;
	private String title;
	private String bio; // idk, mozno String? text je interface a take nechceme
	private Integer maxSlots;
	private Blob image;
	private User guideman;
	private Location location;
	// private Location location;

	public Tour() {

	}

	public Tour(Location location) {
		this.location = location;
	}

	public Tour(User guideman) {
		this.guideman = guideman;
	}

	public Tour(Long id, String title, String bio, Integer maxSlots, Blob image, User guideman, Location location) {
		this.id = id;
		this.title = title;
		this.bio = bio;
		this.maxSlots = maxSlots;
		this.image = image;
		this.guideman = guideman;
		this.location = location;
	}

	public Tour(Long id, String title, String bio, Integer maxSlots, User guideman, Location location) {
		this.id = id;
		this.title = title;
		this.bio = bio;
		this.maxSlots = maxSlots;
		this.guideman = guideman;
		this.location = location;
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

	public User getGuideman() {
		return guideman;
	}

	public void setGuideman(User guideman) {
		this.guideman = guideman;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	@Override
	public String toString() {
		return "Tour [id=" + id + ", title=" + title + ", bio=" + bio + ", maxSlots=" + maxSlots + ", image=" + image
				+ ", guidemanId=" + guideman + ", locationId=" + location + "]";
	}

	// TODO roman
}
