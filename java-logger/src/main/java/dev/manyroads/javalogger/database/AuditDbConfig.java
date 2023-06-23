package dev.manyroads.javalogger.database;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "audit")
public class AuditDbConfig {
    private String jdbcDriver;
    private String dbLocalServerIpAddress;
    private String dbLocalName;
    private String dbLocalConnectionUrl;
    private String dbLocalUserName;
    private String dbLocalUserPw;

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
}
