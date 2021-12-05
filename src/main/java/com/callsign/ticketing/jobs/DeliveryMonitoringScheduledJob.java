package com.callsign.ticketing.jobs;

import com.callsign.ticketing.data.enums.CustomerType;
import com.callsign.ticketing.data.enums.TicketPriority;
import com.callsign.ticketing.data.transactions.DeliveryRecord;
import com.callsign.ticketing.data.transactions.TicketRecord;
import com.callsign.ticketing.services.DeliveryService;
import com.callsign.ticketing.services.TicketService;
import com.callsign.ticketing.tickets.TicketConditionEvaluator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Class encapsulating a scheduled job to periodically monitor delivery records, and creating tickets wherever required.
 */
@Component
public class DeliveryMonitoringScheduledJob {
  private static final Logger LOGGER = LoggerFactory.getLogger(DeliveryMonitoringScheduledJob.class);
  private final DeliveryService deliveryService;
  private final TicketService ticketService;
  private final ApplicationContext applicationContext;

  public DeliveryMonitoringScheduledJob(DeliveryService deliveryService, TicketService ticketService,
                                        ApplicationContext applicationContext) {
    this.deliveryService = deliveryService;
    this.ticketService = ticketService;
    this.applicationContext = applicationContext;
  }

  /**
   * Scheduled job that runs periodically after every {ticketing.monitoring.job.run.ms} seconds. If the property is not
   * defined, the job runs after 5 seconds by default. Two instances of this job never will run together. Fetches all
   * deliveries with status not equal to {@link com.callsign.ticketing.data.enums.DeliveryStatus DELIVERED}. Iterates
   * over each delivery record, evaluating across all available {@link TicketConditionEvaluator}. If the job qualifies
   * for any of the evaluator condition, and there does not already exist a ticket for this condition associated to the
   * delivery being processed, creates a new ticket, else ignores.
   */
  @Transactional
  @Scheduled(fixedDelayString = "${ticketing.monitoring.job.run.ms:5000}")
  public void runJob() {
    List<DeliveryRecord> deliveries = deliveryService.getNUnDeliveredDeliveryRecordsWithUpdateLock(100);
    LOGGER.debug("Picked up {} deliveries for evaluation process.", deliveries.size());
    List<TicketConditionEvaluator> conditionEvaluators = new ArrayList<>(applicationContext.getBeansOfType(
        TicketConditionEvaluator.class).values());
    LOGGER.debug("Found {} TicketConditionEvaluators to evaluate deliveries against.", conditionEvaluators.size());

    for (DeliveryRecord delivery : deliveries) {
      for (TicketConditionEvaluator ticketConditionEvaluator : conditionEvaluators) {
        try {
          boolean shouldEvaluate = shouldEvaluate(delivery.getTickets(), ticketConditionEvaluator.getReasonId());
          if(!shouldEvaluate){
            LOGGER.debug("There already exists a ticket for delivery with id: {} for reason: {}",
                delivery.getDeliveryId(), ticketConditionEvaluator.getReasonId());
          }
          else {
            boolean requiresANewTicket = ticketConditionEvaluator.evaluate(delivery);

            if (requiresANewTicket) {
              LOGGER.debug("Delivery record with id {} has been evaluated to have been requiring a new ticket for reason:" +
                  " {}", delivery.getDeliveryId(), ticketConditionEvaluator.getReasonId());
              TicketPriority ticketPriority = overrideTicketPriorityIfApplicable(ticketConditionEvaluator.
                  getDefaultTicketPriority(), delivery.getCustomerType());
              TicketRecord ticketRecord = ticketService.createTicket(ticketConditionEvaluator.getReasonId(),
                  ticketPriority, delivery.getDeliveryId());
              LOGGER.info("Successfully created a ticket with id: {} for delivery with id: {}, due to the reason: {}",
                  ticketRecord.getTicketId(), ticketRecord.getDeliveryRecord().getDeliveryId(), ticketRecord.getReason());
            }
          }
        }catch (Exception e){
          LOGGER.error("Unable to completely process delivery record with id {} against condition evaluator with reason: {}." +
              " Message: {}", delivery.getDeliveryId(), ticketConditionEvaluator.getReasonId(), e.getMessage());
        }
      }
    }
  }

  private boolean shouldEvaluate(List<TicketRecord> tickets, String evaluatorId) {
    return !tickets.stream().map(TicketRecord::getReason).collect(Collectors.toSet()).contains(evaluatorId);
  }

  private TicketPriority overrideTicketPriorityIfApplicable(TicketPriority defaultTicketPriority, CustomerType customerType) {
    if (defaultTicketPriority.getValue() <= customerType.getTicketPriority().getValue()) {
      return defaultTicketPriority;
    }
    return customerType.getTicketPriority();
  }
}
