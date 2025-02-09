package com.ticketing;

import com.ticketing.cli.ConfigurationCLI;
import com.ticketing.config.TicketingConfiguration;
import com.ticketing.service.TicketingService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class EventTicketingApplication {

  public static void main(String[] args) {
    SpringApplication.run(EventTicketingApplication.class, args);
  }

  // @Bean
  // public CommandLineRunner commandLineRunner(
  // ConfigurationCLI configurationCLI,
  // TicketingService ticketingService) {
  // return args -> {
  // // Get configuration from CLI
  // TicketingConfiguration config = configurationCLI.promptConfiguration();
  //
  // // Show the menu and handle user input
  // configurationCLI.showMenu();
  //
  // // Shutdown the system
  // ticketingService.shutdown();
  // };
  // }
}
