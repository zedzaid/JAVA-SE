package com.example.demo.models;

import java.time.LocalDate;
import java.util.Arrays;

public class Contact {
	private String name;
	private String address;
	private String[] mobileNumber;
	private String imageReference;
	private LocalDate dateOfBirth;
	private String[] email;
	private int groupId;
	public Contact() {
		// TODO Auto-generated constructor stub
	}
	public Contact(String name, String address, String[] mobileNumber, String imageReference, LocalDate dateOfBirth,
			String[] email, int groupId) {
		super();
		this.name = name;
		this.address = address;
		this.mobileNumber = mobileNumber;
		this.imageReference = imageReference;
		this.dateOfBirth = dateOfBirth;
		this.email = email;
		this.groupId = groupId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String[] getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String[] mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getImageReference() {
		return imageReference;
	}
	public void setImageReference(String imageReference) {
		this.imageReference = imageReference;
	}
	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String[] getEmail() {
		return email;
	}
	public void setEmail(String[] email) {
		this.email = email;
	}
	public int getGroupId() {
		return groupId;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((dateOfBirth == null) ? 0 : dateOfBirth.hashCode());
		result = prime * result + Arrays.hashCode(email);
		result = prime * result + groupId;
		result = prime * result + ((imageReference == null) ? 0 : imageReference.hashCode());
		result = prime * result + Arrays.hashCode(mobileNumber);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Contact other = (Contact) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (dateOfBirth == null) {
			if (other.dateOfBirth != null)
				return false;
		} else if (!dateOfBirth.equals(other.dateOfBirth))
			return false;
		if (!Arrays.equals(email, other.email))
			return false;
		if (groupId != other.groupId)
			return false;
		if (imageReference == null) {
			if (other.imageReference != null)
				return false;
		} else if (!imageReference.equals(other.imageReference))
			return false;
		if (!Arrays.equals(mobileNumber, other.mobileNumber))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Contact [name=" + name + ", address=" + address + ", mobileNumber=" + Arrays.toString(mobileNumber)
				+ ", imageReference=" + imageReference + ", dateOfBirth=" + dateOfBirth + ", email="
				+ Arrays.toString(email) + ", groupId=" + groupId + "]";
	}
	
	
	
	

}
