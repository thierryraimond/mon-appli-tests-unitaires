package org.exemple.demo.mon_appli_tests_unitaires;

/**
 * Calculer des opérations
 * 
 * @author thier
 *
 */
public class Calculer {

	/**
	 * Addition de deux valeurs entières cités en paramètre
	 * 
	 * @param a int première valeur
	 * @param b int seconde valeur
	 * @return int addition de a+b
	 */
	public int additionner(int a, int b) {
		System.out.println("Addition : " + a + " + " + b);
		return a + b;
	}
}
