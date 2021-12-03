package com.callsign.ticketing.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;

@Profile("!TEST")
@Configuration
@EnableScheduling
public class SchedulingConfiguration {
}
