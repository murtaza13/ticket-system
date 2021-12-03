package com.callsign.ticketing.tickets;

import com.callsign.ticketing.data.entities.Delivery;
import com.callsign.ticketing.data.enums.TicketPriority;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ExpectedTimeOfDeliveryPassedTicketConditionEvaluator implements TicketConditionEvaluator {
  @Override
  public String getEvaluatorId() {
    return "EXPECTED_TIME_OF_DELIVERY_PASSED";
  }

  @Override
  public boolean evaluate(Delivery delivery) {
    return LocalDateTime.now().isAfter(delivery.getExpectedDeliveryTime());
  }

  @Override
  public TicketPriority getDefaultTicketPriority() {
    return TicketPriority.HIGH;
  }
}
