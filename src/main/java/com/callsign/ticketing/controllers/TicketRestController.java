package com.callsign.ticketing.controllers;

import com.callsign.ticketing.data.converters.TicketRecordAndTicketResponseMapper;
import com.callsign.ticketing.data.responses.TicketResponse;
import com.callsign.ticketing.services.TicketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Class responsible for providing end-users with end-points to access the Ticket resource
 */
@RestController
public class TicketRestController {
  private static final Logger LOGGER = LoggerFactory.getLogger(TicketRestController.class);
  private final TicketService ticketService;

  public TicketRestController(TicketService ticketService) {
    this.ticketService = ticketService;
  }

  /**
   * Maps to {BASE_URI}/tickets. Fetches and returns the list of all available tickets sorted in descending order of priority.
   *
   * @return {@link List<TicketResponse>} list of all sorted tickets
   */
  @RequestMapping(value = "/tickets", method = RequestMethod.GET)
  public ResponseEntity<List<TicketResponse>> getAllTicketsSorted() {
    List<TicketResponse> ticketResponses = ticketService.getAllTicketsSortedByPriority()
        .stream().map(TicketRecordAndTicketResponseMapper::toTicketResponse).collect(Collectors.toList());
    LOGGER.debug("Successfully fetched {} ticket records.", ticketResponses.size());
    return new ResponseEntity<>(ticketResponses, HttpStatus.OK);
  }
}
