package dev.manyroads.javalogger;

import dev.manyroads.javalogger.database.AuditDbConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@SpringBootApplication
@EnableConfigurationProperties
public class JavaLoggerApplication implements CommandLineRunner {

	// ---- Constants ----
	private static final Logger logger = LogManager.getLogger(JavaLoggerApplication.class);
	@Autowired
	private AuditDbConfig auditDbConfig;

	public static void main(String[] args) {
		logger.info("Main started");

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
		//System.out.println("PW: " + auditDbConfig.get);


	}

	// Print error information to console.
	public static void printStandardError(Exception e) {
		System.out.println("SQLException: " + e.getMessage());
		System.out.println("SQLState: " + e.getCause());
		//System.out.println("VendorError: " + e.getErrorCode());
	}
}
