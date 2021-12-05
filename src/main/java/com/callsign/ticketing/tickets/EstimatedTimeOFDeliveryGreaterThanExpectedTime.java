package com.callsign.ticketing.tickets;

import com.callsign.ticketing.data.enums.TicketPriority;
import com.callsign.ticketing.data.transactions.DeliveryRecord;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class EstimatedTimeOFDeliveryGreaterThanExpectedTime implements TicketConditionEvaluator {
  @Override
  public String getReasonId() {
    return "ESTIMATED_TIME_OF_DELIVERY_GREATER_THAN_EXPECTED_TIME";
  }

  @Override
  public boolean evaluate(DeliveryRecord delivery) {
    int estimatedTimeRequiredInSeconds = delivery.getRestaurantsMeanTimetoPrepareFood() +
        delivery.getTimeToReachDestinationInSeconds();
    LocalDateTime estimatedDeliveryTime = delivery.getCreatedAt().plusSeconds(estimatedTimeRequiredInSeconds);
    return estimatedDeliveryTime.isAfter(delivery.getExpectedDeliveryTime());
  }

  @Override
  public TicketPriority getDefaultTicketPriority() {
    return TicketPriority.MEDIUM;
  }
}
