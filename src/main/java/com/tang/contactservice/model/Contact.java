package com.tang.contactservice.model;

public class Contact {

	private final long id;
	private final String name;
	private final String phone;
	private final String address;
	
	//Constructor with name, phone and address
	public Contact(long id, String name, String phone, String address){
		this.id = id;
		this.name = name;
		this.phone = phone;
		this.address = address;
	}

	public long getId() {
		return id;
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
	
	public boolean equals(Object object){
		if(object == null){
			return false;
		}else if(! (object instanceof Contact)){
			return false;
		}else{
			Contact contact = (Contact) object;
			if(id == contact.getId() && name.equals(contact.getName()) 
					&& phone.equals(contact.getPhone()) && address.equals(contact.getAddress())){
				return true;
			}
		}
		return false;
	}

}
