package com.callsign.ticketing.controllers;

import com.callsign.ticketing.data.entities.Ticket;
import com.callsign.ticketing.data.enums.TicketPriority;
import com.callsign.ticketing.data.transactions.businesslayer.DeliveryRecord;
import com.callsign.ticketing.data.transactions.businesslayer.TicketRecord;
import com.callsign.ticketing.data.transactions.presentationlayer.TicketResponse;
import com.callsign.ticketing.services.TicketService;
import mockit.Expectations;
import mockit.Mocked;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

public class TicketRestControllerUnitTests {
  @Test
  public void testWithZeroTickets(@Mocked TicketService ticketService){
    new Expectations(){{
      ticketService.getAllTicketsSortedByPriority();
      result = Collections.emptyList();
    }};

    ResponseEntity<List<TicketResponse>> response = new TicketRestController(ticketService).getAllTicketsSorted();

    Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    Assert.assertEquals(0, response.getBody().size());
  }

  @Test
  public void testWithMultipleTickets(@Mocked TicketService ticketService){
    final String dummyReason1 = "A";
    final String dummyReason2 = "B";

    DeliveryRecord deliveryRecord = new DeliveryRecord(1L);
    TicketRecord ticketRecord = new TicketRecord(1L, dummyReason1, TicketPriority.HIGH, deliveryRecord);
    TicketRecord ticketRecord2 = new TicketRecord(2L, dummyReason2, TicketPriority.LOW, deliveryRecord);

    new Expectations(){{
      ticketService.getAllTicketsSortedByPriority();
      result = Arrays.asList(ticketRecord, ticketRecord2);
    }};

    ResponseEntity<List<TicketResponse>> response = new TicketRestController(ticketService).getAllTicketsSorted();
    Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

    List<TicketResponse> result =  response.getBody();
    Assert.assertEquals(2, result.size());

    TicketResponse one = result.get(0);
    Assert.assertEquals(Long.valueOf(1), one.getTicketId());
    Assert.assertEquals(Long.valueOf(1), one.getDeliveryId());
    Assert.assertEquals(TicketPriority.HIGH, one.getTicketPriority());
    Assert.assertEquals(dummyReason1, one.getReason());

    TicketResponse two = result.get(1);
    Assert.assertEquals(Long.valueOf(2), two.getTicketId());
    Assert.assertEquals(Long.valueOf(1), two.getDeliveryId());
    Assert.assertEquals(TicketPriority.LOW, two.getTicketPriority());
    Assert.assertEquals(dummyReason2, two.getReason());
  }
}
