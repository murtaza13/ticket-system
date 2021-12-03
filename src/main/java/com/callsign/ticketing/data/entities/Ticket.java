package com.callsign.ticketing.data.entities;

import com.callsign.ticketing.data.enums.TicketPriority;

import javax.persistence.*;

@Entity
@Table(name = "ticket")
public class Ticket {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ticket_id")
  private Long ticketId;

  @Column(name = "reason_type")
  private String reasonType;

  @Column(name = "ticket_priority", columnDefinition = "ENUM('HIGH', 'MEDIUM', 'LOW')")
  @Enumerated(EnumType.STRING)
  private TicketPriority ticketPriority;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "delivery_id", referencedColumnName = "delivery_id")
  private Delivery delivery;

  public Ticket() {
  }

  public Ticket(String reasonType, TicketPriority ticketPriority, Delivery delivery) {
    this.reasonType = reasonType;
    this.ticketPriority = ticketPriority;
    this.delivery = delivery;
  }

  public Long getTicketId() {
    return ticketId;
  }

  public void setTicketId(Long ticketId) {
    this.ticketId = ticketId;
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

  public String getReasonType() {
    return reasonType;
  }

  public void setReasonType(String reasonType) {
    this.reasonType = reasonType;
  }
}
