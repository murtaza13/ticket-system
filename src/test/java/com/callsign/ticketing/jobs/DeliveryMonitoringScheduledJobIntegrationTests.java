package com.callsign.ticketing.jobs;

import com.callsign.ticketing.data.entities.Delivery;
import com.callsign.ticketing.data.entities.Ticket;
import com.callsign.ticketing.data.enums.TicketPriority;
import com.callsign.ticketing.data.repositories.TicketRepository;
import com.callsign.ticketing.tickets.EstimatedTimeOFDeliveryGreaterThanExpectedTime;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

@SpringBootTest
@RunWith(SpringRunner.class)
@TestPropertySource("file:src/test/resources/test-application.properties")
public class DeliveryMonitoringScheduledJobIntegrationTests {
  @Autowired
  private DeliveryMonitoringScheduledJob deliveryMonitoringScheduledJob;

  @Autowired
  private EstimatedTimeOFDeliveryGreaterThanExpectedTime estimatedTimeOFDeliveryGreaterThanExpectedTime;

  @Autowired
  private TicketRepository ticketRepository;

  @Transactional
  @Sql("file:src/test/resources/TicketsCreationForDeliveriesWithSingleReason.sql")
  @Test
  public void testTicketsCreationForDeliveriesWithSingleReason(){
    final String reason = estimatedTimeOFDeliveryGreaterThanExpectedTime.getEvaluatorId();
    deliveryMonitoringScheduledJob.runJob();
    List<Ticket> allTickets = ticketRepository.findAll();
    Map<Delivery, Ticket> map = allTickets.stream().collect(toMap(Ticket::getDelivery, ticket -> ticket));

    Assert.assertEquals(3, allTickets.size());

    map.forEach((del, tick) -> assertEqualityInTickets(tick, reason, TicketPriority.HIGH, del.getDeliveryId()));
  }

  @Transactional
  @Sql("file:src/test/resources/MultipleTicketsCreationForSingleDeliveryWithMultipleReasons.sql")
  @Test
  public void testMultipleTicketsCreationForSingleDeliveryWithMultipleReasons(){
    deliveryMonitoringScheduledJob.runJob();
    List<Ticket> allTickets = ticketRepository.findAll();

    Assert.assertEquals(2, allTickets.size());
    Assert.assertEquals(allTickets.get(0).getDelivery(), allTickets.get(1).getDelivery());
  }

  @Transactional
  @Sql("file:src/test/resources/NoTicketsCreatedForDeliveredOrders.sql")
  @Test
  public void testNoTicketsCreatedForDeliveredOrders(){
    deliveryMonitoringScheduledJob.runJob();
    List<Ticket> allTickets = ticketRepository.findAll();
    Assert.assertEquals(0, allTickets.size());
  }

  private void assertEqualityInTickets(Ticket ticket, String reason, TicketPriority ticketPriority, Long deliveryId){
    Assert.assertEquals(deliveryId, ticket.getDelivery().getDeliveryId());
    Assert.assertEquals(ticketPriority, ticket.getTicketPriority());
    Assert.assertEquals(reason, ticket.getReasonType());
  }
}
