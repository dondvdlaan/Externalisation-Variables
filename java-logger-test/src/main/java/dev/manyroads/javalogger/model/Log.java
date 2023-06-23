package dev.manyroads.javalogger.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class Log {

    // ---- Constants ----
    private static final int DEFAULT_INT        = -1;
    private static final String DEFAULT_STRING  = "<nothingToSeeHere";

    // ---- Attributes ----
    private int logID;
    private String application;
    private String logTime;
    private String levelMsg;
    private String message;

    // ---- Constructors ----
    public Log() {
        this.logID          = DEFAULT_INT;
        this.application    = DEFAULT_STRING;
        this.logTime        = DEFAULT_STRING;
        this.levelMsg       = DEFAULT_STRING;
        this.message        = DEFAULT_STRING;
    }
    // ---- Getters & Setters ----
    public int getLogID() {
        return logID;
    }

    public void setLogID(int logID) {
        this.logID = logID;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getLogTime() {
        return logTime;
    }

    public void setLogTime(String logTime) {
        this.logTime = logTime;
    }

    public String getLevelMsg() {
        return levelMsg;
    }

    public void setLevelMsg(String levelMsg) {
        this.levelMsg = levelMsg;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
