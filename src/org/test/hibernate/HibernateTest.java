package org.test.hibernate;

import java.time.LocalDate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.test.hibernate.dto.Address;
import org.test.hibernate.dto.Employee;

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
			 
			//employee.setAddress(address);
			//employee.setHomeAddress(homeAddress);
			
			employee.getEmpList().add(address);
			employee.getEmpList().add(homeAddress);
			
			session.save(employee);

			session.getTransaction().commit();
			
			getEmployee(sessionFactory);
			
			
		} catch (Exception e) {
			 e.printStackTrace();
		} finally {
			session.close();
			sessionFactory.close();
		}

	}
	
	private static void getEmployee(SessionFactory sessionFactory){
		Session session = sessionFactory.openSession();
		Employee employee = session.get(Employee.class, 1);
		System.out.println("Employee get() result : ");
		System.out.println(employee);
		
	}

}
