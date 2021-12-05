package com.callsign.ticketing.controllers;

import com.callsign.ticketing.data.requests.JwtRequest;
import com.callsign.ticketing.data.responses.JwtResponse;
import com.callsign.ticketing.authentication.JwtTokenUtil;
import com.callsign.ticketing.services.JwtUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

/**
 * Class responsible for providing end-users with endpoint to authenticate and obtain jwt token for futher application access
 */
@RestController
public class AuthenticationRestController {
  private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationRestController.class);
  private final AuthenticationManager authenticationManager;
  private final JwtTokenUtil jwtTokenUtil;
  private final JwtUserDetailsService userDetailsService;

  public AuthenticationRestController(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil,
                                      JwtUserDetailsService userDetailsService) {
    this.authenticationManager = authenticationManager;
    this.jwtTokenUtil = jwtTokenUtil;
    this.userDetailsService = userDetailsService;
  }

  /**
   * Maps to {BASE_URL}/authenticate URI. Authenticates the user on the basis of given username & password. Returns a
   * valid Http 200 response, along with a valid Jwt token if provided user details are authentic. In case of any unknown
   * exception whilst processing the request returns an adequate Http 500 response. Any authentication failures are caught
   * by spring automatically and a Http 401 response is sent.
   *
   * @param authenticationRequest {@link JwtRequest} instance containing username & password
   * @return {@link ResponseEntity} wrapping the authentication token
   */
  @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
  public ResponseEntity<JwtResponse> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) {
    String token = "";
    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
        authenticationRequest.getPassword()));
    try {
      final UserDetails userDetails = userDetailsService
          .loadUserByUsername(authenticationRequest.getUsername());

      token = jwtTokenUtil.generateToken(userDetails);
      LOGGER.debug("Successfully fetched a valid JWT token for user: {}", authenticationRequest.getUsername());
      return ResponseEntity.ok(new JwtResponse(token));
    } catch (Exception e) {
      LOGGER.error("Unable to authenticate user: {} for the given credentials.", authenticationRequest.getUsername());
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
