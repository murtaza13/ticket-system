#How to Run

## Pre-Requisites
1. Java 8+ should be installed on the system
2. A valid MySQL server should be available. Use the following properties in config/application.properties file to
   configure the application.
   `spring.datasource.url`
   `spring.datasource.username`
   `spring.datasource.password`
   If no MySQL instance is available, please use provided docker configurations to spin up a new MySQL container. 
   To start MySQL container, clone the project, goto docker-mysql directory and execute `docker-compose up`

### 1. Import Project in your favourite IDE and run
#### Steps:
    1. Clone the project from github repository `https://github.com/murtaza13/ticket-system`
    2. Import the project in the IDE of your choosing (eg: Intellij/Eclipse)
    3. Sync the project dependencies
    4. Run using the default run configurations
    
### 2. Using pre-built jar file of the application
#### Steps:
    1. Unjar the Pre-Built.rar (attached with the email) file into any directory
    2. Open command line and execute `java -jar ticketing-0.0.1-SNAPSHOT.jar`
    
### 3. Import project and run docker-container
#### Steps:
     1. Clone the project from github repository `https://github.com/murtaza13/ticket-system`
     2. Move to `docker-app` and open the Dockerfile
     3. Within the Dockerfile insert your github username and password in the git clone command on line# 11.
     3. Run `docker-compose up` in command line.
     3. Given the docker deamon is up and running the application should start.
     
### 4. Import project and run using gradle wrapper
#### Steps: 
     1. Clone the project from github repository `https://github.com/murtaza13/ticket-system`
     2. In root directory open command line and execute the following statements:
        `gradlew` -- initializes the gradle wrapper
        `gradlew build` -- builds the projects
        `gradlew bootRun` -- launches the application
     
 