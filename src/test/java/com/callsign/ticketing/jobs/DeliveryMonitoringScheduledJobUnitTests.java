package com.callsign.ticketing.jobs;

import com.callsign.ticketing.configurations.EmptyContext;
import com.callsign.ticketing.data.enums.CustomerType;
import com.callsign.ticketing.data.enums.DeliveryStatus;
import com.callsign.ticketing.data.enums.TicketPriority;
import com.callsign.ticketing.data.transactions.DeliveryRecord;
import com.callsign.ticketing.services.DeliveryService;
import com.callsign.ticketing.services.TicketService;
import com.callsign.ticketing.tickets.DeliveryStatusNotChangedFromReceivedFor10Minutes;
import com.callsign.ticketing.tickets.EstimatedTimeOFDeliveryGreaterThanExpectedTime;
import com.callsign.ticketing.tickets.ExpectedTimeOfDeliveryPassedTicketConditionEvaluator;
import com.callsign.ticketing.tickets.TicketConditionEvaluator;
import mockit.Expectations;
import mockit.Mocked;
import mockit.Verifications;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDateTime;
import java.util.*;

@ContextConfiguration(classes = {EmptyContext.class})
public class DeliveryMonitoringScheduledJobUnitTests {

  @Test
  public void testNoDeliveriesCreateNoTickets(@Mocked DeliveryService deliveryService,
                                              @Mocked TicketService ticketService,
                                              @Mocked ApplicationContext applicationContext) {
    new Expectations() {{
      deliveryService.getNUnDeliveredDeliveryRecordsWithUpdateLock(anyInt);
      result = new ArrayList<>();
    }};

    setApplicationContextExpectation(applicationContext);

    DeliveryMonitoringScheduledJob deliveryMonitoringScheduledJob = new DeliveryMonitoringScheduledJob(
        deliveryService,
        ticketService,
        applicationContext
    );

    deliveryMonitoringScheduledJob.runJob();

    new Verifications() {{
      ticketService.createTicket(anyString, (TicketPriority) any, anyLong);
      times = 0;
    }};
  }

  @Test
  public void testSingleDeliveryButNotQualifiedCreateNoTickets(@Mocked DeliveryService deliveryService,
                                                               @Mocked TicketService ticketService,
                                                               @Mocked ApplicationContext applicationContext) {
    DeliveryRecord deliveryRecord = getDeliveryRecord( LocalDateTime.now().plusSeconds(1000), CustomerType.VIP);

    new Expectations() {{
      deliveryService.getNUnDeliveredDeliveryRecordsWithUpdateLock(anyInt);
      result = Collections.singletonList(deliveryRecord);
    }};

    setApplicationContextExpectation(applicationContext);

    DeliveryMonitoringScheduledJob deliveryMonitoringScheduledJob = new DeliveryMonitoringScheduledJob(
        deliveryService,
        ticketService,
        applicationContext
    );

    deliveryMonitoringScheduledJob.runJob();

    new Verifications() {{
      ticketService.createTicket(anyString, (TicketPriority) any, anyLong);
      times = 0;
    }};
  }

  @Test
  public void testSingleDeliveryButQualifiedForSingleReasonSingleTicketCreated(@Mocked DeliveryService deliveryService,
                                                                               @Mocked TicketService ticketService,
                                                                               @Mocked ApplicationContext applicationContext) {
    DeliveryRecord deliveryRecord = getDeliveryRecord(LocalDateTime.now().plusSeconds(100), CustomerType.VIP);

    new Expectations() {{
      deliveryService.getNUnDeliveredDeliveryRecordsWithUpdateLock(anyInt);
      result = Collections.singletonList(deliveryRecord);
    }};

    setApplicationContextExpectation(applicationContext);

    DeliveryMonitoringScheduledJob deliveryMonitoringScheduledJob = new DeliveryMonitoringScheduledJob(
        deliveryService,
        ticketService,
        applicationContext
    );

    deliveryMonitoringScheduledJob.runJob();

    new Verifications() {{
      ticketService.createTicket(anyString, (TicketPriority) any, anyLong);
      times = 1;
    }};
  }

  @Test
  public void testPriorityOfTicketGetsOverriddenCauseCustomerHasGreaterPriority(@Mocked DeliveryService deliveryService,
                                                                               @Mocked TicketService ticketService,
                                                                               @Mocked ApplicationContext applicationContext) {
    DeliveryRecord deliveryRecord = getDeliveryRecord(LocalDateTime.now().plusSeconds(100), CustomerType.VIP);

    new Expectations() {{
      deliveryService.getNUnDeliveredDeliveryRecordsWithUpdateLock(anyInt);
      result = Collections.singletonList(deliveryRecord);
    }};

    setApplicationContextExpectation(applicationContext);

    DeliveryMonitoringScheduledJob deliveryMonitoringScheduledJob = new DeliveryMonitoringScheduledJob(
        deliveryService,
        ticketService,
        applicationContext
    );

    deliveryMonitoringScheduledJob.runJob();

    new Verifications() {{
      String reason;
      TicketPriority priority;
      Long id;

      ticketService.createTicket(reason = withCapture(), priority = withCapture(), id = withCapture());
      Assert.assertEquals(TicketPriority.HIGH, priority);
      Assert.assertEquals("ESTIMATED_TIME_OF_DELIVERY_GREATER_THAN_EXPECTED_TIME", reason);
      Assert.assertEquals(Long.valueOf(1), id);
      times = 1;
    }};
  }

  @Test
  public void testPriorityOfTicketDoesNotGetOverriddenCauseReasonHasGreaterPriorityWithMultipleTicketCreation(
      @Mocked DeliveryService deliveryService,
      @Mocked TicketService ticketService,
      @Mocked ApplicationContext applicationContext) {
    DeliveryRecord deliveryRecord = getDeliveryRecord(LocalDateTime.now().plusSeconds(100), CustomerType.NEW,
        300);

    new Expectations() {{
      deliveryService.getNUnDeliveredDeliveryRecordsWithUpdateLock(anyInt);
      result = Collections.singletonList(deliveryRecord);
    }};

    setApplicationContextExpectation(applicationContext);

    DeliveryMonitoringScheduledJob deliveryMonitoringScheduledJob = new DeliveryMonitoringScheduledJob(
        deliveryService,
        ticketService,
        applicationContext
    );

    deliveryMonitoringScheduledJob.runJob();

    new Verifications() {{
      String reason;
      TicketPriority priority;
      Long id;

      ticketService.createTicket(reason = withCapture(), priority = withCapture(), id = withCapture());
      Assert.assertEquals(TicketPriority.MEDIUM, priority);
      Assert.assertEquals("ESTIMATED_TIME_OF_DELIVERY_GREATER_THAN_EXPECTED_TIME", reason);
      Assert.assertEquals(Long.valueOf(1), id);
      times = 1;
    }};
  }

  private DeliveryRecord getDeliveryRecord(LocalDateTime expectedDeliveryTime, CustomerType customerType) {
    return getDeliveryRecord(expectedDeliveryTime, customerType, 100);
  }

  private DeliveryRecord getDeliveryRecord(LocalDateTime expectedDeliveryTime, CustomerType customerType,
                                           int restaurantMeanTime){
    DeliveryRecord deliveryRecord = new DeliveryRecord();
    deliveryRecord.setDeliveryId(1L);
    deliveryRecord.setCustomerType(customerType);
    deliveryRecord.setDeliveryStatus(DeliveryStatus.RECEIVED);
    deliveryRecord.setCurrentDistanceFromDestinationInMetres(100);
    deliveryRecord.setTimeToReachDestinationInSeconds(100);
    deliveryRecord.setRestaurantsMeanTimetoPrepareFood(restaurantMeanTime);
    deliveryRecord.setExpectedDeliveryTime(expectedDeliveryTime);
    deliveryRecord.setCreatedAt(LocalDateTime.now());
    deliveryRecord.setTickets(new ArrayList<>());
    return deliveryRecord;
  }

  private void setApplicationContextExpectation(ApplicationContext applicationContext) {
    Map<String, TicketConditionEvaluator> map = new LinkedHashMap<>();
    map.put(EstimatedTimeOFDeliveryGreaterThanExpectedTime.class.getName(),
        new EstimatedTimeOFDeliveryGreaterThanExpectedTime());
    map.put(DeliveryStatusNotChangedFromReceivedFor10Minutes.class.getName(),
        new DeliveryStatusNotChangedFromReceivedFor10Minutes());
    map.put(ExpectedTimeOfDeliveryPassedTicketConditionEvaluator.class.getName(),
        new ExpectedTimeOfDeliveryPassedTicketConditionEvaluator());
    new Expectations() {{
      applicationContext.getBeansOfType(TicketConditionEvaluator.class);
      result = map;
    }};
  }
}
