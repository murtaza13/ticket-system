package com.callsign.ticketing.services;

import com.callsign.ticketing.data.entities.Delivery;
import com.callsign.ticketing.data.entities.Ticket;
import com.callsign.ticketing.data.enums.TicketPriority;
import com.callsign.ticketing.data.repositories.TicketRepository;
import com.callsign.ticketing.data.transactions.TicketRecord;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
@TestPropertySource("file:src/test/resources/test-application.properties")
public class TicketServiceUnitTests {

  @Autowired
  private TicketService ticketService;

  @Test
  public void weirdTest(@Mocked TicketService ticketService){
    new Expectations(){{
      ticketService.getString(anyString);
      result = "Hello World";
    }};
    System.out.println(ticketService.getString("Bye"));
    Assert.assertEquals(1,1);
  }
}
