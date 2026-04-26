package com.banking.api.pojo;

public class Booking {
	private String firstname;
	private String lastname;
	private int totalprice;
	private boolean depositpaid;
	private Bookingdates bookingdates; // nested object — uses Bookingdates class
	private String additionalneeds;
	
	 // CONSTRUCTOR — all fields in one go
	public Booking(String firstname, String lastname, int totalprice, boolean depositpaid, Bookingdates bookingdates, String additionalneeds) {
		this.firstname=firstname;
		this.lastname=lastname;
		this.totalprice=totalprice;
		this.depositpaid=depositpaid;
		this.bookingdates=bookingdates;
		this.additionalneeds=additionalneeds;
	}
	//getters — needed for Jackson to convert Java object to JSON
	public String getFirstname() {return firstname;}
	public String getLastname() {return lastname;}
	public int getTotalprice() {return totalprice;}
	// NOTE: boolean getter uses "is" not "get" — isDepositpaid() not getDepositpaid()
    // Jackson knows this convention automatically
	public boolean isDepositpaid() {return depositpaid;}
	public Bookingdates getBookingdates() {return bookingdates;}
	public String getAdditionalneeds() {return additionalneeds;}
	
	// Setters — used to update values after object creation
	public void setFirstname(String firsstname) {this.firstname=firstname;}
	public void setLastname(String lastname) {this.lastname=lastname;}
	public void setTotalprice(int totalprice) {this.totalprice=totalprice;}
	public void setDepositpaid(boolean depositpaid) {this.depositpaid=depositpaid;}
	public void setBookingdates(Bookingdates bookingdates) {this.bookingdates=bookingdates;}
	public void setAdditionalneeds(String additionalneeds) {this.additionalneeds=additionalneeds;}
	
	
	

}
