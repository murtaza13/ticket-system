package com.callsign.ticketing.services;

import com.callsign.ticketing.data.converters.businessanddata.DeliveryAndDeliveryRecordMapper;
import com.callsign.ticketing.data.entities.Delivery;
import com.callsign.ticketing.data.enums.DeliveryStatus;
import com.callsign.ticketing.data.repositories.DeliveryRepository;
import com.callsign.ticketing.data.transactions.businesslayer.DeliveryRecord;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DeliveryServiceImpl implements DeliveryService {
  private final DeliveryRepository deliveryRepository;

  public DeliveryServiceImpl(DeliveryRepository deliveryRepository) {
    this.deliveryRepository = deliveryRepository;
  }

  @Transactional(propagation = Propagation.MANDATORY)
  @Override
  public Set<DeliveryRecord> getNInCompleteDeliveries(int numberOfRecords) {
    Set<Delivery> deliveries = deliveryRepository.fetchAllDeliveriesWithStatusNotEqual(
        DeliveryStatus.DELIVERED.name(), numberOfRecords);
    return deliveries.stream().map(DeliveryAndDeliveryRecordMapper::toDeliveryRecord).collect(Collectors.toSet());
  }

  @Override
  public Optional<Delivery> getById(long id) {
    return deliveryRepository.findById(id);
  }
}
