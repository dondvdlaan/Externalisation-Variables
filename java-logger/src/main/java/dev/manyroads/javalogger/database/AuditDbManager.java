package dev.manyroads.javalogger.database;

import dev.manyroads.javalogger.model.Log;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to access Audit database
 */
@Component
public class AuditDbManager {

    @Autowired
    private AuditDbConfig auditDbConfig;
    @Autowired
    private DAOLogs daoLogs;

    // ---- Constants ----
    private static final Logger logger = LogManager.getLogger(AuditDbManager.class);

    // ---- Attributes ----
    private static AuditDbManager instance;
    //private        DAOLogs daoLogs;

    // ---- Getters & Setters ----

    // ---- Constructors ----
    //private AuditDbManager() {
    //    this.daoLogs = new DAOLogs();
    //}
    // ---- Methods ----
    /**
     * Method to return sole instance
     *
     * @return instance : AuditDbManager : Sole Instance
     */
    public static synchronized AuditDbManager getInstance() {

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

        // 1. Configure Datasource
            String DB_LOCAL_CONNECTION_URL =    auditDbConfig.getDbLocalConnectionUrl() +
                                                auditDbConfig.getDbLocalServerIpAddress() +
                                                auditDbConfig.getDbLocalName();

            BasicDataSource dataSource = new BasicDataSource();

            dataSource.setUrl(DB_LOCAL_CONNECTION_URL);
            dataSource.setUsername(auditDbConfig.getDbLocalUserName());
            dataSource.setPassword(auditDbConfig.getDbLocalUserPw());

        //2. Offenen einer Verbindung
        rwDbConnection = dataSource.getConnection();

        //catch (ClassNotFoundException classNotFoundEx) {
        //    logger.error(classNotFoundEx);
        //    throw new Exception("JDBC Treiber konnte nicht geladen werden");
        //}

        return rwDbConnection;
    }
    /**
     * Method to check if Database is online
     *
     * @return isOnline : boolean : true : Db ist Online : false nicht
     */
    public boolean isDatabaseOnline() throws Exception {

        boolean isOnline = true;

        try {
            this.getRwDbConnection().close();
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
            isOnline = false;
            throw e;
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
            Connection connectionToDb = this.getRwDbConnection();

            if (this.isDatabaseOnline()) {
                //allLogsFromDb = new DAOLogs().getAllDataRecordsFromDbTbl(connectionToDb);
                allLogsFromDb = this.daoLogs.getAllDataRecordsFromDbTbl(connectionToDb);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            System.err.println(e.getMessage());
            throw e;
        }

        return allLogsFromDb;
    }
}
