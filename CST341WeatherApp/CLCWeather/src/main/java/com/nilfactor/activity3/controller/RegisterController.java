package com.nilfactor.activity3.controller;
import java.io.IOException;
import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.nilfactor.activity3.logic.LoginService;
import com.nilfactor.activity3.model.User;

import data.UserEntityRepository;
import entity.UserEntity;

@ManagedBean
@SessionScoped
public class RegisterController implements Serializable {
	private static final long serialVersionUID = 1L;
	
	// Properties to access in JSF
	private String username;
	private String password;
	private String email;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String message;
	
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
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
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
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public String getMessage() {
		// Load message from session if it exists
		if (message == null) {
			HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
			HttpSession session = req.getSession(false);
			if (session != null) {
				message = (String) session.getAttribute("register_message");
			}
		}
		
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
		
		// store message in session
		HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		HttpSession session = req.getSession(false);
		if (session != null) {
			session.setAttribute("register_message", message);
		}
	}
	
	
	public void register() {
		try {
			UserEntity ue = new UserEntity();
			ue.setUsername(username);
			ue.setPassword(password);
			ue.setFirstName(firstName);
			ue.setLastName(lastName);
			ue.setEmail(email);
			ue.setPhoneNumber(phoneNumber);
			
			UserEntityRepository.saveUserEntity(ue);
			
			HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
			HttpServletResponse res = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			res.sendRedirect(req.getContextPath() + "/faces/login.xhtml");
		} catch (Exception e) {
			this.setMessage(e.getMessage());
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
		}
	}
}
