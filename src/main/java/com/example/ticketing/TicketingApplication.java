package com.example.ticketing;

import com.example.ticketing.cli.CLIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TicketingApplication implements CommandLineRunner {

	@Autowired
	private CLIService cliService;

	public static void main(String[] args) {
		SpringApplication.run(TicketingApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// Start the CLI menu on application startup
		cliService.showMenu();
	}
}
