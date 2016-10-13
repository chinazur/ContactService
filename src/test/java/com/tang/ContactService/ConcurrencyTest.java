package com.tang.ContactService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tang.contactservice.model.Contact;

/**
 * Unit test for Contact Service
 */
public class ConcurrencyTest {
	public static final String CONTACT_SERVICE_URL = "http://localhost:8080/contacts/";
	public static final Logger LOG = LoggerFactory.getLogger(ConcurrencyTest.class);

	/**
	 * create 4 contacts before each test
	 */
	@BeforeTest
	public void setup() {
		LOG.info("RUNNING");
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.delete(CONTACT_SERVICE_URL);

		for (int i = 0; i < 4; i++) {
			Contact contact = new Contact("Contact" + i, "06", "antibes");
			restTemplate.postForObject(CONTACT_SERVICE_URL, contact, Contact.class);
		}
		ArrayList<Contact> contactList = restTemplate.getForObject(CONTACT_SERVICE_URL, ArrayList.class);
		Assert.assertEquals(contactList.size(), 4);
	}


	/**
	 * delete all contacts after each test
	 */
	@AfterTest
	public void tearDown() {
		RestTemplate restTemplate = new RestTemplate();
		ArrayList<Contact> contactList = restTemplate.getForObject(CONTACT_SERVICE_URL, ArrayList.class);
		Assert.assertEquals(contactList.size(), 4);

		restTemplate.delete(CONTACT_SERVICE_URL);

		try {
			contactList = restTemplate.getForObject(CONTACT_SERVICE_URL, ArrayList.class);
		} catch (HttpClientErrorException e) {
			Assert.assertEquals(HttpStatus.NOT_FOUND, e.getStatusCode());
		}
	}

	/**
	 * each test picks one of the four testing contacts
	 * and creates it
	 * after the test, we should get only 4 contacts in total
	 */
	@Test(threadPoolSize = 5, invocationCount = 100, timeOut = 10000)
	public void testAddContact() {
		RestTemplate restTemplate = new RestTemplate();

		Contact contact = new Contact("Contact" + new Random().nextInt(4), "06", "antibes");
		try {
			Contact result = restTemplate.postForObject(CONTACT_SERVICE_URL, contact, Contact.class);
			ObjectMapper om = new ObjectMapper();
			LOG.info(om.writerWithDefaultPrettyPrinter().writeValueAsString(result));
		} catch (HttpClientErrorException e) {
			LOG.error(e.getStatusCode().toString());
			LOG.error(e.getResponseBodyAsString());
		} catch (JsonProcessingException jpe) {
			LOG.error(jpe.getMessage());
		}
	}
	
	/**
	 * according to the setup we have Contact0 to Contact3, 4 contacts in total
	 * if we search by a sub string, we should get all contacts back
	 * if we search by a complete name, we should get only 1 contact back
	 * if we search by a random string, we should get NOT_FOUND result
	 */
	@Test
	public void testGetContactsByName() {
		RestTemplate restTemplate = new RestTemplate();

		ArrayList<Contact> contactList = restTemplate.getForObject(CONTACT_SERVICE_URL + "Con", ArrayList.class);
		Assert.assertEquals(contactList.size(), 4);

		contactList = restTemplate.getForObject(CONTACT_SERVICE_URL + "Contact1", ArrayList.class);
		Assert.assertEquals(contactList.size(), 1);

		try {
			contactList = restTemplate.getForObject(CONTACT_SERVICE_URL + RandomStringUtils.randomAlphabetic(2), ArrayList.class);
		} catch (HttpClientErrorException e) {
			Assert.assertEquals(HttpStatus.NOT_FOUND, e.getStatusCode());
		}
	}

	/**
	 * according to the setup, we should get 4 contacts in total
	 */
	@Test
	public void testGetAllContacts() {
		RestTemplate restTemplate = new RestTemplate();
		@SuppressWarnings("unchecked")
		ArrayList<Contact> contactList = restTemplate.getForObject(CONTACT_SERVICE_URL, ArrayList.class);
		Assert.assertEquals(contactList.size(), 4);

	}

	@Test//(threadPoolSize = 5, invocationCount = 100, timeOut = 10000)
	public void testModifyContact() {
		RestTemplate restTemplate = new RestTemplate();

		Contact contact = new Contact("Contact" + new Random().nextInt(4), "06", RandomStringUtils.randomAlphabetic(6));
		
		@SuppressWarnings("unchecked")
		List<LinkedHashMap<String, Object>> entity = restTemplate.getForObject(CONTACT_SERVICE_URL, List.class);
		HashMap<String, Object> map = entity.get(0);
		
		try {
			restTemplate.put(CONTACT_SERVICE_URL + map.get("id"), new Contact(map.get("name").toString(), 
					map.get("phone").toString(), map.get("address").toString()));
		} catch (HttpClientErrorException e) {
			Assert.assertEquals(HttpStatus.CONFLICT, e.getStatusCode());

		}

		try {
			restTemplate.put(CONTACT_SERVICE_URL + map.get("id"), contact);
		} catch (HttpClientErrorException e) {
			Assert.assertEquals(HttpStatus.CONFLICT, e.getStatusCode());

		}

//		try {
//			restTemplate.put(CONTACT_SERVICE_URL +  12345678, contact);
//		} catch (HttpClientErrorException e) {
//			Assert.assertEquals(HttpStatus.NOT_FOUND, e.getStatusCode());
//
//		}
	}

	@Test(threadPoolSize = 5, invocationCount = 100, timeOut = 10000)
	public void testDeleteContact() {
		RestTemplate restTemplate = new RestTemplate();

		try {
			restTemplate.delete(CONTACT_SERVICE_URL + "" + new Random().nextInt(100));
		} catch (HttpClientErrorException e) {
			LOG.error(e.getStatusCode().toString());
			LOG.error(e.getResponseBodyAsString());
		}
	}

}
