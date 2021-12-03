package com.callsign.ticketing.services;

import com.callsign.ticketing.data.enums.TicketPriority;
import com.callsign.ticketing.data.repositories.DeliveryRepository;
import com.callsign.ticketing.data.repositories.RestaurantRepository;
import com.callsign.ticketing.data.transactions.businesslayer.TicketRecord;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@RunWith(SpringRunner.class)
@TestPropertySource("file:src/test/resources/test-application.properties")
public class TicketServiceIntegrationTests {
  private static final String TEST_REASON = "testeason";
  private static final long PRE_LOADED_DELIVERY_ID = 1L;
  @Autowired
  private TicketService ticketService;
  @Autowired
  private DeliveryRepository deliveryRepository;
  @Autowired
  private RestaurantRepository restaurantRepository;

  @Transactional
  @Sql("file:src/test/resources/TicketServiceIntegrationTests.sql")
  @Test
  public void testCreatingAHighPriorityTicket() {
    TicketRecord ticketRecord = ticketService.createTicket(TEST_REASON, TicketPriority.HIGH, PRE_LOADED_DELIVERY_ID);

    Assert.assertEquals(Long.valueOf(PRE_LOADED_DELIVERY_ID), ticketRecord.getDeliveryRecord().getDeliveryId());
    Assert.assertEquals(TEST_REASON, ticketRecord.getReason());
    Assert.assertEquals(TicketPriority.HIGH, ticketRecord.getTicketPriority());
  }

  @Test(expected = RuntimeException.class)
  public void testCreatingATicketWithInvalidDelivery(){
   ticketService.createTicket(TEST_REASON, TicketPriority.HIGH, 100323L);
  }

  @Transactional
  @Test
  public void testTicketsSorted(){

  }
}
