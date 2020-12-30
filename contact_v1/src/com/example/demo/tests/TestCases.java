package com.example.demo.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.AfterClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.demo.daos.ContactDaoImpl;
import com.example.demo.models.Contact;


class TestCases {
	
	ContactDaoImpl dao;
	
	
	@BeforeEach
	void establishConnection() throws Exception {
		dao = new ContactDaoImpl();
		
	}
	
	@DisplayName("Testing for connection to database is null or not")
	@Test 
	void checkConnection() {
		assertNotNull(dao.getConnection());
	}

	
	@DisplayName("checking for contact added DAO service")
	@Test
	void checkForContactAdded() {
		Contact contact = new Contact("mahesh", "mcdq", 
								new String[] {"5764,2345"}, 
								"./cd", LocalDate.of(1998, 04, 23), 
								new String[] {"s@s.com"}, 2);
		
		assertEquals(1, dao.add(contact));
	}
	
	
	@DisplayName("Testing for contact updating DAO service")
	@Test
	void checkForContactUpdate() {
		Contact existing = new Contact("mahesh", "mcdq", 
								new String[] {"5764,2345"}, 
								"./cd", LocalDate.of(1998, 04, 23), 
								new String[] {"s@s.com"}, 2);
		
		Contact updated = new Contact("mahesh", "mcdq", 
				new String[] {"2345"}, 
				"./ca", LocalDate.of(1998, 04, 23), 
				new String[] {"s@s.com"}, 2);
		
		assertEquals(1, dao.update(existing, updated));
	}
	
	
	
	
	@DisplayName("Testing for contact remove DAO service")
	@Disabled
	@Test
	void checkForContactRemoved() {
		assertEquals(1, dao.remove("mahesh"));
	}
	
	
	@AfterClass
	void closeConnection() {
		dao=null;
	}
}
