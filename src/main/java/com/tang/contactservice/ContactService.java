/**
 * 
 */
package com.tang.contactservice;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Component;

import com.tang.contactservice.model.Contact;

@Component
public class ContactService implements Service{
	
	private static final AtomicLong counter = new AtomicLong();
	private final Map<Long, Contact> contactList = new ConcurrentHashMap<Long, Contact>();
	
	public ContactService(){
		
	}
	
	public boolean addContact(Contact contact) {
		if(!contactList.containsValue(contact)){
			contactList.put(counter.incrementAndGet(), contact);
			return true;
		}
		return false;
	}

	public boolean modifyContactById(long id, Contact newContact) {
		contactList.replace(newContact.getId(), newContact);
		return false;
	}

	public List<Contact> searchContactByName(String name) {
		List<Contact> list= new ArrayList<Contact>();
		Iterator<Entry<Long, Contact>> it = contactList.entrySet().iterator();
		
		while(it.hasNext()){
			Map.Entry<Long, Contact> me = (Map.Entry<Long, Contact>)it.next();
			
			if(me.getValue().getName().contains(name)){
				list.add(me.getValue());
			}
			
			it.remove();
		}
		
		return list;
	}

	public List<Contact> getAllContacts() {
		return new ArrayList<Contact> (contactList.values());
	}

	public boolean removeContactById(long id) {
		if(contactList.get(id) != null){
			return contactList.remove(id, contactList.get(id));
		}
		return false;
	}

	public Contact searchContactById(long id) {
		return contactList.get(id);
	}


}
