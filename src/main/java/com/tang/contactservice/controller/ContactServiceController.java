package com.tang.contactservice.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
@RequestMapping(value = "/contacts/")
public class ContactServiceController {

	public static final Logger LOG = LoggerFactory.getLogger(ContactServiceController.class);
	public static final String CONTACT_ALREADY_EXISTS = "Contact Already Exists";
	public static final String NO_CONTACT_FOUND = "No contact found";
	public static final String CONTACT_DELETED = "Contact deleted";
	public static final String ALL_CONTACTS_DELETED = "All contacts deleted";


	// used to call CRUD operations
	@Autowired
	private ContactService contactService;

	// Get all Contacts
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Contact>> getAllContacts() {
		List<Contact> list = contactService.getAllContacts();
		if(list.isEmpty()){
			return new ResponseEntity(NO_CONTACT_FOUND, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Contact>>(list, HttpStatus.OK);
	}
	
	// Delete all Contacts
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity deleteAllContacts() {
		contactService.deleteAllContacts();
		return new ResponseEntity(HttpStatus.OK);
	}

	// Get contact(s) by name
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "{name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Contact>> searchContactByName(@PathVariable("name") String name) {
		List<Contact> list = contactService.searchContactByName(name);
		if(list.isEmpty()){
			return new ResponseEntity(NO_CONTACT_FOUND, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Contact>>(list, HttpStatus.OK);
	}

	// Add a new contact
	@SuppressWarnings("rawtypes")
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity addContact(@RequestBody Contact contact){
		Contact result = contactService.addContact(contact);
		if (result != null) {
			return new ResponseEntity<Contact>(result, HttpStatus.CREATED);
		}
		return new ResponseEntity<String>(CONTACT_ALREADY_EXISTS, HttpStatus.CONFLICT);
	}

	// Delete a contact by ID
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	public ResponseEntity removeContactById(@PathVariable("id") long id) {
		if(contactService.findContactById(id)){
			if(contactService.removeContactById(id)){
				return new ResponseEntity<String>(CONTACT_DELETED, HttpStatus.OK);
			}
		}
		return new ResponseEntity<String>(NO_CONTACT_FOUND, HttpStatus.NOT_FOUND);
	}

	// Modify a contact by ID
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity modifyContactById(@PathVariable("id") long id, @RequestBody Contact contact) {
		if(contactService.findContactById(id)){
			Contact result = contactService.modifyContactById(id, contact);
			if (result != null) {
				return new ResponseEntity<Contact>(result, HttpStatus.CREATED);
			}
			return new ResponseEntity<String>(CONTACT_ALREADY_EXISTS, HttpStatus.CONFLICT);
		}
		return new ResponseEntity<String>(NO_CONTACT_FOUND, HttpStatus.NOT_FOUND);
	}
}
