package com.callsign.ticketing.services;

import com.callsign.ticketing.data.entities.Delivery;
import com.callsign.ticketing.data.transactions.businesslayer.DeliveryRecord;

import java.util.Optional;
import java.util.Set;

public interface DeliveryService {
  Set<DeliveryRecord> getNInCompleteDeliveries(int i);
  Optional<Delivery> getById(long id);
}
