/**
 * 
 */
package org.exemple.demo.mon_appli_tests_unitaires;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author thier
 *
 */
public class CalculerTest {

	@Test
	public void testAdditionner() {
		Calculer calcul = new Calculer();
		int a = 1;
		int b = 2;
		int res = calcul.additionner(a, b);
		System.out.println("@Test additionner(): " + a + " + " + b + " = " + res);
		assertEquals(res, 3);
	}

}
