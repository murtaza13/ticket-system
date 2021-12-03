package com.callsign.ticketing.data.converters;

import com.callsign.ticketing.data.entities.Ticket;
import com.callsign.ticketing.data.transactions.TicketRecord;

public class TicketConverter {

  public static TicketRecord convert(Ticket ticket) {
    return new TicketRecord(
        ticket.getTicketId(),
        ticket.getReasonType(),
        ticket.getTicketPriority(),
        ticket.getDelivery()
    );
  }
}
