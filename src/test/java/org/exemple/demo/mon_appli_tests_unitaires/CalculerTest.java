/**
 * 
 */
package org.exemple.demo.mon_appli_tests_unitaires;

import static org.junit.Assert.*;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.Test;
import static com.googlecode.catchexception.CatchException.*;
/**
 * @author thier
 *
 */
public class CalculerTest {

	final static Logger log = Logger.getLogger(Log4jProperties.class);
	
	@Test
	public void testAdditionner() {
		PropertyConfigurator.configure("log4j.properties");
		Calculer calcul = new Calculer();
		int a = 1;
		int b = 2;
		int res = calcul.additionner(a, b);
		log.trace("@Test additionner(): " + a + " + " + b + " = " + res);
		assertEquals(res, 3);
	}
	
	@Test
	public void testDiviser() {
		PropertyConfigurator.configure("log4j.properties");
		Calculer calcul = new Calculer();
		int a = 4;
		int b = 2;
		int res = calcul.diviser(a, b);
		log.trace("@Test diviser() : " + a + " / " + b);
		assertEquals(res, 2);
	}
	
	@Test
	public void testDiviserParZero() {
		PropertyConfigurator.configure("log4j.properties");
		Calculer calcul = new Calculer();
		int a = 4;
		int b = 0;
		verifyException(calcul, ArithmeticException.class).diviser(a,b);
		log.trace("@Test diviser() par zéro : " + a + " / " + b + " - lance bien une exception");

	}

}
