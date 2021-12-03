package com.callsign.ticketing.tickets;

import com.callsign.ticketing.data.entities.Delivery;
import com.callsign.ticketing.data.enums.DeliveryStatus;
import com.callsign.ticketing.data.enums.TicketPriority;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DeliveryStatusNotChangedFromReceivedFor10Minutes implements TicketConditionEvaluator {
  @Override
  public String getEvaluatorId() {
    return "DELIVERY_STATUS_NOT_CHANGED_FROM_RECIEVED_FOR_10_MINUTES";
  }

  @Override
  public boolean evaluate(Delivery delivery) {
    return LocalDateTime.now().isAfter(delivery.getCreatedAt().plusMinutes(10)) &&
        delivery.getDeliveryStatus().equals(DeliveryStatus.RECEIVED);
  }

  @Override
  public TicketPriority getDefaultTicketPriority() {
    return TicketPriority.LOW;
  }
}
