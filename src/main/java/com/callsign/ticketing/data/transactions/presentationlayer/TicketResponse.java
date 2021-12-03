package com.callsign.ticketing.data.transactions.presentationlayer;

import com.callsign.ticketing.data.enums.TicketPriority;

public class TicketResponse {
  private Long ticketId;
  private Long deliveryId;
  private String reason;
  private TicketPriority ticketPriority;

  public TicketResponse() {
  }

  public TicketResponse(Long ticketId, Long deliveryId, String reason, TicketPriority ticketPriority) {
    this.ticketId = ticketId;
    this.deliveryId = deliveryId;
    this.reason = reason;
    this.ticketPriority = ticketPriority;
  }

  public Long getTicketId() {
    return ticketId;
  }

  public void setTicketId(Long ticketId) {
    this.ticketId = ticketId;
  }

  public Long getDeliveryId() {
    return deliveryId;
  }

  public void setDeliveryId(Long deliveryId) {
    this.deliveryId = deliveryId;
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
}
