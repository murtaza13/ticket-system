package com.callsign.ticketing.jobs;

import com.callsign.ticketing.data.entities.Delivery;
import com.callsign.ticketing.data.entities.Ticket;
import com.callsign.ticketing.data.enums.CustomerType;
import com.callsign.ticketing.data.enums.TicketPriority;
import com.callsign.ticketing.services.DeliveryService;
import com.callsign.ticketing.services.TicketService;
import com.callsign.ticketing.tickets.TicketConditionEvaluator;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class DeliveryMonitoringScheduledJob {
  private final DeliveryService deliveryService;
  private final TicketService ticketService;
  private final ApplicationContext applicationContext;

  public DeliveryMonitoringScheduledJob(DeliveryService deliveryService, TicketService ticketService,
                                        ApplicationContext applicationContext) {
    this.deliveryService = deliveryService;
    this.ticketService = ticketService;
    this.applicationContext = applicationContext;
  }

  @Transactional
  @Scheduled(fixedDelayString = "${ticketing.monitoring.job.run.ms:5000}")
  public void runJob(){
    Set<Delivery> deliveries = deliveryService.getNInCompleteDeliveries(100);
    Set<TicketConditionEvaluator> conditionEvaluators = new HashSet<>(applicationContext.getBeansOfType(
        TicketConditionEvaluator.class).values());

    for(Delivery delivery: deliveries){
      for(TicketConditionEvaluator ticketConditionEvaluator : conditionEvaluators) {
        if (shouldEvaluate(delivery.getTickets(), ticketConditionEvaluator.getEvaluatorId())) {
          boolean requiresANewTicket = ticketConditionEvaluator.evaluate(delivery);

          if (requiresANewTicket) {
            TicketPriority ticketPriority = overrideTicketPriorityIfApplicable(ticketConditionEvaluator.
                getDefaultTicketPriority(), delivery.getCustomerType());
            ticketService.createTicket(ticketConditionEvaluator.getEvaluatorId(), ticketPriority, delivery.getDeliveryId());
          }
        }
      }
    }
  }

  private boolean shouldEvaluate(Set<Ticket> tickets, String evaluatorId) {
    return !tickets.stream().map(Ticket::getReasonType).collect(Collectors.toSet()).contains(evaluatorId);
  }

  private TicketPriority overrideTicketPriorityIfApplicable(TicketPriority defaultTicketPriority, CustomerType customerType){
    if(defaultTicketPriority.getValue() <= customerType.getTicketPriority().getValue()){
      return defaultTicketPriority;
    }
    return customerType.getTicketPriority();
  }
}
