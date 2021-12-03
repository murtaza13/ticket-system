package com.callsign.ticketing.data.repositories;

import com.callsign.ticketing.data.entities.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {
}
