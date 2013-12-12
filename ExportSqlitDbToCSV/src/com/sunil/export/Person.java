package com.sunil.export;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;

public class Person implements Serializable{

	@DatabaseField(generatedId=true)
	private int id;
	@DatabaseField
	private String firtname;
	@DatabaseField
	private String lastname;
	@DatabaseField
	private String address;
	@DatabaseField
	private String email;
	
	
	public Person(){
		
	}
	
	public Person(String fname, String lname, String address, String email){
		this.firtname=fname;
		this.lastname=lname;
		this.address=address;
		this.email=email;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirtname() {
		return firtname;
	}

	public void setFirtname(String firtname) {
		this.firtname = firtname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
