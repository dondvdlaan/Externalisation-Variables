package dev.manyroads.javalogger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;

@SpringBootApplication
public class JavaLoggerApplication implements CommandLineRunner {

	// ---- Constants ----
	private static final Logger logger = LogManager.getLogger(JavaLoggerApplication.class);

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

	}

	// Print error information to console.
	public static void printStandardError(Exception e) {
		System.out.println("SQLException: " + e.getMessage());
		System.out.println("SQLState: " + e.getCause());
		//System.out.println("VendorError: " + e.getErrorCode());
	}
}
