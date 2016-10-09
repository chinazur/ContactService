package com.tang.contactservice.model;

public class Contact {

	private final String name;
	private final String phone;
	private final String address;
	
	//Constructor with name, phone and address
	public Contact(String name, String phone, String address){
		this.name = name;
		this.phone = phone;
		this.address = address;
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
}
