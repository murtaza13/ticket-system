package com.callsign.ticketing.data.converters;

public interface EntityTransactionConverter<E, T> {
  /**
   * @param entity
   * @return
   */
  T convert(E entity);
}
