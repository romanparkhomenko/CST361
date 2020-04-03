package entity;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@Entity(name = "entity.UserEntity")
@XmlRootElement
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
public class UserEntity implements Serializable {
	/**
     * Default value included to remove warning. Remove or modify at will.
     **/
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id", unique=true, nullable = false)
    private Long id;
    
    @NotNull
    @NotEmpty
    @Column(name="username")
    private String username;

    @NotNull
    @NotEmpty
    @Column(name="password")
	private String password;

    @NotNull
    @NotEmpty
    @Column(name="firstname")
	private String firstName;

    @NotNull
    @NotEmpty
    @Column(name="lastname")
	private String lastName;

	@NotNull
    @NotEmpty
    @Email
    @Column(name="email")
	private String email;

	@NotNull
    @Size(min = 9, max = 12)
    @Digits(fraction = 0, integer = 12)
    @Column(name = "phonenumber")
	private String phoneNumber;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
}