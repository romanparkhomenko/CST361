package com.nilfactor.activity3.utility;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import data.HibernateUtil;
import data.UserEntityRepository;
import data.WeatherDataRepository;

public class ServiceService {
	@Inject static UserEntityRepository userEntityRepository;
	@Inject static WeatherDataRepository weatherDataRepository;
	@Inject static HibernateUtil hibernateUtil;
	
	public static Logger getLogger(String className) {
		return Logger.getLogger(className);
	}
	
	public static UserEntityRepository getUserEntityRepository() {
		if (userEntityRepository == null) {
			userEntityRepository = new UserEntityRepository();
		}
		return userEntityRepository;
	}
	
	public static void setUserEntityRepository(UserEntityRepository userEntityRepository) {
		ServiceService.userEntityRepository = userEntityRepository;
	}
	
	public static WeatherDataRepository getWeatherDataRepository() {
		if (weatherDataRepository == null) {
			weatherDataRepository = new WeatherDataRepository();
		}
		return weatherDataRepository;
	}
	
	public static void setWeatherDataRepository(WeatherDataRepository weatherDataRepository) {
		ServiceService.weatherDataRepository = weatherDataRepository;
	}

	public static HibernateUtil getHibernateUtil() {
		if (hibernateUtil == null) {
			hibernateUtil = new HibernateUtil();
		}
		return hibernateUtil;
	}

	public static void setHibernateUtil(HibernateUtil hibernateUtil) {
		ServiceService.hibernateUtil = hibernateUtil;
	}
	
	
}
