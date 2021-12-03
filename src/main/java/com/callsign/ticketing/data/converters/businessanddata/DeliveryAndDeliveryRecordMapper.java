package com.callsign.ticketing.data.converters.businessanddata;

import com.callsign.ticketing.data.entities.Delivery;
import com.callsign.ticketing.data.entities.Ticket;
import com.callsign.ticketing.data.transactions.businesslayer.DeliveryRecord;
import com.callsign.ticketing.data.transactions.businesslayer.TicketRecord;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DeliveryAndDeliveryRecordMapper {
  public static DeliveryRecord toDeliveryRecord(Delivery delivery){
    DeliveryRecord deliveryRecord =  new DeliveryRecord();
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

  private static List<TicketRecord> getTicketRecords(List<Ticket> tickets){
    return tickets.stream().map(x -> new TicketRecord(x.getTicketId(), x.getReasonType(), x.getTicketPriority())).
        collect(Collectors.toList());
  }
}
