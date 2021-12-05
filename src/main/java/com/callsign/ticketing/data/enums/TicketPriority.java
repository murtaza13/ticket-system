package com.callsign.ticketing.data.enums;

/**
 * Representation of possible priorities of tickets
 */
public enum TicketPriority {
  HIGH(1),
  MEDIUM(2),
  LOW(3);

  final int value;

  TicketPriority(int value) {
    this.value = value;
  }

  public int getValue() {
    return value;
  }
}
