package com.tang.contactservice.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Tang
 * POJO for Contact
 */
public class Contact {

	//contact information
	private long id;

	private final String name;
	private final String phone;
	private final String address;
	
	
	//Constructor with JSON properties binding
	@JsonCreator
	public Contact(
			@JsonProperty("name") String name, 
			@JsonProperty("phone") String phone, 
			@JsonProperty("address") String address){
		this.name = name;
		this.phone = phone;
		this.address = address;
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public String getPhone() {
		return phone;
	}

	public String getAddress() {
		return address;
	}
	
	@Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.name);
        hash = 17 * hash + Objects.hashCode(this.phone);
        hash = 17 * hash + Objects.hashCode(this.address);
        return hash;
    }
	
	//if all fields are equal, then 2 contacts are equal
	@Override
	public boolean equals(Object object){
		if(object == null){
			return false;
		}else if(object == this){
			return true;
		}else if(! (object instanceof Contact)){
			return false;
		}else{
			Contact contact = (Contact) object;
			if(name.equals(contact.getName()) 
					&& phone.equals(contact.getPhone()) 
					&& address.equals(contact.getAddress())){
				return true;
			}
		}
		return false;
	}
}
