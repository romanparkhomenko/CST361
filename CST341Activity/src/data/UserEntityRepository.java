package data;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import entity.UserEntity;
import model.User;

/**
* UserEntityRepository 
*
* @author  Richard Williamson
* @version 1.0
* @since   2020-04-02
*/

public class UserEntityRepository {
	 public static void saveUserEntity(User u) {
		 UserEntity ue = new UserEntity();
		 ue.setId(u.getId());
		 ue.setUsername(u.getUsername());
		 ue.setPassword(u.getPassword());
		 ue.setEmail(u.getEmail());
		 ue.setFirstName(u.getFirstName());
		 ue.setLastName(u.getLastName());
		 ue.setPhoneNumber(u.getPhoneNumber());
		 
		 saveUserEntity(ue);
	 }
	 
	 public static void saveUserEntity(UserEntity ue) {
		 Transaction transaction = null;
	     try {
	       	Session session = HibernateUtil.getSessionFactory().openSession();
	        			
	        // start a transaction
	        transaction = session.beginTransaction();
	        session.save(ue);
	        
	        // commit transaction
	       transaction.commit();
	     } catch (Exception e) {
	    	 if (transaction != null) {
	    		 transaction.rollback();
	         }
	         e.printStackTrace();
	     }
	 }


	 @SuppressWarnings("unchecked")
	 public static UserEntity findUserByUsername(String username) {
		 Transaction transaction = null;
		 try {
			// start a transaction
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			transaction = session.beginTransaction();
			
			String hql = "select u from entity.UserEntity u where username = :username";
			List<UserEntity> results = session.createQuery(hql)
					.setParameter("username", username)
					.list();
			
			transaction.commit();
			
			System.out.println("Size => " + results.size());
			
			if (results.size() > 0) {
				return results.get(0);
			}
	     } catch (Exception e) {
	    	 if (transaction != null) {
	    		 transaction.rollback();
	         }
	         e.printStackTrace();
	     }
		 return new UserEntity();
	 }
	 
	 @SuppressWarnings("unchecked")
	 public static List<UserEntity> findAllOrderedById() {
		 Transaction transaction = null;
		 try {
			 Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			 transaction = session.beginTransaction();
			 Query q = session.createQuery("select u from entity.UserEntity u");
			 List<UserEntity> users = q.list();
			 
			 transaction.commit();
			 return users;
		 } catch (Exception e) {
			 if (transaction != null) {
	    		 transaction.rollback();
	         }
	         e.printStackTrace();
	     }
		 
		 return new ArrayList<UserEntity>();
	 }
}
