package com.callsign.ticketing.data.transactions.businesslayer;

import com.callsign.ticketing.data.enums.TicketPriority;

public class TicketRecord {
  private Long ticketId;
  private String reason;
  private TicketPriority ticketPriority;
  private DeliveryRecord deliveryRecord;

  public TicketRecord() {
  }

  public TicketRecord(Long ticketId, String reason, TicketPriority ticketPriority) {
    this.ticketId = ticketId;
    this.reason = reason;
    this.ticketPriority = ticketPriority;
  }

  public TicketRecord(Long ticketId, String reason, TicketPriority ticketPriority, DeliveryRecord deliveryRecord) {
    this.ticketId = ticketId;
    this.reason = reason;
    this.ticketPriority = ticketPriority;
    this.deliveryRecord = deliveryRecord;
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

  public DeliveryRecord getDeliveryRecord() {
    return deliveryRecord;
  }

  public void setDeliveryRecord(DeliveryRecord deliveryRecord) {
    this.deliveryRecord = deliveryRecord;
  }
}
