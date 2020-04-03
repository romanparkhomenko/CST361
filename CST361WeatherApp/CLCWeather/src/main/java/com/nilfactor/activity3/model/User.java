package com.nilfactor.activity3.model;

import data.UserEntityRepository;
import entity.UserEntity;

public class User {
	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private String username;
	private String password;
	private String phoneNumber; 
	
	public User() {
		
	}
	
	public User(Long id, String firstName, String lastName, String email, String username, String password, String phoneNumber) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.username = username;
		this.password = password;
		this.phoneNumber = phoneNumber;
	}
	
	public static User getUser(String username) {
		UserEntityRepository ur = new UserEntityRepository();
		UserEntity ue = UserEntityRepository.findUserByUsername(username);
		
		return new User(ue.getId(), ue.getFirstName(), ue.getLastName(), ue.getEmail(), ue.getUsername(), ue.getPassword(), ue.getPhoneNumber());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
}
