package com.callsign.ticketing.data.repositories;

import com.callsign.ticketing.data.entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
  List<Ticket> getAllByOrderByTicketPriority();
}
