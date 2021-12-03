package com.callsign.ticketing.tickets;

import com.callsign.ticketing.data.entities.Delivery;
import com.callsign.ticketing.data.enums.TicketPriority;

public interface TicketConditionEvaluator {
  String getEvaluatorId();
  boolean evaluate(Delivery delivery);
  TicketPriority getDefaultTicketPriority();
}
