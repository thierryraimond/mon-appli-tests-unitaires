package org.exemple.demo.mon_appli_tests_unitaires;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * Calculer des opérations
 * 
 * @author thier
 *
 */
public class Calculer {
	
	final static Logger log = Logger.getLogger(Log4jProperties.class);

	/**
	 * Addition de deux valeurs entières citées en paramètre
	 * 
	 * @param a int première valeur
	 * @param b int seconde valeur
	 * @return int addition de a+b
	 */
	public int additionner(int a, int b) {
		PropertyConfigurator.configure("log4j.properties");
		log.trace("Addition : " + a + " + " + b);
		return a + b;
	}
	
	public int diviser(int a, int b) {
		PropertyConfigurator.configure("log4j.properties");
		if (b == 0) {
			throw new ArithmeticException();
		}
		log.trace("Division : " + a + " / " + b);
		return a / b;
	}
}
