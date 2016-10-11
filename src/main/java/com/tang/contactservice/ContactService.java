package com.tang.contactservice;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.tang.ContactService.AppTest;
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
	private final Map<Long, Contact> contactList = new ConcurrentHashMap<Long, Contact>();
	
	/**
	 * Dummy constructor
	 */
	public ContactService(){
	}
	
	/**
	 * if contact already exists, do not create and return false
	 * otherwise create a new one
	 */
	public boolean addContact(Contact contact) {
		if(!contactList.containsValue(contact)){
			contactList.put(counter.incrementAndGet(), contact);
			return true;
		}
		LOG.info("contact already exists");
		return false;
	}

	/**
	 * if id exists, replace the corresponding contact by new contact and return true
	 * else return false
	 */
	public boolean modifyContactById(long id, Contact newContact) {
		if(contactList.containsKey(id)){
			contactList.replace(id, newContact);
			return true;
		}
		LOG.info("contact does not exists");
		return false;
	}

	/**
	 * Loop through all contacts for name matches (including sub string match)
	 * return all found matches
	 */
	public Map<Long, Contact> searchContactByName(String name) {
		Map<Long, Contact> list= new HashMap<Long, Contact>();
		Iterator<Entry<Long, Contact>> it = contactList.entrySet().iterator();
		
		while(it.hasNext()){
			Map.Entry<Long, Contact> me = (Map.Entry<Long, Contact>)it.next();
			
			if(me.getValue().getName().contains(name)){
				list.put(me.getKey(), me.getValue());
			}
			
			//it.remove();
		}
		
		return list;
	}

	/**
	 * return contact list
	 */
	public Map<Long, Contact> getAllContacts() {
		return contactList;
	}

	/**
	 * if id exists, remove it from the list and return true
	 * otherwise return false
	 */
	public boolean removeContactById(long id) {
		if(contactList.get(id) != null){
			return contactList.remove(id, contactList.get(id));
		}
		LOG.info("contact does not exists");
		return false;
	}
}
