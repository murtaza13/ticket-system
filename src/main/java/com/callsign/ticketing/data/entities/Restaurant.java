package com.callsign.ticketing.data.entities;

import javax.persistence.*;

@Entity
@Table(name = "restaurant")
public class Restaurant {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "restaurant_id")
  private Integer restaurantId;

  @Column(name = "mean_time_to_prepare_food_in_seconds")
  private Integer meanTimeToPrepareFoodInSeconds;

  public Restaurant() {
  }

  public Restaurant(Integer meanTimeToPrepareFoodInSeconds) {
    this.meanTimeToPrepareFoodInSeconds = meanTimeToPrepareFoodInSeconds;
  }

  public Integer getRestaurantId() {
    return restaurantId;
  }

  public void setRestaurantId(Integer restaurantId) {
    this.restaurantId = restaurantId;
  }

  public Integer getMeanTimeToPrepareFoodInSeconds() {
    return meanTimeToPrepareFoodInSeconds;
  }

  public void setMeanTimeToPrepareFoodInSeconds(Integer meanTimeToPrepareFoodInSeconds) {
    this.meanTimeToPrepareFoodInSeconds = meanTimeToPrepareFoodInSeconds;
  }
}
