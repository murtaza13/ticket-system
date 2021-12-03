package com.callsign.ticketing.tickets;

import com.callsign.ticketing.data.entities.Delivery;
import com.callsign.ticketing.data.enums.TicketPriority;
import com.callsign.ticketing.data.transactions.businesslayer.DeliveryRecord;

public interface TicketConditionEvaluator {
  String getEvaluatorId();
  boolean evaluate(DeliveryRecord delivery);
  TicketPriority getDefaultTicketPriority();
}
