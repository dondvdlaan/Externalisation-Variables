package dev.manyroads.javalogger.controller;

import dev.manyroads.javalogger.database.AuditDbManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping(value = "/api")
public class AuditController {

    @Autowired
    private AuditDbManager auditDbManager;

    // ---- Constants ----
    private static final Logger logger = LogManager.getLogger(AuditController.class);

    // ---- Routes ----
    // Retrieve all logs
    @GetMapping("/allLogs")
    public ResponseEntity<?> getAllLogs()  {

        System.out.println("Route: allLogs");

        // Propagating Errors Up the Call Stack from DAOLogs
        try{
            return ResponseEntity.ok(auditDbManager.getAllLogsFromDb());

        } catch (Exception e ) {
            System.out.println("Route: allLogs catch");
            logger.error(e.getMessage());

            Map<String, String> error = new HashMap<>();
            error.put("error", "An error occurred while retrieving logs: "+ e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
    // Testing
    @GetMapping("/updateUsers")
    public ResponseEntity<?> getAllUsers()  {

        System.out.println("Route: allUsers");

        // Propagating Errors Up the Call Stack from DAOLogs
        try{
            Map<String, String> error = new HashMap<>();
            error.put("error", "An error occurred while updating users: + e.getMessage()");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        } catch (Exception e) {
            logger.error(e.getMessage());

            Map<String, String> message = new HashMap<>();
            message.put("message", "Alles goed");
            return ResponseEntity.ok(message);
        }
    }
    // END Testing
}
