package com.callsign.ticketing.data.transactions;

import com.callsign.ticketing.data.enums.CustomerType;
import com.callsign.ticketing.data.enums.DeliveryStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DeliveryRecord {
  private Long deliveryId;
  private CustomerType customerType;
  private DeliveryStatus deliveryStatus;
  private Integer currentDistanceFromDestinationInMetres;
  private LocalDateTime expectedDeliveryTime;
  private Integer timeToReachDestinationInSeconds;
  private LocalDateTime createdAt;
  private int restaurantsMeanTimetoPrepareFood;
  private List<TicketRecord> tickets = new ArrayList<>();

  public DeliveryRecord() {
  }

  //Added for better testability
  public DeliveryRecord(Long deliveryId) {
    this.deliveryId = deliveryId;
  }

  public DeliveryRecord(Long deliveryId, CustomerType customerType, DeliveryStatus deliveryStatus,
                        Integer currentDistanceFromDestinationInMetres, LocalDateTime expectedDeliveryTime,
                        Integer timeToReachDestinationInSeconds, LocalDateTime createdAt,
                        int restaurantsMeanTimetoPrepareFood, List<TicketRecord> tickets) {
    this.deliveryId = deliveryId;
    this.customerType = customerType;
    this.deliveryStatus = deliveryStatus;
    this.currentDistanceFromDestinationInMetres = currentDistanceFromDestinationInMetres;
    this.expectedDeliveryTime = expectedDeliveryTime;
    this.timeToReachDestinationInSeconds = timeToReachDestinationInSeconds;
    this.createdAt = createdAt;
    this.restaurantsMeanTimetoPrepareFood = restaurantsMeanTimetoPrepareFood;
    this.tickets = tickets;
  }

  public Long getDeliveryId() {
    return deliveryId;
  }

  public void setDeliveryId(Long deliveryId) {
    this.deliveryId = deliveryId;
  }

  public CustomerType getCustomerType() {
    return customerType;
  }

  public void setCustomerType(CustomerType customerType) {
    this.customerType = customerType;
  }

  public DeliveryStatus getDeliveryStatus() {
    return deliveryStatus;
  }

  public void setDeliveryStatus(DeliveryStatus deliveryStatus) {
    this.deliveryStatus = deliveryStatus;
  }

  public Integer getCurrentDistanceFromDestinationInMetres() {
    return currentDistanceFromDestinationInMetres;
  }

  public void setCurrentDistanceFromDestinationInMetres(Integer currentDistanceFromDestinationInMetres) {
    this.currentDistanceFromDestinationInMetres = currentDistanceFromDestinationInMetres;
  }

  public LocalDateTime getExpectedDeliveryTime() {
    return expectedDeliveryTime;
  }

  public void setExpectedDeliveryTime(LocalDateTime expectedDeliveryTime) {
    this.expectedDeliveryTime = expectedDeliveryTime;
  }

  public Integer getTimeToReachDestinationInSeconds() {
    return timeToReachDestinationInSeconds;
  }

  public void setTimeToReachDestinationInSeconds(Integer timeToReachDestinationInSeconds) {
    this.timeToReachDestinationInSeconds = timeToReachDestinationInSeconds;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public int getRestaurantsMeanTimetoPrepareFood() {
    return restaurantsMeanTimetoPrepareFood;
  }

  public void setRestaurantsMeanTimetoPrepareFood(int restaurantsMeanTimetoPrepareFood) {
    this.restaurantsMeanTimetoPrepareFood = restaurantsMeanTimetoPrepareFood;
  }

  public List<TicketRecord> getTickets() {
    return tickets;
  }

  public void setTickets(List<TicketRecord> tickets) {
    this.tickets = tickets;
  }
}
