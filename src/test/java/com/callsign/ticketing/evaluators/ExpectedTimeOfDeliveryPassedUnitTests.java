package com.callsign.ticketing.evaluators;

import com.callsign.ticketing.data.entities.Delivery;
import com.callsign.ticketing.tickets.ExpectedTimeOfDeliveryPassedTicketConditionEvaluator;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;

public class ExpectedTimeOfDeliveryPassedUnitTests {
  private static Delivery delivery = new Delivery();
  private static ExpectedTimeOfDeliveryPassedTicketConditionEvaluator evaluator =
      new ExpectedTimeOfDeliveryPassedTicketConditionEvaluator();

  @Test
  public void testExpectedTimeOfDeliveryNotPassed(){
    delivery.setExpectedDeliveryTime(LocalDateTime.now().plusSeconds(10));

    boolean result = evaluator.evaluate(delivery);

    Assert.assertFalse(result);
  }

  @Test
  public void testExpectedTimeOfDeliveryPassed(){
    delivery.setExpectedDeliveryTime(LocalDateTime.now().minusSeconds(10));

    boolean result = evaluator.evaluate(delivery);

    Assert.assertTrue(result);
  }
}
