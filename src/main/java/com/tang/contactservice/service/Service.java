/**
 * 
 */
package com.tang.contactservice.service;

import java.util.List;

import com.tang.contactservice.model.Contact;

/**
 * @author Tang
 * Service Interface which defines CRUD operations for contacts
 */
public interface Service {
	
	//add a contact
	Contact addContact(Contact contact);
	
	//remove a contact
	boolean removeContactById(long id);
	
	//modify a contact
	Contact modifyContactById(long id, Contact newContact);
	
	//search a contact by exact name or substring
	List<Contact> searchContactByName(String name);
	
	//return the list of contacts
	List<Contact> getAllContacts();
	
	boolean findContactById(long id);
}
