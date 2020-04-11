package data;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import entity.WeatherDataEntity;

public class WeatherDataRepository {
	public static void save(WeatherDataEntity wde) {
		 Transaction transaction = null;
	     try {
	       	Session session = HibernateUtil.getSessionFactory().openSession();
	        			
	        // start a transaction
	        transaction = session.beginTransaction();
	        session.save(wde);
	        
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
	public static List<WeatherDataEntity> getAll() {
		 Transaction transaction = null;
		 try {
			 Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			 transaction = session.beginTransaction();
			 Query q = session.createQuery("select wde from entity.WeatherDataEntity wde");
			 List<WeatherDataEntity> results = q.list();
			 
			 transaction.commit();
			 return results;
		 } catch (Exception e) {
			 if (transaction != null) {
	    		 transaction.rollback();
	         }
	         e.printStackTrace();
	     }
		 
		 return null;
	}
	
	@SuppressWarnings("unchecked")
	public static WeatherDataEntity getById(long id) {
		 Transaction transaction = null;
		 try {
			 Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			 transaction = session.beginTransaction();
			 List<WeatherDataEntity> results = session.createQuery("select wde from entity.WeatherDataEntity wde where id = :id")
			 	.setParameter("id",  id)
			 	.list();
			 
			 transaction.commit();
			 if (results.size() > 0) {
				 return results.get(0);
			 }
		 } catch (Exception e) {
			 if (transaction != null) {
	    		 transaction.rollback();
	         }
	         e.printStackTrace();
	     }
		 
		 return null;
	}
	
	public static void deleteById(long id) {
		 Transaction transaction = null;
		 try {
			 WeatherDataEntity wde = getById(id);
			 if (wde != null) {
				 Session session = HibernateUtil.getSessionFactory().getCurrentSession();
				 transaction = session.beginTransaction();
				 session.delete(wde);
				 transaction.commit();
			 } else {
				 System.out.println("Debug: WeatherDataEntity was null cannot delete");
			 }
		 } catch (Exception e) {
			 if (transaction != null) {
	    		 transaction.rollback();
	         }
	         e.printStackTrace();
	     }
	}
}
