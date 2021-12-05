package com.callsign.ticketing.services;

import com.callsign.ticketing.data.converters.DeliveryAndDeliveryRecordMapper;
import com.callsign.ticketing.data.entities.Delivery;
import com.callsign.ticketing.data.enums.DeliveryStatus;
import com.callsign.ticketing.data.repositories.DeliveryRepository;
import com.callsign.ticketing.data.transactions.DeliveryRecord;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DeliveryServiceImpl implements DeliveryService {
  private final DeliveryRepository deliveryRepository;

  public DeliveryServiceImpl(DeliveryRepository deliveryRepository) {
    this.deliveryRepository = deliveryRepository;
  }

  /**
   * {@inheritDoc}
   */
  @Transactional(propagation = Propagation.MANDATORY)
  @Override
  public List<DeliveryRecord> getNUnDeliveredDeliveryRecordsWithUpdateLock(int numOfRecordsToFetch) {
    Set<Delivery> deliveries = deliveryRepository.fetchAllDeliveriesWithStatusNotEqual(
        DeliveryStatus.DELIVERED.name(), numOfRecordsToFetch);
    return deliveries.stream().map(DeliveryAndDeliveryRecordMapper::toDeliveryRecord).collect(Collectors.toList());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Optional<Delivery> getById(long id) {
    return deliveryRepository.findById(id);
  }
}
