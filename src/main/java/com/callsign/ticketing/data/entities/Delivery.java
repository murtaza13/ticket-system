package com.callsign.ticketing.data.entities;

import com.callsign.ticketing.data.enums.CustomerType;
import com.callsign.ticketing.data.enums.DeliveryStatus;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "delivery")
public class Delivery {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "delivery_id")
  private Long deliveryId;

  @Column(name = "customer_type", columnDefinition = "ENUM('VIP', 'LOYAL', 'NEW')")
  @Enumerated(EnumType.STRING)
  private CustomerType customerType;

  @Column(name = "delivery_status", columnDefinition = "ENUM('RECEIVED','PREPARING', 'PICKED_UP', 'DELIVERED')")
  @Enumerated(EnumType.STRING)
  private DeliveryStatus deliveryStatus;

  @Column(name = "current_distance_from_destination_in_metres")
  private Integer currentDistanceFromDestinationInMetres;

  @Column(name = "expected_delivery_time")
  private LocalDateTime expectedDeliveryTime;

  @Column(name = "time_to_reach_destination_in_seconds")
  private Integer timeToReachDestinationInSeconds;

  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @ManyToOne
  @JoinColumn(name = "restaurant_id", referencedColumnName = "restaurant_id")
  private Restaurant restaurant;

  @OneToMany(mappedBy = "delivery")
  private Set<Ticket> tickets = new HashSet<>();

  public Delivery() {
  }

  public Delivery(CustomerType customerType, DeliveryStatus deliveryStatus,
                  Integer currentDistanceFromDestinationInMetres, LocalDateTime expectedDeliveryTime,
                  Integer timeToReachDestinationInSeconds, LocalDateTime createdAt, Restaurant restaurant) {
    this.customerType = customerType;
    this.deliveryStatus = deliveryStatus;
    this.currentDistanceFromDestinationInMetres = currentDistanceFromDestinationInMetres;
    this.expectedDeliveryTime = expectedDeliveryTime;
    this.timeToReachDestinationInSeconds = timeToReachDestinationInSeconds;
    this.createdAt = createdAt;
    this.restaurant = restaurant;
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

  public Restaurant getRestaurant() {
    return restaurant;
  }

  public void setRestaurant(Restaurant restaurant) {
    this.restaurant = restaurant;
  }

  public Set<Ticket> getTickets() {
    return tickets;
  }

  public void setTickets(Set<Ticket> tickets) {
    this.tickets = tickets;
  }
}
