package dev.manyroads.javalogger.database;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "audit")
public class AuditDbConfig {

    // ---- Attributes ---
    // Audit Database
    private String jdbcDriver;
    private String dbLocalServerIpAddress;
    private String dbLocalName;
    private String dbLocalConnectionUrl;
    private String dbLocalUserName;
    private String dbLocalUserPw;
    // Audit Database Table
    private String colNameLogID;
    private String colNameApplication;
    private String colNameLogTime;
    private String colNameLevelMsg;
    private String colNameMessage;
    private String tableName;

    // ---- Getters & Setters ----
    public String getJdbcDriver() {
        return jdbcDriver;
    }

    public void setJdbcDriver(String jdbcDriver) {
        this.jdbcDriver = jdbcDriver;
    }

    public String getDbLocalServerIpAddress() {
        return dbLocalServerIpAddress;
    }

    public void setDbLocalServerIpAddress(String dbLocalServerIpAddress) {
        this.dbLocalServerIpAddress = dbLocalServerIpAddress;
    }

    public String getDbLocalName() {
        return dbLocalName;
    }

    public void setDbLocalName(String dbLocalName) {
        this.dbLocalName = dbLocalName;
    }

    public String getDbLocalConnectionUrl() {
        return dbLocalConnectionUrl;
    }

    public void setDbLocalConnectionUrl(String dbLocalConnectionUrl) {
        this.dbLocalConnectionUrl = dbLocalConnectionUrl;
    }

    public String getDbLocalUserName() {
        return dbLocalUserName;
    }

    public void setDbLocalUserName(String dbLocalUserName) {
        this.dbLocalUserName = dbLocalUserName;
    }

    public String getDbLocalUserPw() {
        return dbLocalUserPw;
    }

    public void setDbLocalUserPw(String dbLocalUserPw) {
        this.dbLocalUserPw = dbLocalUserPw;
    }

    public String getColNameLogID() {
        return colNameLogID;
    }

    public void setColNameLogID(String colNameLogID) {
        this.colNameLogID = colNameLogID;
    }

    public String getColNameApplication() {
        return colNameApplication;
    }

    public void setColNameApplication(String colNameApplication) {
        this.colNameApplication = colNameApplication;
    }

    public String getColNameLogTime() {
        return colNameLogTime;
    }

    public void setColNameLogTime(String colNameLogTime) {
        this.colNameLogTime = colNameLogTime;
    }

    public String getColNameLevelMsg() {
        return colNameLevelMsg;
    }

    public void setColNameLevelMsg(String colNameLevelMsg) {
        this.colNameLevelMsg = colNameLevelMsg;
    }

    public String getColNameMessage() {
        return colNameMessage;
    }

    public void setColNameMessage(String colNameMessage) {
        this.colNameMessage = colNameMessage;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
