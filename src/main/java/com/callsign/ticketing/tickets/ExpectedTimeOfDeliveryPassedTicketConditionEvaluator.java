package com.callsign.ticketing.tickets;

import com.callsign.ticketing.data.enums.TicketPriority;
import com.callsign.ticketing.data.transactions.DeliveryRecord;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ExpectedTimeOfDeliveryPassedTicketConditionEvaluator implements TicketConditionEvaluator {
  @Override
  public String getReasonId() {
    return "EXPECTED_TIME_OF_DELIVERY_PASSED";
  }

  @Override
  public boolean evaluate(DeliveryRecord delivery) {
    return LocalDateTime.now().isAfter(delivery.getExpectedDeliveryTime());
  }

  @Override
  public TicketPriority getDefaultTicketPriority() {
    return TicketPriority.HIGH;
  }
}
