package controller;

import java.io.IOException;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import data.UserEntityRepository;
import entity.UserEntity;
import model.User;

/**
* The RegistrationController is a view scoped, managed bean that controls user login. 
*
* @author  Roman Parkhomenko
* @version 1.0
* @since   2020-02-27 
*/

@ManagedBean
@SessionScoped
public class LoginController {   
	// Login function called by Login Form command button.
	public String login() {
		//Get the creds from the login form
		FacesContext context = FacesContext.getCurrentInstance();
		User sessionUser = context.getApplication().evaluateExpressionGet(context, "#{user}", User.class);
		
		//Call the array list from user service.
		if (sessionUser != null) {
			System.out.println(sessionUser.getUsername() + " " + sessionUser.getPassword());
			final UserEntity user = UserEntityRepository.findUserByUsername(sessionUser.getUsername());
			
			if (user.getUsername().equals(sessionUser.getUsername()) && user.getPassword().equals(sessionUser.getPassword())) {
				FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("user", user);
				//go to main page if login data is correct
				return "/app/home.xhtml";
			}
		}
		
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Whoops, your username and password are incorrect.");
	    context.addMessage("loginForm:password", msg);
		//refresh page if login data is incorrect
		return "/faces/loginError.xhtml";
	}
	 
	// Logout function called in home page.
    public void logout() {
    	FacesContext context = FacesContext.getCurrentInstance();
    	context.getExternalContext().invalidateSession();
        try {
			context.getExternalContext().redirect("/faces/login.xhtml?faces-redirect=true");
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}