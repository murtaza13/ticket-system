package com.callsign.ticketing.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Class responsible for configuring job-scheduling in the context. By default scheduling is turned off during tests
 * to ensure consistent behaviour of tests.
 */
@Profile("!TEST")
@Configuration
@EnableScheduling
public class SchedulingConfiguration {
}
