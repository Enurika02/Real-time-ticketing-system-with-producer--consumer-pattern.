# Ticketing

## Backend installation instructions

### Dependencies

- Install Apache Maven to compile and run the backend: https://maven.apache.org/install.html

### Configuration
Open the file, CliConflig.java by this path, src/main/java/com/ticketing/CliConfig.java

Run the CLI configuration,

- Change the following section to suit your preferences:



  totalTickets: 100
  ticketReleaseRate: 1
  customerRetrievalRate: 2000
  maxTicketCapacity: 10


### Starting the server

- Install the dependencies with the command: `mvn clean install`

- Start the backend server with the command: `mvn spring-boot:run`

## Frontend installation instructions

### Dependencies

- You will need NodeJS runtime to start the frontend: https://nodejs.org/en

### Starting the client

- Install the React app dependencies with the command: `npm install`

- Start the frontend with the command: `npm start`

## Usage

- There's nothing to be done in the backend after it is started. If you need to change the config you will have to change the YAML file as shown above, and then re-start the backend.

- In the frontend, you can create new vendors (producers), create new customers (consumers).

- The vendors will issue tickets according to the `ticketReleaseRate` field in the `application.yml` file. Here a value of 1 means each vendor will issue a ticket every second.

* The size of the ticket pool is determined by `maxTicketCapacity` field in the `application.yml` file. These are the tickets that are available for purchase. If this limit is reached, the vendors will not issue any more tickets until this goes below capacity.

* Total tickets available to sell are determined by the `totalTickets` field in the `application.yml` file. After this amount of tickets have been issued, vendors will not be able to sell any more tickets. However, if there are tickets left in the ticket pool, customers can still purchase them.

* Customer purchasing behavior is determined by the `customerRetrievalRate` field in the `application.yml` file. This is specified in milliseconds. A value of 2000 means that each customer will purchase 1 ticket every 2000ms.
