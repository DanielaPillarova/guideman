package sk.upjs.paz1c.guideman.storage;

import org.w3c.dom.Text;

import com.mysql.cj.jdbc.Blob;

public class Tour {

	private Long id;
	private String title;
	private Text bio; // idk, mozno String? text je interface a take nechceme
	private int maxSlots;
	private Blob image;
	private User guideman;
	// private Location location;
	
	
	// Location
	public Tour(User guideman) {
		this.guideman = guideman;
		//this.location = location;
	}
	
	
	
	
	
	
}
