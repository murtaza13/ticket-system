package com.callsign.ticketing.data.converters;

import com.callsign.ticketing.data.responses.TicketResponse;
import com.callsign.ticketing.data.transactions.TicketRecord;

/**
 * Helper class responsible for conversions between Ticket Business Object And Ticket Presentation Object
 */
public class TicketRecordAndTicketResponseMapper {
  /**
   * Converts the given business layer representation of a Ticket to the presentation layer representation.
   *
   * @param ticketRecord the record to convert
   * @return {@link TicketResponse} presentation layer representation of a ticket
   */
  public static TicketResponse toTicketResponse(TicketRecord ticketRecord) {
    TicketResponse ticketResponse = new TicketResponse();
    ticketResponse.setTicketId(ticketRecord.getTicketId());
    ticketResponse.setDeliveryId(ticketRecord.getDeliveryRecord().getDeliveryId());
    ticketResponse.setReason(ticketRecord.getReason());
    ticketResponse.setTicketPriority(ticketRecord.getTicketPriority());
    return ticketResponse;
  }
}
