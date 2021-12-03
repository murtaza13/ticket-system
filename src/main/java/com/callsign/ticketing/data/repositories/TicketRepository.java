package com.callsign.ticketing.data.repositories;

import com.callsign.ticketing.data.entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
  Set<Ticket> getAllByOrderByTicketPriority();
}
