package com.callsign.ticketing.tickets;

import com.callsign.ticketing.data.entities.Delivery;
import com.callsign.ticketing.data.enums.TicketPriority;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class EstimatedTimeOFDeliveryGreaterThanExpectedTime implements TicketConditionEvaluator {
  @Override
  public String getEvaluatorId() {
    return "ESTIMATED_TIME_OF_DELIVERY_GREATER_THAN_EXPECTED_TIME";
  }

  @Override
  public boolean evaluate(Delivery delivery) {
    int estimatedTimeRequiredInSeconds = delivery.getRestaurant().getMeanTimeToPrepareFoodInSeconds() +
        delivery.getTimeToReachDestinationInSeconds();
    LocalDateTime estimatedDeliveryTime = delivery.getCreatedAt().plusSeconds(estimatedTimeRequiredInSeconds);
    return estimatedDeliveryTime.isAfter(delivery.getExpectedDeliveryTime());
  }

  @Override
  public TicketPriority getDefaultTicketPriority() {
    return TicketPriority.MEDIUM;
  }
}
