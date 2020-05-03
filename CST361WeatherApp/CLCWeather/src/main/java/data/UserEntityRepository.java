package data;

import java.util.ArrayList;
import java.util.List;

import javax.interceptor.Interceptors;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.nilfactor.activity3.utility.LogInterceptor;
import com.nilfactor.activity3.utility.ServiceService;

import entity.UserEntity;

@Interceptors(LogInterceptor.class)
public class UserEntityRepository {
	private HibernateUtil hibernateUtil = ServiceService.getHibernateUtil(); 
	
	@Interceptors(LogInterceptor.class)
	public void saveUserEntity(UserEntity ue) {
		 Transaction transaction = null;
	     try {
	       	Session session = hibernateUtil.getSessionFactory().openSession();
	        			
	        // start a transaction
	        transaction = session.beginTransaction();
	        session.save(ue);
	        
	        // commit transaction
	       transaction.commit();
	     } catch (Exception e) {
	    	 if (transaction != null) {
	    		 transaction.rollback();
	         }
	    	 ServiceService.getLogger("UserEntityRepository").error(e.getMessage());
	    	 ServiceService.getLogger("UserEntityRepository").error(e.getStackTrace());
	     }
	 }


	 @SuppressWarnings("unchecked")
	 @Interceptors(LogInterceptor.class)
	 public UserEntity findUserByUsername(String username) {
		 Transaction transaction = null;
		 try {
			// start a transaction
			Session session = hibernateUtil.getSessionFactory().getCurrentSession();
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
	    	 ServiceService.getLogger("UserEntityRepository").error(e.getMessage());
	    	 ServiceService.getLogger("UserEntityRepository").error(e.getStackTrace());
	     }
		 return new UserEntity();
	 }
	 
	 @SuppressWarnings("unchecked")
	 @Interceptors(LogInterceptor.class)
	 public List<UserEntity> findAllOrderedById() {
		 Transaction transaction = null;
		 try {
			 Session session = hibernateUtil.getSessionFactory().getCurrentSession();
			 transaction = session.beginTransaction();
			 Query q = session.createQuery("select u from entity.UserEntity u");
			 List<UserEntity> users = q.list();
			 
			 transaction.commit();
			 return users;
		 } catch (Exception e) {
			 if (transaction != null) {
	    		 transaction.rollback();
	         }
			 ServiceService.getLogger("UserEntityRepository").error(e.getMessage());
			 ServiceService.getLogger("UserEntityRepository").error(e.getStackTrace());
	     }
		 
		 return new ArrayList<UserEntity>();
	 }
}
