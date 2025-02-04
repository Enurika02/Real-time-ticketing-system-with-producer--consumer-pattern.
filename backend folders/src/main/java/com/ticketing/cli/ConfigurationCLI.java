package com.ticketing.cli;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ticketing.config.TicketingConfiguration;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.Map;
import java.util.Scanner;

public class ConfigurationCLI {

    private static final String CONFIG_FILE = "ticketing_config.json";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static final Scanner scanner = new Scanner(System.in);

    public static void promptConfiguration() {
        System.out.println("\n=== Event Ticketing System Configuration ===\n");

        // Try to load existing configuration
        TicketingConfiguration existingConfig = loadConfiguration();
        if (existingConfig != null) {
            System.out.println("Found existing configuration:");
            printConfiguration(existingConfig);
            System.out.print("\nWould you like to use this configuration? (yes/no): ");
            String response = scanner.nextLine().trim().toLowerCase();
            if (response.equals("yes") || response.equals("y")) {
                return;
            }
        }

        TicketingConfiguration config = new TicketingConfiguration();

        // Total Tickets
        while (true) {
            System.out.print("Enter total number of tickets (minimum 1): ");
            try {
                int totalTickets = Integer.parseInt(scanner.nextLine().trim());
                if (totalTickets > 0) {
                    config.setTotalTickets(totalTickets);
                    break;
                }
                System.out.println("Error: Total tickets must be greater than 0");
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid number");
            }
        }

        // Ticket Release Rate
        while (true) {
            System.out.print("Enter ticket release rate (tickets per release, minimum 1): ");
            try {
                int releaseRate = Integer.parseInt(scanner.nextLine().trim());
                if (releaseRate > 0) {
                    config.setTicketReleaseRate(releaseRate);
                    break;
                }
                System.out.println("Error: Release rate must be greater than 0");
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid number");
            }
        }

        // Customer Retrieval Rate
        while (true) {
            System.out.print("Enter customer retrieval rate (milliseconds, minimum 100): ");
            try {
                int retrievalRate = Integer.parseInt(scanner.nextLine().trim());
                if (retrievalRate >= 100) {
                    config.setCustomerRetrievalRate(retrievalRate);
                    break;
                }
                System.out.println("Error: Retrieval rate must be at least 100ms");
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid number");
            }
        }

        // Maximum Ticket Capacity
        while (true) {
            System.out.print("Enter maximum ticket capacity (minimum 10): ");
            try {
                int maxCapacity = Integer.parseInt(scanner.nextLine().trim());
                if (maxCapacity >= 10) {
                    config.setMaxTicketCapacity(maxCapacity);
                    break;
                }
                System.out.println("Error: Maximum capacity must be at least 10");
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid number");
            }
        }

        // Save configuration
        System.out.print("\nWould you like to save this configuration? (yes/no): ");
        String saveResponse = scanner.nextLine().trim().toLowerCase();
        if (saveResponse.equals("yes") || saveResponse.equals("y")) {
            saveConfiguration(config);
            saveConfigurationToYaml(config);
        }
    }

    private static void saveConfiguration(TicketingConfiguration config) {
        try (Writer writer = new FileWriter(CONFIG_FILE)) {
            gson.toJson(config, writer);
            System.out.println("Configuration saved successfully!");
        } catch (IOException e) {
            System.out.println("Error saving configuration: " + e.getMessage());
        }
    }

    private static TicketingConfiguration loadConfiguration() {
        File configFile = new File(CONFIG_FILE);
        if (!configFile.exists()) {
            return null;
        }

        try (Reader reader = new FileReader(configFile)) {
            return gson.fromJson(reader, TicketingConfiguration.class);
        } catch (IOException e) {
            System.out.println("Error loading configuration: " + e.getMessage());
            return null;
        }
    }

    private static void saveConfigurationToYaml(TicketingConfiguration config) {
        String yamlFile = "src/main/resources/application.yml"; // Path to your application.yml file
        try (FileInputStream inputStream = new FileInputStream(yamlFile)) {
            LoaderOptions loaderOptions = new LoaderOptions();
            Yaml yaml = new Yaml(loaderOptions);

            Map<String, Object> yamlData = yaml.load(inputStream);

            Map<String, Object> ticketingSection = (Map<String, Object>) yamlData.get("ticketing");
            if (ticketingSection != null) {
                ticketingSection.put("totalTickets", config.getTotalTickets());
                ticketingSection.put("ticketReleaseRate", config.getTicketReleaseRate());
                ticketingSection.put("customerRetrievalRate", config.getCustomerRetrievalRate());
                ticketingSection.put("maxTicketCapacity", config.getMaxTicketCapacity());
            } else {
                yamlData.put("ticketing", Map.of(
                        "totalTickets", config.getTotalTickets(),
                        "ticketReleaseRate", config.getTicketReleaseRate(),
                        "customerRetrievalRate", config.getCustomerRetrievalRate(),
                        "maxTicketCapacity", config.getMaxTicketCapacity()
                ));
            }

            DumperOptions options = new DumperOptions();
            options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
            Yaml yamlWriter = new Yaml(options);

            try (FileWriter writer = new FileWriter(yamlFile)) {
                yamlWriter.dump(yamlData, writer);
            }
            System.out.println("Ticketing configuration updated successfully!");
        } catch (IOException e) {
            System.out.println("Error updating configuration: " + e.getMessage());
        }
    }


    private static void printConfiguration(TicketingConfiguration config) {
        System.out.println("Total Tickets: " + config.getTotalTickets());
        System.out.println("Ticket Release Rate: " + config.getTicketReleaseRate());
        System.out.println("Customer Retrieval Rate: " + config.getCustomerRetrievalRate() + "ms");
        System.out.println("Maximum Ticket Capacity: " + config.getMaxTicketCapacity());
    }
}
