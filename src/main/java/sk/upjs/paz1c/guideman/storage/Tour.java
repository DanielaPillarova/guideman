package sk.upjs.paz1c.guideman.storage;

import java.sql.Blob;

public class Tour {

	private Long id;
	private String title;
	private String bio; // idk, mozno String? text je interface a take nechceme
	private int maxSlots;
	private Blob image;
	private Long guidemanId;
	// private Location location;

	public Tour() {

	}

	// Location
	public Tour(Long guideman) {
		this.guidemanId = guideman;
		// this.location = location;
	}

	public Tour(Long id, String title, String bio, int maxSlots, Blob image, Long guideman) {
		this.id = id;
		this.title = title;
		this.bio = bio;
		this.maxSlots = maxSlots;
		this.image = image;
		this.guidemanId = guideman;
	}

	public Tour(Long id, String title, String bio, int maxSlots, Long guideman) {
		this.id = id;
		this.title = title;
		this.bio = bio;
		this.maxSlots = maxSlots;
		this.guidemanId = guideman;
	}

	public Tour(Long id, String title, int maxSlots) {
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

	public int getMaxSlots() {
		return maxSlots;
	}

	public void setMaxSlots(int maxSlots) {
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

	@Override
	public String toString() {
		return "Tour [id=" + id + ", title=" + title + ", bio=" + bio + ", maxSlots=" + maxSlots + ", image=" + image
				+ ", guideman=" + guidemanId + "]";
	}

	// TODO roman
}
