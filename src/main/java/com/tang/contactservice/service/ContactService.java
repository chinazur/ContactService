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
 * @author Tang Implementation for interface Service
 */
@Component
public class ContactService implements Service {

	// counter used to increment contact's ID
	private static final AtomicLong counter = new AtomicLong();

	public static final Logger LOG = LoggerFactory.getLogger(ContactService.class);

	/**
	 * ConcurrentHashMap to store all contacts key: contact's ID value: Contact
	 * object
	 */
	private final Map<Long, Contact> contactList = new HashMap<Long, Contact>();

	/**
	 * Dummy constructor
	 */
	public ContactService() {
	}

	/**
	 * if contact already exists, do not create and return false otherwise throw
	 * an exception
	 */
	public Contact addContact(Contact contact) {
		LOG.info("adding contact : {}", contact);

		synchronized (contactList) {
			if (!contactList.containsValue(contact)) {
				long id = counter.incrementAndGet();
				contact.setId(id);
				return contactList.put(id, contact);
			}
		}

		return null;
	}

	/**
	 * if id exists, replace the corresponding contact by new contact and return
	 * it
	 */
	public Contact modifyContactById(long id, Contact newContact) {
		LOG.info("modifying contact : {}", newContact);

		synchronized (contactList) {
			if (!contactList.containsValue(newContact)) {
				newContact.setId(id);
				return contactList.replace(id, newContact);
			}
		}
		return null;
	}

	/**
	 * Loop through all contacts for name matches (including sub string match)
	 * return all found matches
	 */
	public List<Contact> searchContactByName(String name) {
		LOG.info("searching contact by name : {}", name);

		List<Contact> list = new ArrayList<Contact>();

		synchronized (contactList) {
			Iterator<Entry<Long, Contact>> it = contactList.entrySet().iterator();

			while (it.hasNext()) {
				Map.Entry<Long, Contact> me = (Map.Entry<Long, Contact>) it.next();

				if (me.getValue().getName().contains(name)) {
					list.add(me.getValue());
				}
			}
		}
		return list;
	}

	/**
	 * return all contacts
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Contact> getAllContacts() {
		LOG.info("getting all contacts");

		return new ArrayList(contactList.values());
	}

	/**
	 * if id exists, remove it and its corresponding value from the map
	 */
	public void removeContactById(long id) {
		LOG.info("removing contact with id : {} ", id);

		synchronized (contactList) {
			contactList.remove(id, contactList.get(id));
		}

	}

	public boolean findContactById(long id) {
		synchronized (contactList) {
			return contactList.containsKey(id);
		}
	}
}
