package com.callsign.ticketing.authentication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

/**
 * Class responsible for providing single interface for all authentication exceptions. All exceptions are caught during
 * authentication are forwarded here. Sets the 401 http status code and relevant error message.
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {
  private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationEntryPoint.class);

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response,
                       AuthenticationException authException) throws IOException {
    LOGGER.error("Unable to authenticate user for request: " + request.getRequestURI(), authException);
    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User is unauthorized. Reason: " + authException.getMessage());
  }
}