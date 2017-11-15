package org.exemple.demo.mon_appli_tests_unitaires;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Log4jProperties {
	final static Logger log = Logger.getLogger(Log4jProperties.class);
	
	public static void main(String[] args) {
		PropertyConfigurator.configure("log4j.properties");
		log.debug("This is a debug message");
		myMethod();
		log.info("This is an info message");
	}
	
	private static void myMethod() {
		try {
			throw new Exception("My Exception");
		} catch (Exception e) {
			log.error("This is an exception", e);
		}
	}
}
