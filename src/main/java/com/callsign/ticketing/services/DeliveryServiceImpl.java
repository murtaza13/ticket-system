package com.callsign.ticketing.services;

import com.callsign.ticketing.data.entities.Delivery;
import com.callsign.ticketing.data.enums.DeliveryStatus;
import com.callsign.ticketing.data.repositories.DeliveryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Service
public class DeliveryServiceImpl implements DeliveryService {
  private final DeliveryRepository deliveryRepository;

  public DeliveryServiceImpl(DeliveryRepository deliveryRepository) {
    this.deliveryRepository = deliveryRepository;
  }

  @Transactional(propagation = Propagation.MANDATORY)
  @Override
  public Set<Delivery> getNInCompleteDeliveries(int numberOfRecords) {
    return deliveryRepository.fetchAllDeliveriesWithStatusNotEqual(DeliveryStatus.DELIVERED.name(), numberOfRecords);
  }

  @Override
  public Optional<Delivery> getById(long id) {
    return deliveryRepository.findById(id);
  }
}
