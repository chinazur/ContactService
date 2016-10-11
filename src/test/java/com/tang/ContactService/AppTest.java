package com.tang.ContactService;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tang.contactservice.model.Contact;

import junit.framework.TestCase;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase{
    public static final String CONTACT_SERVICE_URL = "http://localhost:8080/contact/";
    public static final Logger LOG = LoggerFactory.getLogger(AppTest.class);
    
    public void testAddContact(){
    	RestTemplate restTemplate = new RestTemplate();
    	
    	Contact contact = new Contact("tang1", "0336", "antibes");
    	if(restTemplate.postForObject(CONTACT_SERVICE_URL, contact, Boolean.class)){
    		LOG.info("contact added");
    	}else{
    		LOG.error("contact not added");
    	}
    }
    
    public void testGetContactsByName(){
    	RestTemplate restTemplate = new RestTemplate();
    	String name = "tan";
    	HashMap<Long, Contact> contactList = restTemplate.getForObject(CONTACT_SERVICE_URL + name, HashMap.class);
        
        if(contactList.isEmpty()){
        	LOG.info("no contact found");
        }else{
        	ObjectMapper om = new ObjectMapper();
        	try{
        		LOG.info(om.writerWithDefaultPrettyPrinter().writeValueAsString(contactList));
        	}catch(JsonProcessingException e){
        		LOG.error(e.getMessage());
        	}
        
        	
        }
    }
    
    public void testGetAllContacts(){
    	RestTemplate restTemplate = new RestTemplate();
    	
    	HashMap<Long, Contact> contactList = restTemplate.getForObject(CONTACT_SERVICE_URL, HashMap.class);
        
        if(contactList.isEmpty()){
        	LOG.info("no contact found");
        }else{
        	ObjectMapper om = new ObjectMapper();
        	try{
        		LOG.info(om.writerWithDefaultPrettyPrinter().writeValueAsString(contactList));
        	}catch(JsonProcessingException e){
        		LOG.error(e.getMessage());
        	}
        
        	
        }
    }
    
    public void testModifyContact(){
    	RestTemplate restTemplate = new RestTemplate();
    	
    	Contact contact = new Contact("lina", "2161", "sophia");
    	String id = "1";
    	
    	restTemplate.put(CONTACT_SERVICE_URL + id, contact);
    }
    
    public void testDeleteContact(){
    	RestTemplate restTemplate = new RestTemplate();
    	
    	String id = "2";
    	
    	restTemplate.delete(CONTACT_SERVICE_URL + id);
    }

}
