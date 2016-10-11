package com.tang.contactservice.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tang.contactservice.model.Contact;
import com.tang.contactservice.service.ContactService;


/**
 * Controller to expose methods as web service
 */
@RestController
@RequestMapping(value="/contacts/")
public class ContactServiceController {
	
    public static final Logger LOG = LoggerFactory.getLogger(ContactServiceController.class);

	//used to call CRUD operations
	@Autowired
	private ContactService contactService;
	
	// Get all Contacts
	@RequestMapping(method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public List<Contact> getAllContacts(){
		return contactService.getAllContacts();
	}
	
	// Get contact(s) by name
	@RequestMapping(value="{name}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public List<Contact> searchContactByName(@PathVariable("name") String name){
		return contactService.searchContactByName(name);
	}
	
	// Add a new contact
	@RequestMapping(method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	public Contact addContact(@RequestBody Contact contact) throws Exception{
		LOG.info("adding contact...");
		return contactService.addContact(contact);
	}
	
	// Delete a contact by ID
	@RequestMapping(value="{id}", method=RequestMethod.DELETE)
	public void removeContactById(@PathVariable("id") long id) throws Exception{
		contactService.removeContactById(id);
	}
	
	// Modify a contact by ID
	@RequestMapping(value="{id}", method=RequestMethod.PUT, consumes=MediaType.APPLICATION_JSON_VALUE)
	public Contact modifyContactById(@PathVariable("id") long id, @RequestBody Contact contact) throws Exception{
		return contactService.modifyContactById(id, contact);
	}
}
