package com.tang.contactservice.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tang.contactservice.ContactService;
import com.tang.contactservice.model.Contact;

@ComponentScan
@RestController
/**
 * Controller to expose methods as web service
 */
public class ContactServiceController {
	
	//used to call CRUD operations
	@Autowired
	ContactService contactService;
	
	// Get all Contacts
	@RequestMapping(value="/contact/", method=RequestMethod.GET)
	public Map<Long, Contact> getAllContacts(){
		return contactService.getAllContacts();
	}
	
	// Get contact(s) by name
	@RequestMapping(value="/contact/{name}", method=RequestMethod.GET)
	public Map<Long, Contact> searchContactByName(@PathVariable("name") String name){
		return contactService.searchContactByName(name);
	}
	
	// Add a new contact
	@RequestMapping(value="/contact/", method=RequestMethod.POST)
	public boolean addContact(@RequestBody Contact contact){
		return contactService.addContact(contact);
	}
	
	// Delete a contact by ID
	@RequestMapping(value="/contact/{id}", method=RequestMethod.DELETE)
	public boolean removeContactById(@PathVariable("id") long id){
		return contactService.removeContactById(id);
	}
	
	// Modify a contact by ID
	@RequestMapping(value="/contact/{id}", method=RequestMethod.PUT)
	public boolean modifyContactById(@PathVariable("id") long id, @RequestBody Contact contact){
		return contactService.modifyContactById(id, contact);
	}
}
