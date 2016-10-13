package com.tang.ContactService;

import java.util.ArrayList;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tang.contactservice.model.Contact;

import junit.framework.Assert;

/**
 * Unit test for Contact Service
 */
public class FunctionalTest {
	public static final String CONTACT_SERVICE_URL = "http://localhost:8080/contacts/";
	public static final Logger LOG = LoggerFactory.getLogger(FunctionalTest.class);

	/**
	 * 1. add a contact -> ok
	 * 2. add the same contact again -> ko (user already exists)
	 */
	@Test(threadPoolSize = 5, invocationCount = 100, timeOut = 10000)
	public void testAddContact() {
		RestTemplate restTemplate = new RestTemplate();

		Contact contact = new Contact(RandomStringUtils.randomAlphabetic(6), "" + new Random().nextInt(10),
				RandomStringUtils.randomAlphabetic(6));
		restTemplate.postForObject(CONTACT_SERVICE_URL, contact, Contact.class);

		try {
			restTemplate.postForObject(CONTACT_SERVICE_URL, contact, Contact.class);
		} catch (HttpClientErrorException e) {
			Assert.assertEquals(HttpStatus.CONFLICT, e.getStatusCode());
		}
	}

	/**
	 * if no contact -> ko (no user found)
	 * otherwise -> print all contacts
	 */
	@Test(threadPoolSize = 5, invocationCount = 100, timeOut = 10000)
	public void testGetContactsByName() {
		RestTemplate restTemplate = new RestTemplate();
		try {
			ArrayList<Contact> contactList = restTemplate
					.getForObject(CONTACT_SERVICE_URL + RandomStringUtils.randomAlphabetic(1), ArrayList.class);
			ObjectMapper om = new ObjectMapper();
			LOG.info(om.writerWithDefaultPrettyPrinter().writeValueAsString(contactList));
		} catch (HttpClientErrorException e) {
			LOG.error(e.getStatusCode().toString());
			LOG.error(e.getResponseBodyAsString());
		} catch (JsonProcessingException jpe) {
			LOG.error(jpe.getMessage());
		}
	}

	/**
	 * if no contact -> ko (no user found)
	 * otherwise -> print all contacts
	 */
	@Test
	public void testGetAllContacts() {
		RestTemplate restTemplate = new RestTemplate();
		try {
			ArrayList<Contact> contactList = restTemplate.getForObject(CONTACT_SERVICE_URL, ArrayList.class);

			ObjectMapper om = new ObjectMapper();
			LOG.info(om.writerWithDefaultPrettyPrinter().writeValueAsString(contactList));
		} catch (HttpClientErrorException e) {
			LOG.error(e.getStatusCode().toString());
			LOG.error(e.getResponseBodyAsString());
		} catch (JsonProcessingException jpe) {
			LOG.error(jpe.getMessage());
		}
	}

	/**
	 * 1. create a contact 
	 * 2. modify it -> ok 
	 * 3. modify it again with same info -> ko (user already exists)
	 * 4. modify an non exsiting contact -> ko (no user found)
	 */
	@Test
	public void testModifyContact() {
		RestTemplate restTemplate = new RestTemplate();

		Contact contact = new Contact(RandomStringUtils.randomAlphabetic(6), "" + new Random().nextInt(10),
				RandomStringUtils.randomAlphabetic(6));
		Contact result = restTemplate.postForObject(CONTACT_SERVICE_URL, contact, Contact.class);

		Contact newContact = new Contact(RandomStringUtils.randomAlphabetic(6), "" + new Random().nextInt(10),
				RandomStringUtils.randomAlphabetic(6));

		restTemplate.put(CONTACT_SERVICE_URL + result.getId(), newContact);
		try {
			restTemplate.put(CONTACT_SERVICE_URL + result.getId(), newContact);
		} catch (HttpClientErrorException e) {
			Assert.assertEquals(HttpStatus.CONFLICT, e.getStatusCode());
		}

		try {
			restTemplate.put(CONTACT_SERVICE_URL + "12345678", contact);
		} catch (HttpClientErrorException e) {
			Assert.assertEquals(HttpStatus.NOT_FOUND, e.getStatusCode());
		}
	}

	/**
	 * 1. Create a contact 
	 * 2. Delete it -> ok 
	 * 3. Delete it again -> ko (no user found)
	 */
	@Test
	public void testDeleteContact() {
		RestTemplate restTemplate = new RestTemplate();

		Contact contact = new Contact(RandomStringUtils.randomAlphabetic(6), "" + new Random().nextInt(10),
				RandomStringUtils.randomAlphabetic(6));
		Contact result = restTemplate.postForObject(CONTACT_SERVICE_URL, contact, Contact.class);

		restTemplate.delete(CONTACT_SERVICE_URL + "" + result.getId());

		try {
			restTemplate.delete(CONTACT_SERVICE_URL + "" + result.getId());
		} catch (HttpClientErrorException e) {
			Assert.assertEquals(HttpStatus.NOT_FOUND, e.getStatusCode());
		}
		
	}

}
