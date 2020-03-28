package model;

import java.security.Principal;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@ManagedBean
@SessionScoped
public class User {

	// Declare variables and input validation for User object.
	@NotNull(message = "Please enter a Username. This is a required field.")
	@Size(min = 3, max = 15)
	private String username;

	@NotNull(message = "Please enter a Password. This is a required field.")
	@Size(min = 4, max = 15)
	private String password;

	@NotNull(message = "Please enter your first name. This is a required field.")
	@Size(min = 4, max = 15)
	private String firstName;

	@NotNull(message = "Please enter your last name. This is a required field.")
	@Size(min = 4, max = 15)	
	private String lastName;

	@NotNull(message = "Please enter your email. This is a required field.")
	@Size(min = 5, max = 40)
	private String email;

	@NotNull(message = "Please enter your phone number with the area code. This is a required field.")
	@Size(min = 10, max = 11)
	private String phoneNumber;

	// Constructor
	public User(String username, String password, String firstName, String lastName, String email, String phoneNumber) {
		super();
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phoneNumber = phoneNumber;
	}

	// Default Generic Constructor
	public User() {

	}

	@PostConstruct
	public void init() {
		// Get the logged in Principle
		Principal principle = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
		if (principle == null) {
			setUsername("roman");
			setPassword("admin");
			setFirstName("Roman");
			setLastName("Parkhomenko");
			setEmail("roman@acme.com");
			setPhoneNumber("1234567890");
		} else {
			setUsername("gcu");
			setPassword("Lopes2020!");
			setFirstName(principle.getName());
			setLastName("GcuUser");
			setEmail("gcu@acme.com");
			setPhoneNumber("1234567890");
		}
	}

	// Getters for user data
	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	// Setters for user data
	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

}
