package dev.manyroads.javalogger.database;

import dev.manyroads.javalogger.JavaLoggerApplication;
import dev.manyroads.javalogger.model.Log;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLNonTransientConnectionException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Class to access Audit database
 */
@Component
@ConfigurationProperties(prefix = "audit")
public class AuditDbManager {

    // ---- Constants ----

    @Autowired
    private AuditDbConfig auditDbConfig;
    private static final Logger logger = LogManager.getLogger(AuditDbManager.class);
    private  String jdbcDriver;
    //private static final String JDBC_DRIVER                 = "org.mariadb.jdbc.Driver";
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
    @Autowired
    private        DAOLogs daoLogs;

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
    /*
    private AuditDbManager() {
        this.daoLogs = new DAOLogs();
    }
     */
    // ---- Methods ----
    /**
     * Method to return sole instance
     *
     * @return instance : AuditDbManager : Sole Instance
     */
    public synchronized AuditDbManager getInstance() {
    //public static synchronized AuditDbManager getInstance() {

        if (instance == null) instance = new AuditDbManager();

        return instance;
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
            System.out.println("getRwDbConnection()try: " + new SimpleDateFormat(
                    "dd MMM yyyy HH:mm:ss:SSS Z").format(new Date()));

            Class.forName(this.getJdbcDriver());

            System.out.println("this.dbLocalUserPw: " + this.dbLocalUserPw);

            //2. Offenen einer Verbindung
            rwDbConnection = DriverManager.getConnection(DB_LOCAL_CONNECTION_URL, DB_LOCAL_USER_NAME, DB_LOCAL_USER_PW);
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

                System.out.println("getAllLogsFromDb(): " + new SimpleDateFormat(
                        "dd MMM yyyy HH:mm:ss:SSS Z").format(new Date()));

                allLogsFromDb = daoLogs.getAllDataRecordsFromDbTbl(this.getRwDbConnection());
                //allLogsFromDb = this.daoLogs.getAllDataRecordsFromDbTbl(this.getRwDbConnection());
           // }
        }
            finally{}

      /*  } catch (Exception e) {
            logger.error(e.getMessage());
            System.err.println(e.getMessage());
        }
*/
        return allLogsFromDb;
    }
}
