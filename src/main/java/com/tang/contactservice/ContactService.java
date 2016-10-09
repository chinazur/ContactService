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

import org.springframework.stereotype.Component;

import com.tang.contactservice.model.Contact;

@Component
public class ContactService implements Service{
	
	private final Map<String, Contact> contactList = new ConcurrentHashMap<String, Contact>();
	
	public Map<String, Contact> getContactList() {
		return contactList;
	}

	public ContactService(){
		
	}
	
	public boolean addContact(Contact contact) {
		if(!contactList.containsKey(contact.getName())){
			contactList.put(contact.getName(), contact);
			return true;
		}
		return false;
	}

	public boolean removeContact(Contact contact) {
		return contactList.remove(contact.getName(), contact);
	}

	public boolean modifyContact(Contact newContact) {
		contactList.replace(newContact.getName(), newContact);
		return false;
	}

	public List<Contact> searchContactByName(String name) {
		List<Contact> list= new ArrayList<Contact>();
		Iterator<Entry<String, Contact>> it = contactList.entrySet().iterator();
		
		while(it.hasNext()){
			Map.Entry<String, Contact> me = (Map.Entry<String, Contact>)it.next();
			
			if(me.getKey().contains(name)){
				list.add(me.getValue());
			}
			
			it.remove();
		}
		
		return list;
	}

	public List<Contact> getAllContacts() {
		return new ArrayList<Contact> (contactList.values());
	}


}
