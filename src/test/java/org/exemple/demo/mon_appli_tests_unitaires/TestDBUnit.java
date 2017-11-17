package org.exemple.demo.mon_appli_tests_unitaires;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.RootLogger;
import org.dbunit.Assertion;
import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Test;
 
/**
 * Classe de test unitaire.
 * @url http://www.scub-foundation.org/accueil/tutoriaux/tutorial-dbunit/
 * @author Scub-Foundation
 */
public class TestDBUnit extends DBTestCase {
 
    /** Logger. */
    private static final Logger LOGGER = RootLogger.getLogger(DBTestCase.class);
 
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
 
    /**
     * Constructeur.
     * @param name
     * @throws Exception
     */
    public TestDBUnit(String name) {
        super(name);
        System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, JDBC_DRIVER);
        System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, DATABASE);
        System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, USER);
        System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, PASSWORD);
    }
 
    /**
     * Override method to set custom properties/features {@inheritDoc}.
     */
    @Override
    protected void setUpDatabaseConfig(DatabaseConfig config) {
        super.setUpDatabaseConfig(config);
        config.setProperty(DatabaseConfig.PROPERTY_BATCH_SIZE, new Integer(97));
        config.setFeature(DatabaseConfig.FEATURE_BATCHED_STATEMENTS, true);
    }
 
    /**
     * Charge le jeu de données à partir d'un fichier XML d'import.
     */
    @Override
    protected IDataSet getDataSet() throws Exception {
//        FlatXmlDataSet loadedDataSet = new FlatXmlDataSet(new FileInputStream(
//                INPUT_DATA_SET_FILENAME));
//        return loadedDataSet;
    	return new FlatXmlDataSetBuilder().build(new FileInputStream(INPUT_DATA_SET_FILENAME));
    }
 
    /**
     * Méthode étant appelée au début de chaque test.
     */
    @Override
    protected DatabaseOperation getSetUpOperation() throws Exception {
        DatabaseOperation.CLEAN_INSERT.execute(getConnection(), getDataSet());
        return DatabaseOperation.NONE;
    }
 
    /**
     * Méthode étant appelée après à la fin de chaque test.
     */
    @Override
    protected DatabaseOperation getTearDownOperation() throws Exception {
        return DatabaseOperation.NONE;
    }
 
    /**
     * Retourne le jeu de données chargé dans la base de données.
     */
    protected QueryDataSet getDatabaseDataSet() throws Exception {
        QueryDataSet loadedDataSet = new QueryDataSet(getConnection());
        loadedDataSet.addTable(TABLE_NAME);
 
        return loadedDataSet;
    }
 
    /**
     * Méthode qui vérifie que le jeu de données a correctement été chargé.
     * @throws Exception
     */
    @Test
    public void testCheckDataLoaded() throws Exception {
        // On récupère notre jeu de données
        IDataSet databaseDataSet = getDatabaseDataSet();
        // On vérifie que le jeu de données n'est pas nul.
        assertNotNull(databaseDataSet);
        // On compte combien d'enregistrement contient la table
        int rowCount = databaseDataSet.getTable(TABLE_NAME).getRowCount();
        // On le compare avec le nombre d'enregistrement connu
        assertEquals("Le nombre d'enregistrements dans la table \""
                + TABLE_NAME + "\" ne correspond pas", ROW_COUNT, rowCount);
    }
 
    /**
     * Montre comment des données peuvent être extraites pour être comparées
     * avec la représentation XML.
     * @throws Exception
     */
    @Test
    public void testCompareTable() throws Exception {
 
        // Récupère la base de données après avoir exécuté le code
        IDataSet databaseDataSet = getDatabaseDataSet();
        ITable actualTable = databaseDataSet.getTable(TABLE_NAME);
 
        // Charge les données attendues à partir du jeu de données du fichier
        // XML
        IDataSet expectedDataSet = new FlatXmlDataSet(new File(
                INPUT_DATA_SET_FILENAME));
        ITable expectedTable = expectedDataSet.getTable(TABLE_NAME);
 
        // Compare les deux tables pour vérifier qu'elles sont bien identiques
        Assertion.assertEquals(expectedTable, actualTable);
    }
 
    /**
     * Test de comparaison des données avec une requête genérée par IDataSet.
     * @throws Exception
     */
    @Test
    public void testCompareQuery() throws Exception {
        IDataSet loadedDataSet = getDataSet();
        // Préparation d'un jeu de données pour effectuer une requête
        QueryDataSet queryDataSet = new QueryDataSet(getConnection());
        queryDataSet.addTable(TABLE_NAME);
 
        // On compare que le jeu de données original chargé est identique à la
        // requête qu'on vient d'effectuer
        Assertion.assertEquals(loadedDataSet, queryDataSet);
    }
 
    /**
     * Teste le mécanisme d'exportation de DBUnit.
     * @throws Exception
     */
    @Test
    public void testExportData() throws Exception {
        // On récupère le jeu de données du fichier XML
        IDataSet dataSet = getDatabaseDataSet();
 
        // Fichier XML du jeu de données d'import
        File inputFile = new File(INPUT_DATA_SET_FILENAME);
        // On vérifie que le fichier existe
        assertNotNull(inputFile);
        // Fichier XML du jeu de données d'export
        File outputFile = new File(OUTPUT_DATA_SET_FILENAME);
        FlatXmlDataSet.write(dataSet, new FileOutputStream(outputFile));
 
        // On compare les deux fichiers XML pour vérifier qu'ils sont identiques
        String inputDataSetString = FileUtils.readFileToString(inputFile,
                "UTF8").replace("  ", "\t").trim();
        String outputDataSetString = FileUtils.readFileToString(outputFile,
                "UTF8").replace("  ", "\t").trim();
        assertEquals(inputDataSetString, outputDataSetString);
 
    }
}