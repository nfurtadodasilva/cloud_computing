package com.efrei.lefrere_furtado_tp1;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.cache.Cache;
import javax.cache.CacheException;
import javax.cache.CacheFactory;
import javax.cache.CacheManager;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@SuppressWarnings("serial")
public class ReceiverServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
		
		String key = req.getParameter("emp_key");
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		try {
			Key employeeKey = KeyFactory.createKey("Employee", key);
			
			//Cache 
			Map props = new HashMap();
			props.put(com.google.appengine.api.memcache.stdimpl.GCacheFactory.EXPIRATION_DELTA,3600);
			CacheFactory cacheFactory = CacheManager.getInstance().getCacheFactory();
			Cache cache = cacheFactory.createCache(props);
			
			
			if(cache.containsKey(employeeKey)) {
				System.out.println("Key " + key + " in cache");
				Entity emp = (Entity) cache.get(employeeKey);
				System.out.println("Name: " + emp.getProperty("firstName") + " / Salary: " + emp.getProperty("Salary"));
			} else {
				//Requete if not in cache 
				System.out.println("Key " +key + " not in cache");
				Entity emp = datastore.get(employeeKey);
				System.out.println("Name: " + emp.getProperty("firstName") + " / Salary: " + emp.getProperty("Salary"));
				Map<Key,Entity> map = new HashMap();
				map.put(employeeKey, emp);
				cache.putAll(map);
			}
			
			
		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CacheException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");

		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		String name = req.getParameter("firstName");
		String salary = req.getParameter("Salary");
		// Creation et Properties
		Entity employee = new Entity("Employee", "EMP_" + name);
		employee.setProperty("firstName", name);
		employee.setProperty("Salary", salary);
		Date hireDate = new Date();
		employee.setProperty("hireDate", hireDate);
		datastore.put(employee);
	}
}
