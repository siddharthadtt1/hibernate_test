package org.test.hibernate.dto;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
public class Vehicle {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "vehicle_name")
	private String vehicleName;

	// @ManyToOne
	// @JoinColumn(name="user_id")
	// private UserDetails userDetails;

	@ManyToMany(mappedBy = "vehicleList")
	@NotFound(action = NotFoundAction.IGNORE)
	private Collection<UserDetails> userList = new ArrayList<>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getVehicleName() {
		return vehicleName;
	}

	public void setVehicleName(String vehicleName) {
		this.vehicleName = vehicleName;
	}

	public Collection<UserDetails> getUserList() {
		return userList;
	}

	public void setUserList(Collection<UserDetails> userList) {
		this.userList = userList;
	}

	// public UserDetails getUserDetails() {
	// return userDetails;
	// }

	// public void setUserDetails(UserDetails userDetails) {
	// this.userDetails = userDetails;
	// }

}
