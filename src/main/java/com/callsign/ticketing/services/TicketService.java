package com.callsign.ticketing.services;

import com.callsign.ticketing.data.enums.TicketPriority;
import com.callsign.ticketing.data.transactions.TicketRecord;

import java.util.List;
import java.util.Set;

public interface TicketService {
  TicketRecord createTicket(String reason, TicketPriority ticketPriority, Long deliveryId);
  Set<TicketRecord> getAllTicketsSortedByPriority();
  String getString(String s);
}
