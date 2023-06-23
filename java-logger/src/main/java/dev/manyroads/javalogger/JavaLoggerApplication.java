package dev.manyroads.javalogger;

import dev.manyroads.javalogger.database.AuditDbConfig;
import io.github.cdimascio.dotenv.Dotenv;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@SpringBootApplication
public class JavaLoggerApplication implements CommandLineRunner {

	// ---- Constants ----
	private static final Logger logger = LogManager.getLogger(JavaLoggerApplication.class);
	Dotenv dotenv = Dotenv.load();

	public static void main(String[] args) {
		logger.info("Main started");
		SpringApplication.run(JavaLoggerApplication.class, args);
	}

	/**
	 * Class run is related to SpringBoot CommandlineRunner and used for debugging/testing
	 *
	 * @param args          : Spring boot default
	 * @throws Exception    : Spring boot default
	 */
	@Override
	public void run(String... args) throws SQLException {

		System.out.println("Main CommandLineRunner testing");
		//System.out.println("jdbc: " + dotenv.get("jdbcDriver"));


	}

	// Print error information to console.
	public static void printStandardError(Exception e) {
		System.out.println("SQLException: " + e.getMessage());
		System.out.println("SQLState: " + e.getCause());
		//System.out.println("VendorError: " + e.getErrorCode());
	}
}
