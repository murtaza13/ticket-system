package com.callsign.ticketing.controllers;

import com.callsign.ticketing.data.entities.Delivery;
import com.callsign.ticketing.data.entities.Restaurant;
import com.callsign.ticketing.data.repositories.DeliveryRepository;
import com.callsign.ticketing.data.repositories.RestaurantRepository;
import com.callsign.ticketing.data.repositories.TicketRepository;
import com.callsign.ticketing.data.enums.CustomerType;
import com.callsign.ticketing.data.enums.DeliveryStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class TestController {
  private final DeliveryRepository deliveryRepository;
  private final RestaurantRepository restaurantRepository;
  private final TicketRepository ticketRepository;

  public TestController(DeliveryRepository deliveryRepository, RestaurantRepository restaurantRepository,
                        TicketRepository ticketRepository) {
    this.deliveryRepository = deliveryRepository;
    this.restaurantRepository = restaurantRepository;
    this.ticketRepository = ticketRepository;
  }

  @RequestMapping("/status")
  public String testme(){
    return "I am actually working!";
  }

  @RequestMapping("/test")
  public void tester() {
    Restaurant restaurant = new Restaurant(30);
    restaurantRepository.save(restaurant);

    Delivery delivery = new Delivery(
        CustomerType.VIP,
        DeliveryStatus.PREPARING,
        200,
        LocalDateTime.now().plusSeconds(5000),
        5000,
        LocalDateTime.now(),
        restaurant
    );
    deliveryRepository.save(delivery);

    Delivery delivery1 = new Delivery(CustomerType.LOYAL,
        DeliveryStatus.RECEIVED,
        10,
        LocalDateTime.now().plusSeconds(50),
        20000,
        LocalDateTime.now(),
        restaurant);

    deliveryRepository.save(delivery1);


    Delivery delivery2 = new Delivery(CustomerType.LOYAL,
        DeliveryStatus.DELIVERED,
        10,
        LocalDateTime.now().plusSeconds(50),
        20000,
        LocalDateTime.now(),
        restaurant);

    deliveryRepository.save(delivery2);

    String s = "";
  }
}
