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
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.delete(CONTACT_SERVICE_URL);

		for (int i = 0; i < 4; i++) {
			Contact contact = new Contact("Contact" + i, "06", "antibes");
			restTemplate.postForObject(CONTACT_SERVICE_URL, contact, Contact.class);
		}
		;
		Assert.assertEquals(restTemplate.getForObject(CONTACT_SERVICE_URL, List.class).size(), 4);
	}

	/**
	 * delete all contacts after each test
	 */
	@AfterTest
	public void tearDown() {
		RestTemplate restTemplate = new RestTemplate();
		Assert.assertEquals(restTemplate.getForObject(CONTACT_SERVICE_URL, List.class).size(), 4);

		restTemplate.delete(CONTACT_SERVICE_URL);

		try {
			restTemplate.getForObject(CONTACT_SERVICE_URL, List.class);
		} catch (HttpClientErrorException e) {
			Assert.assertEquals(HttpStatus.NOT_FOUND, e.getStatusCode());
		}
	}

	/**
	 * each test picks one of the four testing contacts and creates it after the
	 * test, we should get only 4 contacts in total
	 */
	@Test(threadPoolSize = 5, invocationCount = 100, timeOut = 10000)
	public void testAddContact() {
		RestTemplate restTemplate = new RestTemplate();

		Contact contact = new Contact("Contact" + new Random().nextInt(4), "06", "antibes");
		try {
			restTemplate.postForObject(CONTACT_SERVICE_URL, contact, Contact.class);
		} catch (HttpClientErrorException e) {
			LOG.error(e.getStatusCode().toString());
			LOG.error(e.getResponseBodyAsString());
		}
	}

	/**
	 * according to the setup we have Contact0 to Contact3, 4 contacts in total
	 * if we search by a sub string, we should get all contacts back if we
	 * search by a complete name, we should get only 1 contact back if we search
	 * by a random string, we should get NOT_FOUND result
	 */
	@Test
	public void testGetContactsByName() {
		RestTemplate restTemplate = new RestTemplate();

		Assert.assertEquals(restTemplate.getForObject(CONTACT_SERVICE_URL + "Con", List.class).size(), 4);

		Assert.assertEquals(restTemplate.getForObject(CONTACT_SERVICE_URL + "Contact1", ArrayList.class).size(), 1);

		try {
			restTemplate.getForObject(CONTACT_SERVICE_URL + RandomStringUtils.randomAlphabetic(2), ArrayList.class);
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
		Assert.assertEquals(restTemplate.getForObject(CONTACT_SERVICE_URL, List.class).size(), 4);
	}

	/**
	 * Randomly pick one of the 4 existing contacts and try to modify it with
	 * the same information -> ko Continue to modify it with different address
	 * information -> ok
	 */
	@Test(threadPoolSize = 5, invocationCount = 100, timeOut = 10000)
	public void testModifyContact() {
		RestTemplate restTemplate = new RestTemplate();

		@SuppressWarnings("unchecked")
		List<LinkedHashMap<String, Object>> contactList = restTemplate.getForObject(CONTACT_SERVICE_URL, List.class);
		HashMap<String, Object> map = contactList.get(new Random().nextInt(4));

		try {
			restTemplate.put(CONTACT_SERVICE_URL + map.get("id"), new Contact(map.get("name").toString(),
					map.get("phone").toString(), map.get("address").toString()));
		} catch (HttpClientErrorException e) {
			Assert.assertEquals(HttpStatus.CONFLICT, e.getStatusCode());
		}

		Contact contact = new Contact("Contact" + new Random().nextInt(4), "06", RandomStringUtils.randomAlphabetic(6));
		restTemplate.put(CONTACT_SERVICE_URL + map.get("id"), contact);

	}

	/**
	 * Randomly pick one of the 4 contacts and delete it then re-create it there
	 * should always be 4 contacts in total (in tearDown method)
	 */
	@Test(threadPoolSize = 5, invocationCount = 100, timeOut = 10000)
	public void testDeleteContact() {
		RestTemplate restTemplate = new RestTemplate();

		try {
			List<LinkedHashMap<String, Object>> contactList = restTemplate.getForObject(CONTACT_SERVICE_URL, List.class);
			HashMap<String, Object> map = contactList.get(new Random().nextInt(contactList.size()));
			String id = map.get("id").toString();
			try {
				restTemplate.delete(CONTACT_SERVICE_URL + "" + id);
			} catch (HttpClientErrorException e) {
				LOG.error(e.getStatusCode().toString());
				LOG.error(e.getResponseBodyAsString());
			}
	
			Contact contact = new Contact(map.get("name").toString(), map.get("phone").toString(),
					map.get("address").toString());
			try {
				restTemplate.postForObject(CONTACT_SERVICE_URL, contact, Contact.class);
			} catch (HttpClientErrorException e) {
				LOG.error(e.getStatusCode().toString());
				LOG.error(e.getResponseBodyAsString());
			}
		}catch (HttpClientErrorException e) {
			LOG.error(e.getStatusCode().toString());
			LOG.error(e.getResponseBodyAsString());
		}
	}

}
