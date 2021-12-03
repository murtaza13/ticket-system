package com.callsign.ticketing.data.transactions;

import com.callsign.ticketing.data.entities.Delivery;
import com.callsign.ticketing.data.enums.TicketPriority;

public class TicketRecord {
  private Long ticketId;
  private String reason;
  private TicketPriority ticketPriority;
  private Delivery delivery;

  public TicketRecord(Long ticketId, String reason, TicketPriority ticketPriority, Delivery delivery) {
    this.ticketId = ticketId;
    this.reason = reason;
    this.ticketPriority = ticketPriority;
    this.delivery = delivery;
  }

  public Long getTicketId() {
    return ticketId;
  }

  public void setTicketId(Long ticketId) {
    this.ticketId = ticketId;
  }

  public String getReason() {
    return reason;
  }

  public void setReason(String reason) {
    this.reason = reason;
  }

  public TicketPriority getTicketPriority() {
    return ticketPriority;
  }

  public void setTicketPriority(TicketPriority ticketPriority) {
    this.ticketPriority = ticketPriority;
  }

  public Delivery getDelivery() {
    return delivery;
  }

  public void setDelivery(Delivery delivery) {
    this.delivery = delivery;
  }
}
