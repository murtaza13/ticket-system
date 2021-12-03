package com.callsign.ticketing.controllers;

import com.callsign.ticketing.data.transactions.TicketRecord;
import com.callsign.ticketing.services.TicketService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class TicketRestController {
  private final TicketService ticketService;

  public TicketRestController(TicketService ticketService) {
    this.ticketService = ticketService;
  }

  @RequestMapping(value = "/tickets", method = RequestMethod.GET)
  public Set<TicketRecord> getAllTickets(){
    return ticketService.getAllTicketsSortedByPriority();
  }
}
