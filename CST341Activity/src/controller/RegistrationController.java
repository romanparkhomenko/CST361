package controller;

import java.io.IOException;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import business.UserBusinessInterface;
import model.User;

/**
* The RegistrationController is a view scoped, managed bean that controls new user registration. 
*
* @author  Roman Parkhomenko
* @version 1.0
* @since   2020-02-27 
*/

@ManagedBean
@ViewScoped
public class RegistrationController {
	
	// Inject UserBusinessInterface
	@Inject
	private UserBusinessInterface userService;
	
	public void register(){
		
		//Get the new user values from the register form and create user
		FacesContext context = FacesContext.getCurrentInstance();
		User user = context.getApplication().evaluateExpressionGet(context, "#{user}", User.class);
		
		// Add new user to the Users ArrayList
		userService.addUser(user);
		
		try {
			context.getExternalContext().redirect("/faces/login.xhtml");
		} catch (IOException e) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Whoops, your username and password are incorrect.");
		    FacesContext.getCurrentInstance().addMessage("registrationForm:password", msg);
		}
	}
	
	 // Get User Service
    public UserBusinessInterface getService() {
		return userService;
	}

}
