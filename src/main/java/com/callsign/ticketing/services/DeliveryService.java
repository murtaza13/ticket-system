package com.callsign.ticketing.services;

import com.callsign.ticketing.data.entities.Delivery;
import com.callsign.ticketing.data.transactions.DeliveryRecord;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Class encapsulating all the business logic related to Delivery
 */
public interface DeliveryService {
  /**
   * Fetches N incomplete Delivery records and obtains update lock over each.
   * This methods should always be called within a {@link org.hibernate.Transaction} context or else will error out.
   *
   * @param numOfRecordsToFetch number of Delivery records to fetch
   * @return {@link Set<DeliveryRecord>} Delivery records qualifying criteria
   */
  List<DeliveryRecord> getNUnDeliveredDeliveryRecordsWithUpdateLock(int numOfRecordsToFetch);

  /**
   * Fetches Delivery record for the given internal id.
   *
   * @param id identifier of the delivery record
   * @return {@link Optional<Delivery>}
   */
  Optional<Delivery> getById(long id);
}
