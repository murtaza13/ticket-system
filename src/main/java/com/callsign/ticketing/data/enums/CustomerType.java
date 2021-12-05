package com.callsign.ticketing.data.enums;

/**
 * Representation of possible types of Customers
 */
public enum CustomerType {
  VIP(TicketPriority.HIGH),
  LOYAL(TicketPriority.MEDIUM),
  NEW(TicketPriority.LOW);

  final TicketPriority ticketPriority;

  CustomerType(TicketPriority ticketPriority) {
    this.ticketPriority = ticketPriority;
  }

  public TicketPriority getTicketPriority() {
    return ticketPriority;
  }
}
