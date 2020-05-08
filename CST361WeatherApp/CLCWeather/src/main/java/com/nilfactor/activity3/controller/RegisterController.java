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
import com.nilfactor.activity3.userFactory.RandomInfo;
import com.nilfactor.activity3.userFactory.RandomizeFactory;
import com.nilfactor.activity3.utility.ServiceService;

import data.HibernateUtil;
import data.UserEntityRepository;
import entity.UserEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

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

	public void setLoginMessage(String message) {
		this.message = message;

		// store message in session
		HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		HttpSession session = req.getSession(false);
		if (session != null) {
			session.setAttribute("login_message", message);
		}
	}

	public void createRandomUser() {
		System.out.println("creating random user \n");
		try {
			HibernateUtil hibernateUtil = ServiceService.getHibernateUtil();
			SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
			//check to see if DB is up first
			if (sessionFactory != null) {
				RandomizeFactory myFactory = new RandomizeFactory();
				RandomInfo newPass = myFactory.getRandRequest("Password");
				RandomInfo newUser = myFactory.getRandRequest("Name");
				RandomInfo newUser2 = myFactory.getRandRequest("Username");

				UserEntity ue = new UserEntity();
				ue.setUsername(newUser.generate());
				ue.setPassword(newPass.generate());
				ue.setFirstName(newUser2.generate());
				ue.setLastName("lastname");
				ue.setEmail("email@email.com");
				ue.setPhoneNumber("1234567890");

				ServiceService.getUserEntityRepository().saveUserEntity(ue);

				HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
				HttpServletResponse res = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();

				setLoginMessage("Username: " + ue.getUsername() + " Password: " + ue.getPassword());
				res.sendRedirect(req.getContextPath() + "/faces/login.xhtml");
			} else {
				HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
				HttpServletResponse res = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
				setMessage("Whoops, looks like our server is down!");
				res.sendRedirect(req.getContextPath() + "/faces/register.xhtml");
			}
		} catch(Exception e) {
			this.setMessage(e.getMessage());
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
		}

	}
	
	public void register() {
		System.out.println("registering user \n");
		try {
			HibernateUtil hibernateUtil = ServiceService.getHibernateUtil();
			SessionFactory sessionFactory = hibernateUtil.getSessionFactory();

			if (sessionFactory != null) {
				UserEntity ue = new UserEntity();
				ue.setUsername(username);
				ue.setPassword(password);
				ue.setFirstName(firstName);
				ue.setLastName(lastName);
				ue.setEmail(email);
				ue.setPhoneNumber(phoneNumber);

				ServiceService.getUserEntityRepository().saveUserEntity(ue);

				HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
				HttpServletResponse res = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
				res.sendRedirect(req.getContextPath() + "/faces/login.xhtml");
			} else {
				HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
				HttpServletResponse res = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
				setMessage("Whoops, looks like our server is down!");
				res.sendRedirect(req.getContextPath() + "/faces/register.xhtml");
			}
		} catch (Exception e) {
			this.setMessage(e.getMessage());
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
		}
	}
}
