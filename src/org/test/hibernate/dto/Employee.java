package org.test.hibernate.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.CollectionId;
import org.hibernate.annotations.GeneratorType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "employee_details")
@NamedQueries(value = {
		@NamedQuery(name = "Employee.byId", query = "from Employee"),
		@NamedQuery(name = "Employee.count", query = " select count(*) from Employee")})
@Cacheable
@Cache(usage=CacheConcurrencyStrategy.READ_ONLY)
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "emp_id")
	private int id;

	// @Basic(fetch = FetchType.LAZY)
	@Column(name = "emp_name")
	private String name;

	@Column(name = "emp_age")
	private int age;

	@Column(name = "emp_joining_date")
	// @Temporal(TemporalType.DATE)
	private LocalDate joiningDate;

	// @Transient
	@Column(name = "emp_joining_location")
	private String joiningLocation;

	// @Embedded
	// private Address address;

	// @Embedded
	// @AttributeOverrides({ @AttributeOverride(name = "street_name", column =
	// @Column(name = "home_street_name")),
	// @AttributeOverride(name = "city_name", column = @Column(name =
	// "home_city_name")) })
	// private Address homeAddress;

	@ElementCollection /* (fetch=FetchType.EAGER) */
	@JoinTable(name = "emp_addr", joinColumns = @JoinColumn(name = "emp_id_fk"))
	@GenericGenerator(name = "sequence-gen", strategy = "sequence")
	@CollectionId(columns = {
			@Column(name = "addr_id")}, generator = "sequence-gen", type = @Type(type = "long"))
	private Collection<Address> addrList = new ArrayList<>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public LocalDate getJoiningDate() {
		return joiningDate;
	}

	public void setJoiningDate(LocalDate joiningDate) {
		this.joiningDate = joiningDate;
	}

	public String getJoiningLocation() {
		return joiningLocation;
	}

	public void setJoiningLocation(String joiningLocation) {
		this.joiningLocation = joiningLocation;
	}

	public Collection<Address> getEmpList() {
		return addrList;
	}

	public void setEmpList(List<Address> addrList) {
		this.addrList = addrList;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", age=" + age
				+ ", joiningDate=" + joiningDate + ", joiningLocation="
				+ joiningLocation + ", empList=" + addrList + "]";
	}

	// public Address getAddress() {
	// return address;
	// }

	// public void setAddress(Address address) {
	// this.address = address;
	// }

	// public Address getHomeAddress() {
	// return homeAddress;
	// }

	// public void setHomeAddress(Address homeAddress) {
	// this.homeAddress = homeAddress;
	// }

	// @Override
	// public String toString() {
	// return "Employee [id=" + id + ", name=" + name + ", age=" + age + ",
	// joiningDate=" + joiningDate
	// + ", joiningLocation=" + joiningLocation + ", address=" + address + ",
	// homeAddress=" + homeAddress
	// + "]";
	// }

}
