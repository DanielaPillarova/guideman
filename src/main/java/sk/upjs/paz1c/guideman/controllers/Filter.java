package sk.upjs.paz1c.guideman.controllers;

import java.util.List;

public enum Filter {
	
	INSTANCE;
	
	private String country;
	private String month;
	private String guideman;
	private String price;
	
	public String getCountry() {
		return country;
	}
	
	public String getMonth() {
		return month;
	}
	
	public String getGuideman() {
		return guideman;
	}
	
	public String getPrice() {
		return price;
	}

	public void setCountry(String country) {
		System.out.println("setting country : " + country);
		this.country = country;
	}
	
	public void setMonth(String month) {
		System.out.println("setting month : " + month);
		this.month = month;
	}
	
	public void setGuideman(String guideman) {
		System.out.println("setting guideman : " + guideman);
		this.guideman = guideman;
	}
	
	public void setPrice(String price) {
		System.out.println("setting price : " + price);
		this.price = price;
	}

}
