/**
 * 
 */
package com.tang.contactservice;

import java.util.List;

import com.tang.contactservice.model.Contact;

/**
 * @author Tang
 * Interface that contains web service methods
 */
public interface Service {
	
	//add a contact
	public boolean addContact(Contact contact);
	
	//remove a contact
	public boolean removeContact(Contact contact);
	
	//modify a contact
	public boolean modifyContact(Contact contact);
	
	//search a contact by exact name or substring
	public List<Contact> searchContactByName(String name);
	
	//return the list of contacts
	public List<Contact> getAllContacts();
}
