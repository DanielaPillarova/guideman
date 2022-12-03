package sk.upjs.paz1c.guideman.storage;

import java.sql.Blob;

public class Tour {

	private Long id;
	private String title;
	private String bio; // idk, mozno String? text je interface a take nechceme
	private Integer maxSlots;
	private Integer location;
	private Integer guideman;
	private Blob image;

	public Tour() {

	}

	public Tour(Long id, String title, String bio, Integer maxSlots, Integer location, Integer guideman, Blob image) {
		this.id = id;
		this.title = title;
		this.bio = bio;
		this.maxSlots = maxSlots;
		this.location = location;
		this.guideman = guideman;
		this.image = image;
	}

	public Tour(String title, String bio, Integer maxSlots, Integer location, Integer guideman, Blob image) {
		this.title = title;
		this.bio = bio;
		this.maxSlots = maxSlots;
		this.location = location;
		this.guideman = guideman;
		this.image = image;
	}

	public Tour(Long id, String title, String bio, Integer maxSlots, Integer location, Integer guideman) {
		this.id = id;
		this.title = title;
		this.bio = bio;
		this.maxSlots = maxSlots;
		this.location = location;
		this.guideman = guideman;
	}

	public Tour(Long id, String title, Integer maxSlots, Integer location, Integer guideman) {
		this.id = id;
		this.title = title;
		this.maxSlots = maxSlots;
		this.location = location;
		this.guideman = guideman;
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

	public Integer getGuideman() {
		return guideman;
	}

	public void setGuideman(Integer guideman) {
		this.guideman = guideman;
	}

	public Integer getLocation() {
		return location;
	}

	public void setLocation(Integer location) {
		this.location = location;
	}

	@Override
	public String toString() {
		return "Tour [id=" + id + ", title=" + title + ", bio=" + bio + ", maxSlots=" + maxSlots + ", location="
				+ location + ", guideman=" + guideman + ", image=" + image + "]";
	}

	// TODO roman
}
