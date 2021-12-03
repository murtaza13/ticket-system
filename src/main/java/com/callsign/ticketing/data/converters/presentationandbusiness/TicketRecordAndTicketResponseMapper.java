package com.callsign.ticketing.data.converters.presentationandbusiness;

import com.callsign.ticketing.data.transactions.businesslayer.TicketRecord;
import com.callsign.ticketing.data.transactions.presentationlayer.TicketResponse;

public class TicketRecordAndTicketResponseMapper {
  public static TicketResponse toTicketResponse(TicketRecord ticketRecord){
    TicketResponse ticketResponse = new TicketResponse();
    ticketResponse.setTicketId(ticketRecord.getTicketId());
    ticketResponse.setDeliveryId(ticketRecord.getDeliveryRecord().getDeliveryId());
    ticketResponse.setReason(ticketRecord.getReason());
    ticketResponse.setTicketPriority(ticketRecord.getTicketPriority());
    return ticketResponse;
  }
}
