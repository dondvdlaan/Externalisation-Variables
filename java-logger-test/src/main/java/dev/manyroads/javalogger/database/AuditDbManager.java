package dev.manyroads.javalogger.database;

import dev.manyroads.javalogger.JavaLoggerApplication;
import dev.manyroads.javalogger.model.Log;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Class to access Audit database
 */
@SpringBootApplication
//@ConfigurationProperties(prefix = "audit")
public class AuditDbManager implements CommandLineRunner {

    // ---- Constants ----

    @Autowired
    private AuditDbConfig auditDbConfig;
    private static final Logger logger = LogManager.getLogger(AuditDbManager.class);
    private  String jdbcDriver;
    private static final String JDBC_DRIVER                 = "org.mariadb.jdbc.Driver";
    private static final String DB_LOCAL_SERVER_IP_ADDRESS  = "localhost";
    private static final String DB_LOCAL_NAME               = "/support_audit";
    private static final String DB_LOCAL_CONNECTION_URL     =   "jdbc:mariadb://" +
                                                                DB_LOCAL_SERVER_IP_ADDRESS +
                                                                DB_LOCAL_NAME;
    private static final String DB_LOCAL_USER_NAME          = "testUser";
    private static final String DB_LOCAL_USER_PW            = "testPW";
    private String dbLocalUserPw;

    // ---- Attributes ----
    private AuditDbManager instance;
    //private static AuditDbManager instance;
    //@Autowired
    //private        DAOLogs daoLogs;

    // ---- Getters & Setters ----

    public String getJdbcDriver() {
        return jdbcDriver;
    }

    public void setJdbcDriver(String jdbcDriver) {
        this.jdbcDriver = jdbcDriver;
    }

    public String getDbLocalUserPw() {
        return dbLocalUserPw;
    }

    public void setDbLocalUserPw(String dbLocalUserPw) {
        this.dbLocalUserPw = dbLocalUserPw;
    }


    // ---- Constructors ----
    //private AuditDbManager() {
    //    this.daoLogs = new DAOLogs();
    //}
    // ---- Methods ----
    public static void main(String[] args) {
        logger.info("AuditDbManager started");

        // NStartuo banner turned off
        //SpringApplication application = new SpringApplication(JavaLoggerApplication.class);
        //application.setBannerMode(Banner.Mode.OFF);
        //application.run(args);
/*
		new SpringApplicationBuilder()
				.sources(JavaLoggerApplication.class)
				.bannerMode(Banner.Mode.OFF)
				.run(args);

 */
        SpringApplication.run(AuditDbManager.class, args);
    }

    /**
     * Class run is related to SpringBoot CommandlineRunner and used for debugging/testing
     *
     * @param args          : Spring boot default
     * @throws Exception    : Spring boot default
     */
    @Override
    public void run(String... args) throws Exception {

        System.out.println("AuditDbManager CommandLineRunner testing");

        String temp = auditDbConfig.getDbLocalUserName();
        System.out.println("this.dbLocalUserPw: " + temp);

        BasicDataSource dataSource = new BasicDataSource();

        dataSource.setUrl(DB_LOCAL_CONNECTION_URL);
        dataSource.setUsername(auditDbConfig.getDbLocalUserName());
        dataSource.setPassword(DB_LOCAL_USER_PW);

        Connection rwDbConnection = dataSource.getConnection();
        //Class.forName(JDBC_DRIVER);

        //Connection rwDbConnection =
        //        DriverManager.getConnection(DB_LOCAL_CONNECTION_URL, auditDbConfig.getDbLocalUserName(), DB_LOCAL_USER_PW);
        Statement dbStatementToExecute = rwDbConnection.createStatement();

        //3. Query generieren und absetzen und Ergebnismenge merken
        String strSqlStmtGetAll = "SELECT * FROM " + "logs";

        ResultSet resultSetFromExecutedQuery = dbStatementToExecute.executeQuery(strSqlStmtGetAll);
        System.out.println("logs: " + resultSetFromExecutedQuery);
        //System.out.println("logs: " + new DAOLogs().getAllDataRecordsFromDbTbl(rwDbConnection));


        }
    /**
     * Method generates Database connection with read and write permissions. In case of failure, connection
     * is st to null.
     *
     * @return rwDbConnection : {@link Connection} : Database connection w rw - permissions
     */
    private Connection getRwDbConnection() throws Exception {

        Connection rwDbConnection = null;

        try {
            //1: Registeren des JDBC driver
            //Class.forName(auditDbConfig.getJdbcDriver());

            Class.forName(JDBC_DRIVER);

            System.out.println("this.dbLocalUserPw: " + this.dbLocalUserPw);

            //2. Offenen einer Verbindung
            rwDbConnection = DriverManager.getConnection(DB_LOCAL_CONNECTION_URL, DB_LOCAL_USER_NAME, getDbLocalUserPw());
            //rwDbConnection = DriverManager.getConnection(DB_LOCAL_CONNECTION_URL, DB_LOCAL_USER_NAME, DB_LOCAL_USER_PW);

        } catch (SQLNonTransientConnectionException sqlNoConnectionEx) {
            logger.error(sqlNoConnectionEx);
            throw new Exception("Keine Datenbankverbindung");
        } catch (ClassNotFoundException classNotFoundEx) {
            logger.error(classNotFoundEx);
            throw new Exception("JDBC Treiber konnte nicht geladen werden");
        }

        return rwDbConnection;
    }
    /**
     * Method to check if Database is online
     *
     * @return isOnline : boolean : true : Db ist Online : false nicht
     */
    public boolean isDatabaseOnline() {

        boolean isOnline = true;

        try {
            this.getRwDbConnection().close();
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
            isOnline = false;
        }
        return isOnline;
    }
    // ---- CRUD -Opeations Log
    /**
     * Liest alle Daten aus der Tabelle aus
     *
     * @return allUsersFromDbTable : {@link List} - {@link Log}: Alle Logs aus Db-Tabelle
     */
    public List<Log> getAllLogsFromDb() throws Exception {

        List<Log> allLogsFromDb = new ArrayList<>();

        //Neue Verbindung erstellen
        // Propagating Errors Up the Call Stack at AuditController
        try {
           // if (this.isDatabaseOnline()) {

                allLogsFromDb = new DAOLogs().getAllDataRecordsFromDbTbl(this.getRwDbConnection());
                //allLogsFromDb = this.daoLogs.getAllDataRecordsFromDbTbl(this.getRwDbConnection());
           // }

       } catch (Exception e) {
            logger.error(e.getMessage());
            System.err.println(e.getMessage());
        }
        return allLogsFromDb;
    }
}
