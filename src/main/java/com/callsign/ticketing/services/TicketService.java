package com.callsign.ticketing.services;

import com.callsign.ticketing.data.enums.TicketPriority;
import com.callsign.ticketing.data.transactions.businesslayer.TicketRecord;

import java.util.List;
import java.util.Set;

public interface TicketService {
  TicketRecord createTicket(String reason, TicketPriority ticketPriority, Long deliveryId);
  List<TicketRecord> getAllTicketsSortedByPriority();
}
