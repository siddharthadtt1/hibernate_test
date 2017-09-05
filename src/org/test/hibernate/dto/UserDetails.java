package org.test.hibernate.dto;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Cascade;

@Entity(name = "user_details")
public class UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "user_name")
	private String userName;

	// @OneToOne
	// @JoinColumn(name="id_vehicle")
	// private Vehicle vehicle;

	@ManyToMany(cascade=CascadeType.ALL)
	//@OneToMany(mappedBy="userDetails")
	// @JoinTable(name="user_vehicle_details",
	// joinColumns=@JoinColumn(name="user_id"),
	// inverseJoinColumns=@JoinColumn(name="vehicle_id"))
	private Collection<Vehicle> vehicleList = new ArrayList<>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Collection<Vehicle> getVehicleList() {
		return vehicleList;
	}

	public void setVehicleList(Collection<Vehicle> vehicleList) {
		this.vehicleList = vehicleList;
	}

	// public Vehicle getVehicle() {
	// return vehicle;
	// }

	// public void setVehicle(Vehicle vehicle) {
	// this.vehicle = vehicle;
	// }

}
