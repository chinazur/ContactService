package com.tang.contactservice.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.tang.contactservice.model.Contact;

/**
 * @author Tang
 * Implementation for interface Service
 */
@Component
public class ContactService implements Service{
	
	//counter used to increment contact's ID
	private static final AtomicLong counter = new AtomicLong();
	
    public static final Logger LOG = LoggerFactory.getLogger(ContactService.class);

	/**
	 * ConcurrentHashMap to store all contacts
	 * key: contact's ID
	 * value: Contact object
	 */
	private final Map<Long, Contact> contactList = new HashMap<Long, Contact>();
	
	/**
	 * Dummy constructor
	 */
	public ContactService(){
	}
	
	/**
	 * if contact already exists, do not create and return false
	 * otherwise throw an exception
	 */
	public Contact addContact(Contact contact) throws Exception {
		if(!contactList.containsValue(contact)){
			long id = counter.incrementAndGet();
			contact.setId(id);
			contactList.put(id,contact);
			return contact;
		}
		throw new Exception("contact already exists");
	}

	/**
	 * if id exists, replace the corresponding contact by new contact and return it
	 * else throw an exception
	 */
	public Contact modifyContactById(long id, Contact newContact) throws Exception {
		if(contactList.containsKey(id)){
			newContact.setId(id);
			contactList.replace(id, newContact);
			return newContact;
		}
		throw new Exception("contact does not exists");
	}

	/**
	 * Loop through all contacts for name matches (including sub string match)
	 * return all found matches
	 */
	public List<Contact> searchContactByName(String name) {
		List<Contact> list= new ArrayList<Contact>();
		Iterator<Entry<Long, Contact>> it = contactList.entrySet().iterator();
		
		while(it.hasNext()){
			Map.Entry<Long, Contact> me = (Map.Entry<Long, Contact>)it.next();
			
			if(me.getValue().getName().contains(name)){
				list.add(me.getValue());
			}
		}
		
		return list;
	}

	/**
	 * return contact list
	 */
	public List<Contact> getAllContacts() {
		return new ArrayList(contactList.values());
	}

	/**
	 * if id exists, remove it from the list 
	 * otherwise throw an Exception 
	 */	
	public void removeContactById(long id) throws Exception {
		if(contactList.containsKey(id)){
			contactList.remove(id, contactList.get(id));
			return;
		}
		throw new Exception("contact does not exists");
	}
}
