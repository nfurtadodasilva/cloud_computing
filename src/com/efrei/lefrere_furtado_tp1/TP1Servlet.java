package com.efrei.lefrere_furtado_tp1;

import java.io.IOException;
import java.util.Date;
import java.util.Formatter;
import java.util.Locale;

import javax.servlet.http.*;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

@SuppressWarnings("serial")
public class TP1Servlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
		
		
		// Database
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		// Creation et Properties
		Entity employee = new Entity("Employee", "Emp1");
		employee.setProperty("firstName", "Antonio");
		employee.setProperty("Salary", 0);
		Date hireDate = new Date();
		employee.setProperty("hireDate", hireDate);
		datastore.put(employee);

		Entity employee2 = new Entity("Employee", "Emp2");
		employee2.setProperty("firstName", "Nicolas");
		employee2.setUnindexedProperty("Salary", 45553000);
		hireDate = new Date("11/01/2015");
		employee2.setProperty("hireDate", hireDate);
		datastore.put(employee2);

		Entity employee3 = new Entity("Employee", "Emp3");
		employee3.setProperty("firstName", "Cocoya");
		employee3.setProperty("Salary", 1000);
		hireDate = new Date("9/5/2015");
		employee3.setProperty("hireDate", hireDate);
		datastore.put(employee3);

		Entity employee4 = new Entity("Employee", "Emp4");
		employee4.setProperty("firstName", "Kirikou");
		employee4.setProperty("Salary", 50);
		hireDate = new Date("9/11/2015");
		employee4.setProperty("hireDate", hireDate);
		datastore.put(employee4);

		try {
			Key employeeKey = KeyFactory.createKey("Employee", "Emp3");
			Entity emp = datastore.get(employeeKey);
			emp.setProperty("Salary", 123000);
			datastore.put(emp);
		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Requete
		Query q = new Query("Employee");

		q.addFilter("Salary", Query.FilterOperator.GREATER_THAN, 100000);
		PreparedQuery pq = datastore.prepare(q);

		for (Entity result : pq.asIterable()) {
			resp.getWriter().println("Name: " + result.getProperty("firstName") + " / Salary : " + result.getProperty("Salary"));
		}		
		
	}
}
