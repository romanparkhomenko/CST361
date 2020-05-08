package data;

import javax.interceptor.Interceptors;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import com.nilfactor.activity3.utility.LogInterceptor;
import com.nilfactor.activity3.utility.ServiceService;

@Interceptors(LogInterceptor.class) 
public class HibernateUtil {
	private static final SessionFactory sessionFactory = buildSessionFactory();

	@Interceptors(LogInterceptor.class)
    private static SessionFactory buildSessionFactory() {
		try
		{
			if (sessionFactory == null)
			{
				try {
					Configuration configuration = new Configuration().configure();
					configuration.addResource("UserEntity.hbm.xml");
					configuration.addResource("WeatherDataEntity.hbm.xml");
					StandardServiceRegistryBuilder serviceRegistryBuilder = new StandardServiceRegistryBuilder();
					serviceRegistryBuilder.applySettings(configuration.getProperties());
					ServiceRegistry serviceRegistry = serviceRegistryBuilder.build();
					return configuration.buildSessionFactory(serviceRegistry);
				} catch (Throwable ex){
					return null;
				}
			}
			return sessionFactory;
		} catch (Throwable ex)
		{
			ServiceService.getLogger("HibernateUtil").error(ex.getMessage());
			ServiceService.getLogger("HibernateUtil").error(ex.getStackTrace());
			throw new ExceptionInInitializerError(ex);
		}
    }

	@Interceptors(LogInterceptor.class)
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    
	@Interceptors(LogInterceptor.class)
    public void shutdown() {
    	// Close caches and connection pools
    	getSessionFactory().close();
    }
}