package com.callsign.ticketing.data.converters;

import com.callsign.ticketing.data.entities.Ticket;
import com.callsign.ticketing.data.transactions.TicketRecord;

/**
 * Helper class responsible for conversion between Ticket Data Object and Ticket Business Object
 */
public class TicketAndTicketRecordMapper {
  /**
   * Converts the given data layer representation of a Ticket to the business layer representation.
   *
   * @param ticket entity to convert
   * @return {@link TicketRecord} business layer representation of a Ticket
   */
  public static TicketRecord toTicketRecord(Ticket ticket) {
    TicketRecord ticketRecord = new TicketRecord();
    ticketRecord.setTicketId(ticket.getTicketId());
    ticketRecord.setReason(ticket.getReasonType());
    ticketRecord.setTicketPriority(ticket.getTicketPriority());
    ticketRecord.setDeliveryRecord(DeliveryAndDeliveryRecordMapper.toDeliveryRecord(ticket.getDelivery()));
    return ticketRecord;
  }
}
