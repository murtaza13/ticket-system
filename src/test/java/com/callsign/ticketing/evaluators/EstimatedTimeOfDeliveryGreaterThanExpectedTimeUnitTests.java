package com.callsign.ticketing.evaluators;

import com.callsign.ticketing.data.transactions.DeliveryRecord;
import com.callsign.ticketing.tickets.EstimatedTimeOFDeliveryGreaterThanExpectedTime;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;

public class EstimatedTimeOfDeliveryGreaterThanExpectedTimeUnitTests {
  private static DeliveryRecord delivery;

  static {
    delivery = new DeliveryRecord();
    delivery.setRestaurantsMeanTimetoPrepareFood(100);
    delivery.setCreatedAt(LocalDateTime.now());
  }

  @Test
  public void testEstimatedAndExpectedTimesAreEqual(){
    delivery.setTimeToReachDestinationInSeconds(100);
    delivery.setExpectedDeliveryTime(LocalDateTime.now().plusSeconds(100 + 100));

    boolean result = new EstimatedTimeOFDeliveryGreaterThanExpectedTime().evaluate(delivery);

    Assert.assertFalse(result);
  }

  @Test
  public void testEstimatedTimeLessThanExpectedTime(){
    delivery.setTimeToReachDestinationInSeconds(100);
    delivery.setExpectedDeliveryTime(LocalDateTime.now().plusSeconds(250));

    boolean result = new EstimatedTimeOFDeliveryGreaterThanExpectedTime().evaluate(delivery);

    Assert.assertFalse(result);
  }

  @Test
  public void testEstimatedTimeGreaterThanExpectedTime(){
    delivery.setTimeToReachDestinationInSeconds(100);
    delivery.setExpectedDeliveryTime(LocalDateTime.now().plusSeconds(100));

    boolean result = new EstimatedTimeOFDeliveryGreaterThanExpectedTime().evaluate(delivery);

    Assert.assertTrue(result);
  }


}
