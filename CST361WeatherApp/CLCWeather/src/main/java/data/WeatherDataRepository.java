package data;

import java.util.List;

import javax.interceptor.Interceptors;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.nilfactor.activity3.utility.LogInterceptor;
import com.nilfactor.activity3.utility.ServiceService;

import entity.WeatherDataEntity;

@Interceptors(LogInterceptor.class)
public class WeatherDataRepository {
	private HibernateUtil hibernateUtil = ServiceService.getHibernateUtil(); 
			
	@Interceptors(LogInterceptor.class)
	public void save(WeatherDataEntity wde) {
		 Transaction transaction = null;
	     try {
	       	Session session = hibernateUtil.getSessionFactory().openSession();
	        			
	        // start a transaction
	        transaction = session.beginTransaction();
	        session.save(wde);
	        
	        // commit transaction
	       transaction.commit();
	     } catch (Exception e) {
	    	 if (transaction != null) {
	    		 transaction.rollback();
	         }
	    	 ServiceService.getLogger("WeatherDataRepository").error(e.getMessage());
	    	 ServiceService.getLogger("WeatherDataRepository").error(e.getStackTrace());
	     }
	 }
	
	@SuppressWarnings("unchecked")
	@Interceptors(LogInterceptor.class)
	public List<WeatherDataEntity> getAll() {
		 Transaction transaction = null;
		 try {
			 Session session = hibernateUtil.getSessionFactory().getCurrentSession();
			 transaction = session.beginTransaction();
			 Query q = session.createQuery("select wde from entity.WeatherDataEntity wde");
			 List<WeatherDataEntity> results = q.list();
			 
			 transaction.commit();
			 return results;
		 } catch (Exception e) {
			 if (transaction != null) {
	    		 transaction.rollback();
	         }
			 ServiceService.getLogger("WeatherDataRepository").error(e.getMessage());
			 ServiceService.getLogger("WeatherDataRepository").error(e.getStackTrace());
	     }
		 
		 return null;
	}
	
	@SuppressWarnings("unchecked")
	@Interceptors(LogInterceptor.class)
	public WeatherDataEntity getById(long id) {
		 Transaction transaction = null;
		 try {
			 Session session = hibernateUtil.getSessionFactory().getCurrentSession();
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
			 ServiceService.getLogger("WeatherDataRepository").error(e.getMessage());
			 ServiceService.getLogger("WeatherDataRepository").error(e.getStackTrace());
	     }
		 
		 return null;
	}
	
	@Interceptors(LogInterceptor.class)
	public void deleteById(long id) {
		 Transaction transaction = null;
		 try {
			 WeatherDataEntity wde = this.getById(id);
			 if (wde != null) {
				 Session session = hibernateUtil.getSessionFactory().getCurrentSession();
				 transaction = session.beginTransaction();
				 session.delete(wde);
				 transaction.commit();
			 } else {
				 ServiceService.getLogger("WeatherDataRepository").debug("Debug: WeatherDataEntity was null cannot delete");
			 }
		 } catch (Exception e) {
			 if (transaction != null) {
	    		 transaction.rollback();
	         }
			 ServiceService.getLogger("WeatherDataRepository").error(e.getMessage());
			 ServiceService.getLogger("WeatherDataRepository").error(e.getStackTrace());
	     }
	}
}
