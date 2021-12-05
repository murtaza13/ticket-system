package com.callsign.ticketing.data.converters;

import com.callsign.ticketing.data.entities.Delivery;
import com.callsign.ticketing.data.entities.Ticket;
import com.callsign.ticketing.data.transactions.DeliveryRecord;
import com.callsign.ticketing.data.transactions.TicketRecord;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Helper class responsible for conversion between Delivery Data Object and Delivery Business Object
 */
public class DeliveryAndDeliveryRecordMapper {
  /**
   * Converts the given data layer representation of a Delivery to the business layer representation.
   *
   * @param delivery the entity to convert
   * @return {@link DeliveryRecord} business layer representation of a Delivery
   */
  public static DeliveryRecord toDeliveryRecord(Delivery delivery) {
    DeliveryRecord deliveryRecord = new DeliveryRecord();
    deliveryRecord.setDeliveryId(delivery.getDeliveryId());
    deliveryRecord.setCustomerType(delivery.getCustomerType());
    deliveryRecord.setDeliveryStatus(delivery.getDeliveryStatus());
    deliveryRecord.setCreatedAt(delivery.getCreatedAt());
    deliveryRecord.setCurrentDistanceFromDestinationInMetres(delivery.getCurrentDistanceFromDestinationInMetres());
    deliveryRecord.setExpectedDeliveryTime(delivery.getExpectedDeliveryTime());
    deliveryRecord.setRestaurantsMeanTimetoPrepareFood(delivery.getRestaurant().getMeanTimeToPrepareFoodInSeconds());
    deliveryRecord.setTimeToReachDestinationInSeconds(delivery.getTimeToReachDestinationInSeconds());
    deliveryRecord.setTickets(getTicketRecords(delivery.getTickets()));
    return deliveryRecord;
  }

  private static List<TicketRecord> getTicketRecords(List<Ticket> tickets) {
    return tickets.stream().map(x -> new TicketRecord(x.getTicketId(), x.getReasonType(), x.getTicketPriority())).
        collect(Collectors.toList());
  }
}
