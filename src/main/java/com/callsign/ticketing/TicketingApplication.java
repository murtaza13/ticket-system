package com.callsign.ticketing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
public class TicketingApplication {

  public static void main(String[] args) {
    SpringApplication.run(TicketingApplication.class, args);
  }

}
