/**
 * 
 */
package com.tang.contactservice;

import java.util.Map;

import com.tang.contactservice.model.Contact;

/**
 * @author Tang
 * Service Interface which defines CRUD operations for contacts
 */
public interface Service {
	
	//add a contact
	public boolean addContact(Contact contact);
	
	//remove a contact
	public boolean removeContactById(long id);
	
	//modify a contact
	public boolean modifyContactById(long id, Contact newContact);
	
	//search a contact by exact name or substring
	public Map<Long, Contact> searchContactByName(String name);
	
	//return the list of contacts
	public Map<Long, Contact> getAllContacts();
}
