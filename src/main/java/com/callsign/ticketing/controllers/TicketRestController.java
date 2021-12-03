package com.callsign.ticketing.controllers;

import com.callsign.ticketing.data.converters.presentationandbusiness.TicketRecordAndTicketResponseMapper;
import com.callsign.ticketing.data.transactions.presentationlayer.TicketResponse;
import com.callsign.ticketing.services.TicketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class TicketRestController {
  private final TicketService ticketService;

  public TicketRestController(TicketService ticketService) {
    this.ticketService = ticketService;
  }

  @RequestMapping(value = "/tickets", method = RequestMethod.GET)
  public ResponseEntity<List<TicketResponse>> getAllTicketsSorted(){
    List<TicketResponse> ticketResponses = ticketService.getAllTicketsSortedByPriority()
        .stream().map(TicketRecordAndTicketResponseMapper::toTicketResponse).collect(Collectors.toList());
    return new ResponseEntity<>(ticketResponses, HttpStatus.OK);
  }
}
