package com.callsign.ticketing.evaluators;

import com.callsign.ticketing.data.entities.Delivery;
import com.callsign.ticketing.data.entities.Restaurant;
import com.callsign.ticketing.tickets.EstimatedTimeOFDeliveryGreaterThanExpectedTime;
import com.callsign.ticketing.tickets.ExpectedTimeOfDeliveryPassedTicketConditionEvaluator;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;

public class EstimatedTimeOfDeliveryGreaterThanExpectedTimeUnitTests {
  private static Delivery delivery;

  static {
    Restaurant restaurant =  new Restaurant();
    restaurant.setMeanTimeToPrepareFoodInSeconds(100);

    delivery = new Delivery();
    delivery.setRestaurant(restaurant);
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
