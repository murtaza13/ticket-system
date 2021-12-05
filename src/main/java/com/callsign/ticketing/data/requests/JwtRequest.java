package com.callsign.ticketing.data.requests;

import java.io.Serializable;

/**
 * Class representing incoming request for authentication
 */
public class JwtRequest implements Serializable {

  private String username;
  private String password;

  public JwtRequest() {

  }

  public JwtRequest(String username, String password) {
    this.setUsername(username);
    this.setPassword(password);
  }

  public String getUsername() {
    return this.username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}