package com.callsign.ticketing.data.responses;

import java.io.Serializable;

/**
 * Class representing outgoing response of an authentication request
 */
public class JwtResponse implements Serializable {
  private final String jwtToken;

  public JwtResponse(String jwtToken) {
    this.jwtToken = jwtToken;
  }

  public String getToken() {
    return this.jwtToken;
  }
}