package org.test.hibernate;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.test.hibernate.dto.Address;
import org.test.hibernate.dto.Employee;
import org.test.hibernate.dto.UserDetails;
import org.test.hibernate.dto.Vehicle;

public class HibernateTest {

	public static void main(String[] args) {

		Configuration configuration = null;
		SessionFactory sessionFactory = null;

		try {
			configuration = new Configuration();
			sessionFactory = configuration.configure().buildSessionFactory();

			// getEmployee(sessionFactory);

			// different types of mapping
			// mappingType(sessionFactory);

			// populate employee table
			// populateEmployee(sessionFactory);

			// use hql query
			// getByHqlQuery(sessionFactory);

			// get by pagination
			// getByPagination(sessionFactory);

			// get by using Criteria & Restrictions
			// getByCriteria(sessionFactory);

			// use first_level_cache
			// performFirstLevelCache(sessionFactory);

			// use second level cache
			// performSecondLevelCache(sessionFactory);
			
			// use query level cache
			performQueryLevelCache(sessionFactory);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sessionFactory.close();
		}

	}

	private static void anotationBased(SessionFactory sessionFactory) {
		Session session = sessionFactory.openSession();

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

		// session.save(userDetails);
		session.persist(userDetails);
		// session.save(vehicle);
		// session.save(vehicle2);

		session.getTransaction().commit();
	}

	private static void populateEmployee(SessionFactory sessionFactory) {
		Session session = sessionFactory.openSession();

		session.beginTransaction();
		for (int i = 1; i <= 10; i++) {
			Employee employee = new Employee();

			employee.setName("User " + i);
			employee.setAge(22 + i);
			employee.setJoiningDate(LocalDate.now().plusDays(i));

			session.save(employee);
		}

		session.getTransaction().commit();
		session.close();
	}

	private static void getByHqlQuery(SessionFactory sessionFactory) {

		System.out.println("Fetch elements by using HQL query ");

		Session session = sessionFactory.openSession();

		Query query = session.createQuery("from Employee where id > :id");
		query.setParameter("id", 5);

		List<Employee> empList = query.getResultList();

		for (Employee e : empList)
			System.out.println(e);

		session.close();
	}

	private static void getByPagination(SessionFactory sessionFactory) {
		Session session = sessionFactory.openSession();

		Query countQuery = session.createNamedQuery("Employee.count",
				Long.class);
		Long tableSize = (Long) countQuery.uniqueResult();

		System.out.println("Table size : " + tableSize);

		Query query = session.createNamedQuery("Employee.byId", Employee.class);

		int page = 1;
		int offset = 0;
		int maxResult = 3;

		while (offset <= tableSize) {
			System.out.println(" Page " + page++);

			query.setFirstResult(offset);
			query.setMaxResults(maxResult);

			List<Employee> empList = query.getResultList();

			for (Employee emp : empList)
				System.out.println(emp);

			offset += maxResult;
		}

		session.close();
	}

	private static void getByCriteria(SessionFactory sessionFactory) {
		System.out.println("Using Criteria example : ");

		Session session = sessionFactory.openSession();

		Criteria criteria = session.createCriteria(Employee.class);

		// criteria.add(Restrictions.or(Restrictions.ge("id", 6),
		// Restrictions.like("name", "User 1%")));

		// criteria.setProjection(Projections.property("joiningDate")).addOrder(Order.desc("id"));

		// List<String> empIdList = criteria.list();
		// System.out.println(empIdList);

		List<Employee> empList = criteria.list();

		for (Employee emp : empList)
			System.out.println(emp);

		session.close();
	}

	private static void performFirstLevelCache(SessionFactory sessionFactory) {
		System.out.println("Using first level of cache - session");

		Session session = sessionFactory.openSession();

		session.beginTransaction();
		Employee employee = session.get(Employee.class, 1);
		System.out.println(employee);
		employee.setAge(24);

		Employee employee2 = session.get(Employee.class, 1);
		System.out.println(employee2);

		session.getTransaction().commit();
		session.close();
	}

	private static void performSecondLevelCache(SessionFactory sessionFactory) {
		System.out.println(
				"Using second level of cache - across multiple session");

		Session session = sessionFactory.openSession();

		Employee employee = session.get(Employee.class, 1);
		System.out.println(employee);
		session.close();

		Session session2 = sessionFactory.openSession();

		Employee employee2 = session2.get(Employee.class, 1);
		System.out.println(employee2);

		session2.close();
	}
	
	private static void performQueryLevelCache(SessionFactory sessionFactory) {
		System.out.println(
				"Using second level of cache - across multiple session");

		Session session = sessionFactory.openSession();

		Query query = session.createQuery("from Employee where id > :id");
		query.setCacheable(true);
		query.setParameter("id", 5);
		
		List<Employee> empList1 = query.getResultList();
		
		session.close();

		Session session2 = sessionFactory.openSession();
		
		Query query2 = session2.createQuery("from Employee where id > :id");
		query2.setCacheable(true);
		query2.setParameter("id", 5);
		
		List<Employee> empList2 = query2.getResultList();

		session2.close();
	}

	
}
