package com.tang.contactservice.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Tang POJO for Contact
 */
public class Contact {

	// contact information
	private long id;

	private final String name;
	private final String phone;
	private final String address;

	// Constructor with JSON properties binding
	@JsonCreator
	public Contact(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
			@JsonProperty("address") String address) {
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
		return new HashCodeBuilder(17, 37).append(this.name).append(this.phone).append(this.address).toHashCode();
	}

	//!! ID is not checked here
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (obj.getClass() != getClass()) {
			return false;
		}
		Contact rhs = (Contact) obj;
		return new EqualsBuilder().append(name, rhs.getName()).append(phone, rhs.getPhone())
				.append(address, rhs.getAddress()).isEquals();
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
