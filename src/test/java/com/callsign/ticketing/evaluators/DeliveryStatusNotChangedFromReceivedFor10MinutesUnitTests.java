package com.callsign.ticketing.evaluators;

import com.callsign.ticketing.data.entities.Delivery;
import com.callsign.ticketing.data.enums.DeliveryStatus;
import com.callsign.ticketing.tickets.DeliveryStatusNotChangedFromReceivedFor10Minutes;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;

public class DeliveryStatusNotChangedFromReceivedFor10MinutesUnitTests {
  private static Delivery delivery = new Delivery();
  private static DeliveryStatusNotChangedFromReceivedFor10Minutes evaluator =
      new DeliveryStatusNotChangedFromReceivedFor10Minutes();

  @Test
  public void testWithDeliveryStatusNotReceivedButCreatedAtGreaterThan10Minutes(){
    delivery.setCreatedAt(LocalDateTime.now().minusMinutes(10));
    delivery.setDeliveryStatus(DeliveryStatus.PREPARING);

    boolean result = evaluator.evaluate(delivery);

    Assert.assertFalse(result);
  }

  @Test
  public void testWithDeliverStatusReceivedButCreatedAtLessThan10Minutes(){
    delivery.setCreatedAt(LocalDateTime.now());
    delivery.setDeliveryStatus(DeliveryStatus.RECEIVED);

    boolean result = evaluator.evaluate(delivery);

    Assert.assertFalse(result);
  }

  @Test
  public void testWithDeliveryStatusReceivedAndCreatedAtGreaterThan10Minutes(){
    delivery.setCreatedAt(LocalDateTime.now().minusMinutes(11));
    delivery.setDeliveryStatus(DeliveryStatus.RECEIVED);

    boolean result = evaluator.evaluate(delivery);

    Assert.assertTrue(result);
  }
}
