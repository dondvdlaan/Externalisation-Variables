package dev.manyroads.javalogger.database;

import dev.manyroads.javalogger.model.Log;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Class serves as DAO (Data Access Object) for the logs in database support_audit
 */
@Service
public class DAOLogs {

    @Autowired
    private AuditDbConfig auditDbConfig;

    // ---- Constants ----
    private static final Logger logger = LogManager.getLogger(DAOLogs.class);

    // ---- Methods ----
    // Read
    /**
     * Method to read all logs as a List
     *
     * @param dbRwConnection : {@link Connection} : Db-Connection with Read / Write permission
     *
     * @return allDataRecordsFromDbTbl : {@link List} Objects extended from {@link Log} : List of all records
     */
    public List<Log> getAllDataRecordsFromDbTbl(Connection dbRwConnection) throws Exception {

             //Decl. and Init
        List<Log> allLogsFromDbTable = new ArrayList<>();

        Statement dbStatementToExecute = null;

       //try {
            //1. Rw Db Connection ist bereits vom DbManger geoeffenent und Integriert

            //2. Geneieren des Statenements
            dbStatementToExecute = dbRwConnection.createStatement();

            //3. Query generieren und absetzen und Ergebnismenge merken
            String strSqlStmtGetAll = "SELECT * FROM " + auditDbConfig.getTableName();

            ResultSet resultSetFromExecutedQuery = dbStatementToExecute.executeQuery(strSqlStmtGetAll);

            //4. ResultSet == Ergebnismenge durchlaufen bis kein Datensaezte mehr da sind
            while (resultSetFromExecutedQuery.next()) {

                //5. Aus der Ergebenismenge einen User beschafften
                Log logFromDbTable = this.getModelFromResultSet(resultSetFromExecutedQuery);

                //6. Modelobjekt zur passenden Liste addiern
                allLogsFromDbTable.add(logFromDbTable);
            }
      // }
        // Propagating Errors Up the Call Stack at AuditController
       /*   catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
        }
         finally {

        if (dbStatementToExecute != null) {
                //5. Schliessen der des Statements
                try {
                    dbStatementToExecute.close();
                } catch (SQLException sqlEx) {
                    logger.error(sqlEx);
                    sqlEx.printStackTrace();
                }
            }
*/
            if (dbRwConnection != null) {
                //6. Schliessen der Verbindung
                try {
                    dbRwConnection.close();
                } catch (SQLException sqlEx) {
                    logger.error(sqlEx);
                    sqlEx.printStackTrace();
                }
            }
        //}
        return allLogsFromDbTable;
    }
    // ---- Submethods ----
    /**
     * Nimmt die Ergebnismenge und formt ein konkretes Model daraus
     *
     * @param currentResultSet : {@link ResultSet} : Ergebnismenge der aktuellen Abfrage
     *
     * @return log : {@link Log} : Model abgeleitet von der Basisklasse
     *
     * @throws Exception
     */
    private Log getModelFromResultSet(ResultSet currentResultSet) throws Exception {
        //Index auslesen
//        final int iColumnIndexId          = currentResultSet.findColumn(COL_NAME_ID);
//        final int iColumnIndexName        = currentResultSet.findColumn(COL_NAME_NAME);
//        final int iColumnIndexDescription = currentResultSet.findColumn(COL_NAME_DESCRIPTION);
//        final int iColumnIndexTaxPrice    = currentResultSet.findColumn(COL_NAME_TAX_PRICE);
//        final int iColumnIndexIsUploaded  = currentResultSet.findColumn(COL_NAME_IS_UPLOADED);

        //6. Durch Auswahl des Datentyps und angabe des Spaltenindizes auselsen der Daten

        int logID           = currentResultSet.getInt(auditDbConfig.getColNameLogID());
        String application  = currentResultSet.getString(auditDbConfig.getColNameApplication());
        String logTime      = currentResultSet.getString(auditDbConfig.getColNameLogTime());
        String levelMsg     = currentResultSet.getString(auditDbConfig.getColNameLevelMsg());
        String message      = currentResultSet.getString(auditDbConfig.getColNameMessage());

        //7. Neues Modelobjekt generieren
        Log logFromDb = new Log();

        logFromDb.setLogID(logID);
        logFromDb.setApplication(application);
        logFromDb.setLogTime(logTime);
        logFromDb.setLevelMsg(levelMsg);
        logFromDb.setMessage(message);

        return logFromDb;
    }
}
