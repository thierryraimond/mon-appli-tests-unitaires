package org.exemple.demo.mon_appli_tests_unitaires;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * Entrainement avec la base de données embarquée HSQL
 * 
 * @url http://baptiste-wicht.developpez.com/tutoriels/java/db/hsql/
 * @author thier
 *
 */
public class ExempleHSQL {

	final static Logger log = Logger.getLogger(ExempleHSQL.class);
	
    /** Driver JDBC. */
    private static final String JDBC_DRIVER = "org.hsqldb.jdbcDriver";
 
    /** Base de données HSQLDB nommée "exemple" qui fonctionne en mode mémoire. */
    private static final String DATABASE = "jdbc:hsqldb:file:db/exemple";
 
    /** Utilisateur qui se connecte à la base de données. */
    private static final String USER = "sa";
 
    /** GetDataSet mot de passe pour se connecter à la base de données. */
    private static final String PASSWORD = "";
 
    /** Nom du fichier xml contenant le jeu de données à importer. */
    private static final String INPUT_DATA_SET_FILENAME = "dataSets/inputFlatXmlDataSet.xml";
 
    /** Nom du fichier xml qui contiendra le jeu de données exporté. */
    private static final String OUTPUT_DATA_SET_FILENAME = "dataSets/outputFlatXmlDataSet.xml";
 
    /** Nom de la table. */
    public static final String TABLE_NAME = "EXEMPLE";
 
    // Variables de test
    /** Le nombre de tuples présents dans la table. */
    private static final int ROW_COUNT = 4;
	
	public static void main(String[] args) {
		// chargement du fichier properties de log4J
		PropertyConfigurator.configure("log4j.properties");
		
        /* Chargement du driver JDBC pour HSQL */
        try {           
            log.trace( "Chargement du driver..." );
            Class.forName( JDBC_DRIVER );
            log.trace( "Driver chargé !" );
            connexion();
        } catch ( ClassNotFoundException e ) {
            log.error( "Erreur lors du chargement : le driver n'a pas été trouvé dans le classpath ! "
                    + e.getMessage() );
        }
	}
	
	/**
	 * Connexion à la base de données HSQL
	 */
	private static void connexion() {
		Connection connexion = null;
		
		try {
			log.trace("Connexion à la base de données...");
			connexion = DriverManager.getConnection(DATABASE, USER, PASSWORD);
			log.trace("Connexion réussie !");
		} catch ( SQLException e) {
			log.error("Erreur lors de la connexion : " + e.getMessage());
		} finally {
			log.trace("Fermeture de l'objet Connection.");
			if (connexion != null) {
				try {
					connexion.close();
					log.trace("connexion fermée.");
				} catch (SQLException ignore) {
					
				}
			}
		}
	}
	
	

}
