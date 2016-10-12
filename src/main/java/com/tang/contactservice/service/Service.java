/**
 * 
 */
package com.tang.contactservice.service;

import java.util.List;
import java.util.Map;

import com.tang.contactservice.model.Contact;

/**
 * @author Tang
 * Service Interface which defines CRUD operations for contacts
 */
public interface Service {
	
	//add a contact
	public Contact addContact(Contact contact) throws Exception;
	
	//remove a contact
	public void removeContactById(long id) throws Exception;
	
	//modify a contact
	public Contact modifyContactById(long id, Contact newContact) throws Exception;
	
	//search a contact by exact name or substring
	public List<Contact> searchContactByName(String name);
	
	//return the list of contacts
	public List<Contact> getAllContacts();
}
