package org.exemple.demo.mon_appli_tests_unitaires;

import static org.junit.Assert.*;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.Test;
import static com.googlecode.catchexception.CatchException.*;

public class Log4jPropertiesTest {

	final static Logger log = Logger.getLogger(Log4jProperties.class);
	
	/**
	 * voir https://github.com/Codearte/catch-exception
	 */
	@Test
	public void testMyMethod() {
		PropertyConfigurator.configure("log4j.properties");
		Log4jProperties log4jProp = new Log4jProperties();

		// when : nous accédons à log4jProp.myMethod();
		// then : nous nous attendons à une Exception
		verifyException(log4jProp, Exception.class).myMethod();
		log.trace("@TEST myMethod() : Une exception est bien envoyée");
	}

}
