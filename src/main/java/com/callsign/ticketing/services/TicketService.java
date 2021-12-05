package com.callsign.ticketing.services;

import com.callsign.ticketing.data.enums.TicketPriority;
import com.callsign.ticketing.data.transactions.TicketRecord;

import java.util.List;

public interface TicketService {
  /**
   * Creates a Ticket record for the given parameters.
   *
   * @param reason         The reason for ticket creation.
   * @param ticketPriority {@link TicketPriority} priority of the ticket
   * @param deliveryId     id of the delivery for which the ticket is to be created
   * @return the created {@link TicketRecord}
   * @throws IllegalArgumentException if the given delivery id is invalid
   */
  TicketRecord createTicket(String reason, TicketPriority ticketPriority, Long deliveryId);

  /**
   * Fetches list of all Tickets sorted by priority from HIGH to LOW
   * @return {@link List<TicketRecord>} list of ticket records
   */
  List<TicketRecord> getAllTicketsSortedByPriority();
}
