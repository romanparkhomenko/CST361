package business;

import java.util.List;

import javax.ejb.Local;

import model.User;

/**
* The UserBusinessInterface is a Local interface that allows
* implementation of the UserBusinessServic
*
* @author  Roman Parkhomenko
* @version 1.0
* @since   2020-02-27 
*/

@Local
public interface UserBusinessInterface {
	public void addUser(User user);
	public User getUser(int index);
	public List<User> getUserList();
}