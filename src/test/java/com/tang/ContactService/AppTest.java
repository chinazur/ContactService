package com.tang.ContactService;

import java.util.ArrayList;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tang.contactservice.model.Contact;

/**
 * Unit test for Contact Service
 */
@Test(threadPoolSize = 3, invocationCount = 10,  timeOut = 10000)
public class AppTest {
	public static final String CONTACT_SERVICE_URL = "http://localhost:8080/contacts/";
	public static final Logger LOG = LoggerFactory.getLogger(AppTest.class);

	@Test(threadPoolSize = 5, invocationCount = 100, timeOut = 10000)
	public void testAddContact() {
		RestTemplate restTemplate = new RestTemplate();
		
		Contact contact = new Contact(RandomStringUtils.random(2, "ab"), "" + new Random().nextInt(10), RandomStringUtils.random(2, "ab"));
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

	@Test(threadPoolSize = 5, invocationCount = 100, timeOut = 10000)
	public void testGetContactsByName() {
		RestTemplate restTemplate = new RestTemplate();
		try {
			ArrayList<Contact> contactList = restTemplate.getForObject(CONTACT_SERVICE_URL + RandomStringUtils.random(2, "ab"), ArrayList.class);
			ObjectMapper om = new ObjectMapper();
			LOG.info(om.writerWithDefaultPrettyPrinter().writeValueAsString(contactList));
		} catch (HttpClientErrorException e) {
			LOG.error(e.getStatusCode().toString());
			LOG.error(e.getResponseBodyAsString());
		} catch (JsonProcessingException jpe) {
			LOG.error(jpe.getMessage());
		}
	}

	@Test(threadPoolSize = 5, invocationCount = 100, timeOut = 10000)
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

	@Test(threadPoolSize = 5, invocationCount = 100, timeOut = 10000)
	public void testModifyContact() {
		RestTemplate restTemplate = new RestTemplate();

		Contact contact = new Contact(RandomStringUtils.random(2, "ab"), "" + new Random().nextInt(10), RandomStringUtils.random(2, "ab"));
		
		try {
			restTemplate.put(CONTACT_SERVICE_URL + new Random().nextInt(10), contact);
		} catch (HttpClientErrorException e) {
			LOG.error(e.getStatusCode().toString());
			LOG.error(e.getResponseBodyAsString());
		}
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
