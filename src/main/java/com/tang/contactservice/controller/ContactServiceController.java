package com.tang.contactservice.controller;

import java.util.List;

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
public class ContactServiceController {
	
	
	@Autowired
	ContactService contactService;
	
	// Get all users
	@RequestMapping(value="/contact/", method=RequestMethod.GET)
	public List<Contact> getAllContacts(){
		return contactService.getAllContacts();
	}
	
	// Get contact(s) by name
	@RequestMapping(value="/contact/{name}", method=RequestMethod.GET)
	public List<Contact> searchContactByName(@PathVariable("name") String name){
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
	
	// Modify a contact
	@RequestMapping(value="/contact/{id}", method=RequestMethod.PUT)
	public boolean modifyContact(@PathVariable("id") long id, @RequestBody Contact contact){
		return contactService.modifyContactById(id, contact);
	}
}
