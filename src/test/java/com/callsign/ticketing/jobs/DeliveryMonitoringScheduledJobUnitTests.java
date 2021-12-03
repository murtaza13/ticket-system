package com.callsign.ticketing.jobs;

import com.callsign.ticketing.configurations.EmptyContext;
import com.callsign.ticketing.data.entities.Delivery;
import com.callsign.ticketing.data.entities.Restaurant;
import com.callsign.ticketing.data.enums.CustomerType;
import com.callsign.ticketing.data.enums.DeliveryStatus;
import com.callsign.ticketing.data.enums.TicketPriority;
import com.callsign.ticketing.services.DeliveryService;
import com.callsign.ticketing.services.TicketService;
import com.callsign.ticketing.tickets.DeliveryStatusNotChangedFromReceivedFor10Minutes;
import com.callsign.ticketing.tickets.EstimatedTimeOFDeliveryGreaterThanExpectedTime;
import com.callsign.ticketing.tickets.ExpectedTimeOfDeliveryPassedTicketConditionEvaluator;
import com.callsign.ticketing.tickets.TicketConditionEvaluator;
import mockit.Expectations;
import mockit.Mocked;
import mockit.Verifications;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

@ContextConfiguration(classes = {EmptyContext.class} )
public class DeliveryMonitoringScheduledJobUnitTests {

  @Test
  public void testNoDeliveriesCreateNoTickets(@Mocked DeliveryService deliveryService, @Mocked TicketService ticketService,
                       @Mocked ApplicationContext applicationContext){
    new Expectations(){{
      deliveryService.getNInCompleteDeliveries(anyInt);
      result = new HashSet<>();
    }};

    setApplicationContextExpectation(applicationContext);

    DeliveryMonitoringScheduledJob deliveryMonitoringScheduledJob = new DeliveryMonitoringScheduledJob(
        deliveryService,
        ticketService,
        applicationContext
    );

    deliveryMonitoringScheduledJob.runJob();

    new Verifications(){{
      ticketService.createTicket(anyString, (TicketPriority) any, anyLong); times = 0;
    }};
  }

  @Test
  public void testSingleDeliveryButNotQualifiedCreateNoTickets(@Mocked DeliveryService deliveryService,
                                                                 @Mocked TicketService ticketService,
                                                                 @Mocked ApplicationContext applicationContext){
    Delivery delivery = new Delivery(
        CustomerType.VIP,
        DeliveryStatus.RECEIVED,
        100,
        LocalDateTime.now().plusSeconds(1000),
        100,
        LocalDateTime.now(),
        new Restaurant(100)
     );


    new Expectations(){{
      deliveryService.getNInCompleteDeliveries(anyInt);
      result = Collections.singleton(delivery);
    }};

    setApplicationContextExpectation(applicationContext);

    DeliveryMonitoringScheduledJob deliveryMonitoringScheduledJob = new DeliveryMonitoringScheduledJob(
        deliveryService,
        ticketService,
        applicationContext
    );

    deliveryMonitoringScheduledJob.runJob();

    new Verifications(){{
      ticketService.createTicket(anyString, (TicketPriority) any, anyLong); times = 0;
    }};
  }

  @Test
  public void testSingleDeliveryButQualifiedForSingleReasonSingleTicketCreated(@Mocked DeliveryService deliveryService,
                                                                               @Mocked TicketService ticketService,
                                                                               @Mocked ApplicationContext applicationContext){
    Delivery delivery = new Delivery(
        CustomerType.VIP,
        DeliveryStatus.RECEIVED,
        100,
        LocalDateTime.now().plusSeconds(100),
        100,
        LocalDateTime.now(),
        new Restaurant(100)
    );

    new Expectations(){{
      deliveryService.getNInCompleteDeliveries(anyInt);
      result = Collections.singleton(delivery);
    }};

    setApplicationContextExpectation(applicationContext);

    DeliveryMonitoringScheduledJob deliveryMonitoringScheduledJob = new DeliveryMonitoringScheduledJob(
        deliveryService,
        ticketService,
        applicationContext
    );

    deliveryMonitoringScheduledJob.runJob();

    new Verifications(){{
      ticketService.createTicket(anyString, (TicketPriority) any, anyLong); times = 1;
    }};
  }

  private void setApplicationContextExpectation(ApplicationContext applicationContext){
    Map<String, TicketConditionEvaluator> map = new HashMap<>();
    map.put(EstimatedTimeOFDeliveryGreaterThanExpectedTime.class.getName(),
        new EstimatedTimeOFDeliveryGreaterThanExpectedTime());
    map.put(DeliveryStatusNotChangedFromReceivedFor10Minutes.class.getName(),
        new DeliveryStatusNotChangedFromReceivedFor10Minutes());
    map.put(ExpectedTimeOfDeliveryPassedTicketConditionEvaluator.class.getName(),
        new ExpectedTimeOfDeliveryPassedTicketConditionEvaluator());
     new Expectations(){{
       applicationContext.getBeansOfType(TicketConditionEvaluator.class);
       result = map;
     }};
  }
}
