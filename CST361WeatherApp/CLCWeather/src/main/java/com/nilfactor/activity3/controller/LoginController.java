package com.nilfactor.activity3.controller;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.nilfactor.activity3.logic.LoginService;
import com.nilfactor.activity3.model.User;
import com.nilfactor.activity3.model.WeatherData;
import com.nilfactor.activity3.utility.ServiceService;

import entity.WeatherDataEntity;

@ManagedBean
@SessionScoped
public class LoginController implements Serializable {
	private static final long serialVersionUID = 1L;
	
	// Properties to access in JSF
	private String username;
	private String password;
	private String message;
	private String serverMessage;

	// to be used later
	private User user;
	
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
	
	public String getMessage() {
		// Load message from session if it exists
		if (message == null) {
			HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
			HttpSession session = req.getSession(false);
			if (session != null) {
				message = (String) session.getAttribute("login_message");
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
			session.setAttribute("login_message", message);
		}
	}

	public String getServerMessage() {
		// Load message from session if it exists
		if (serverMessage == null) {
			HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
			HttpSession session = req.getSession(false);
			if (session != null) {
				serverMessage = (String) session.getAttribute("server_message");
			}
		}

		return serverMessage;
	}

	public void setServerMessage(String message) {
		this.serverMessage = message;

		// store message in session
		HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		HttpSession session = req.getSession(false);
		if (session != null) {
			session.setAttribute("server_message", message);
		}
	}
	
	public User getUser() {
		// load user from session if it exists
		if (user == null) {
			HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
			HttpSession session = req.getSession(false);
			if (session != null) {
				user = User.getUser(username);
			}
		}
		
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public String validateLogin() {
		// lookup user from database
		user = User.getUser(username);
		HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		HttpSession session = req.getSession(false);
		
		// validate login through business logic
		if (LoginService.isValidLogin(user, username, password)) {
			// store username in session
			if (session != null) {
				session.setAttribute("username", username);
			}
			
			// set welcome message
			setMessage(user.getFirstName() + "!");
			return "/app/home.xhtml";
		}
		
		// set login failure message
		setMessage("Invalid username/password");
		
		return "/faces/login.xhtml";
	}
	
	public void logout() throws IOException {
		HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		HttpServletResponse res = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
		HttpSession session = req.getSession(false);
		session.invalidate();
		res.sendRedirect(req.getContextPath() + "/faces/login.xhtml");
	}

	public List<WeatherDataEntity> getAllWeatherData() {
		List<WeatherDataEntity> weatherData = ServiceService.getWeatherDataRepository().getAll();
		if (weatherData != null) {
			return weatherData;
		} else {
			setServerMessage("Could not reach server");
			return null;
		}
	}
}
