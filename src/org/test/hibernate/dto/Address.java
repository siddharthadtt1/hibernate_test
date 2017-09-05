package org.test.hibernate.dto;

import javax.persistence.Embeddable;

@Embeddable
public class Address {
	
	private String street_name;
	
	private String city_name;

	public String getStreet_name() {
		return street_name;
	}

	public void setStreet_name(String street_name) {
		this.street_name = street_name;
	}

	public String getCity_name() {
		return city_name;
	}

	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}

	@Override
	public String toString() {
		return "Address [street_name=" + street_name + ", city_name=" + city_name + "]";
	}
	
}
