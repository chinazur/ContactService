package com.tang.contactservice.controller;

import java.util.List;

import javax.ws.rs.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
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
	
	@RequestMapping(value="/contact/", method=RequestMethod.GET)
	public List<Contact> getAllContacts(){
		return contactService.getAllContacts();
	}
	
	@RequestMapping(value="/contact/{name}", method=RequestMethod.GET)
	public List<Contact> searchContactByName(@PathParam("name") String name){
		return contactService.searchContactByName(name);
	}
}
