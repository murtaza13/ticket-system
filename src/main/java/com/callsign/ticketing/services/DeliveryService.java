package com.callsign.ticketing.services;

import com.callsign.ticketing.data.entities.Delivery;

import java.util.Optional;
import java.util.Set;

public interface DeliveryService {
  Set<Delivery> getNInCompleteDeliveries(int i);
  Optional<Delivery> getById(long id);
}
