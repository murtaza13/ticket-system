package com.callsign.ticketing.data.converters.businessanddata;

import com.callsign.ticketing.data.entities.Ticket;
import com.callsign.ticketing.data.transactions.businesslayer.TicketRecord;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class TicketAndTicketRecordMapper {

  public static TicketRecord toTicketRecord(Ticket ticket) {
    TicketRecord ticketRecord =  new TicketRecord();
    ticketRecord.setTicketId(ticket.getTicketId());
    ticketRecord.setReason(ticket.getReasonType());
    ticketRecord.setTicketPriority(ticket.getTicketPriority());
    ticketRecord.setDeliveryRecord(DeliveryAndDeliveryRecordMapper.toDeliveryRecord(ticket.getDelivery()));
    return ticketRecord;
  }

  public static Set<TicketRecord> toTicketRecords(Collection<Ticket> tickets){
    return tickets.stream().map(TicketAndTicketRecordMapper::toTicketRecord).collect(Collectors.toSet());
  }

  public static Ticket toTicket(TicketRecord ticketRecord){
    Ticket ticket = new Ticket();
    ticket.setTicketId(ticketRecord.getTicketId());
    ticket.setReasonType(ticketRecord.getReason());
    ticket.setTicketPriority(ticketRecord.getTicketPriority());
    ticket.setDelivery(ticket.getDelivery());
    return ticket;
  }
}
