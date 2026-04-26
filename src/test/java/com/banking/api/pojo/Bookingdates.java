package com.banking.api.pojo;

public class Bookingdates {
	private String checkin;
	private String checkout;
	
	 // CONSTRUCTOR — used to create the object with values
    // new Bookingdates("2026-05-01", "2026-05-10")
	public Bookingdates(String checkin,String checkout) {
		this.checkin=checkin;
		this.checkout=checkout;
	}
		
		// GETTERS — Jackson needs these to read the values
	    // when converting Java object to JSON
	    // without getters Jackson cannot see the field values
		
		 public String getCheckin() { return checkin; }
		public String getCheckout() {
			return checkout;
		}
		
		// SETTERS — used to update values after object creation
		public void setCheckin(String checkin) {
			this.checkin=checkin;
		}
		public void setCheckout(String checkout) {
			this.checkout=checkout;
		}
		
	}


