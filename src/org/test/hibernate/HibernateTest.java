package org.test.hibernate;

import java.time.LocalDate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.test.hibernate.dto.Address;
import org.test.hibernate.dto.Employee;
import org.test.hibernate.dto.UserDetails;
import org.test.hibernate.dto.Vehicle;

public class HibernateTest {

	public static void main(String[] args) {

		Configuration configuration = null;
		SessionFactory sessionFactory = null;
		Session session = null;

		try {
			configuration = new Configuration();
			sessionFactory = configuration.configure().buildSessionFactory();
			session = sessionFactory.openSession();

			session.beginTransaction();
			Employee employee = new Employee();
			employee.setName("Emp 1");
			employee.setAge(23);

			// DateTimeFormatter dateTimeFormatter =
			// DateTimeFormatter.ofPattern("dd/MM/yyyy");

			// employee.setJoiningDate(LocalDate.now().format(dateTimeFormatter));

			employee.setJoiningDate(LocalDate.now());
			employee.setJoiningLocation("Mumbai");

			Address address = new Address();
			address.setCity_name("city_name");
			address.setStreet_name("street_name");

			Address homeAddress = new Address();
			homeAddress.setCity_name("home_city_name");
			homeAddress.setStreet_name("home_street_name");

			// employee.setAddress(address);
			// employee.setHomeAddress(homeAddress);

			employee.getEmpList().add(address);
			employee.getEmpList().add(homeAddress);

			session.save(employee);

			session.getTransaction().commit();

			// getEmployee(sessionFactory);

			// different types of mapping
			mappingType(sessionFactory);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
			sessionFactory.close();
		}

	}

	private static void getEmployee(SessionFactory sessionFactory) {
		Session session = sessionFactory.openSession();
		// Employee employee = session.load(Employee.class, 1);
		Employee employee = session.get(Employee.class, 1);
		// session.close();
		System.out.println("Employee get() result : ");
		// System.out.println(employee);
		System.out.println(
				"Employee addr size : " + employee.getEmpList().size());
	}

	private static void mappingType(SessionFactory sessionFactory) {
		Session session = sessionFactory.openSession();

		UserDetails userDetails = new UserDetails();
		userDetails.setUserName("user_name");

		Vehicle vehicle = new Vehicle();
		vehicle.setVehicleName("Car");
		// vehicle.setUserDetails(userDetails);
		vehicle.getUserList().add(userDetails);

		Vehicle vehicle2 = new Vehicle();
		vehicle2.setVehicleName("Jeep");
		// vehicle2.setUserDetails(userDetails);
		vehicle2.getUserList().add(userDetails);

		userDetails.getVehicleList().add(vehicle);
		userDetails.getVehicleList().add(vehicle2);

		session.beginTransaction();

		//session.save(userDetails);
		session.persist(userDetails);
		// session.save(vehicle);
		// session.save(vehicle2);

		session.getTransaction().commit();

	}
}
