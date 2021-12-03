package com.callsign.ticketing.services;

import com.callsign.ticketing.data.converters.businessanddata.TicketAndTicketRecordMapper;
import com.callsign.ticketing.data.entities.Delivery;
import com.callsign.ticketing.data.entities.Ticket;
import com.callsign.ticketing.data.enums.TicketPriority;
import com.callsign.ticketing.data.repositories.TicketRepository;
import com.callsign.ticketing.data.transactions.businesslayer.TicketRecord;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TicketServiceImpl implements TicketService {
  private final TicketRepository ticketRepository;
  private final DeliveryService deliveryService;

  public TicketServiceImpl(TicketRepository ticketRepository, DeliveryService deliveryService) {
    this.ticketRepository = ticketRepository;
    this.deliveryService = deliveryService;
  }

  @Override
  public TicketRecord createTicket(String reason, TicketPriority ticketPriority, Long deliveryId) {
    Delivery delivery = deliveryService.getById(deliveryId).orElseThrow(() -> new RuntimeException("No delivery record " +
        "found for the given id: " + deliveryId));
    Ticket ticket = new Ticket(reason, ticketPriority, delivery);
    ticketRepository.save(ticket);
    return TicketAndTicketRecordMapper.toTicketRecord(ticket);
  }

  @Override
  public List<TicketRecord> getAllTicketsSortedByPriority() {
    return ticketRepository.getAllByOrderByTicketPriority()
        .stream().map(TicketAndTicketRecordMapper::toTicketRecord).collect(Collectors.toList());
  }
}
