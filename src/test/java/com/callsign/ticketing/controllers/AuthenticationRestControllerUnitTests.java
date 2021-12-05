package com.callsign.ticketing.controllers;

import com.callsign.ticketing.data.requests.JwtRequest;
import com.callsign.ticketing.authentication.JwtTokenUtil;
import com.callsign.ticketing.data.responses.JwtResponse;
import com.callsign.ticketing.services.JwtUserDetailsService;
import mockit.Expectations;
import mockit.Mocked;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

public class AuthenticationRestControllerUnitTests {
  @Test
  public void testReturnsJwtGeneratedTokenAfterSuccess(@Mocked AuthenticationManager authenticationManager,
                                                        @Mocked JwtTokenUtil jwtTokenUtil,
                                                        @Mocked JwtUserDetailsService userDetailsService){
    final String validDummyToken = "this is a valid dummy token";
    new Expectations(){{
      authenticationManager.authenticate((Authentication) any);
      result = any;
      userDetailsService.loadUserByUsername(anyString);
      result = any;
      jwtTokenUtil.generateToken((UserDetails) any);
      result = validDummyToken;
    }};

    AuthenticationRestController authenticationRestController = new AuthenticationRestController(
        authenticationManager,
        jwtTokenUtil,
        userDetailsService
    );

    ResponseEntity<JwtResponse> response = authenticationRestController.
        createAuthenticationToken(new JwtRequest("", ""));
    Assert.assertEquals(HttpStatus.OK,response.getStatusCode());
    Assert.assertEquals(validDummyToken, response.getBody().getToken());
  }

  @Test
  public void testInternalServerErrorIfUserLoadFails(@Mocked AuthenticationManager authenticationManager,
                                                    @Mocked JwtTokenUtil jwtTokenUtil,
                                                    @Mocked JwtUserDetailsService userDetailsService){
    new Expectations(){{
      authenticationManager.authenticate((Authentication) any);
      result = any;
      userDetailsService.loadUserByUsername(anyString);
      result = new Exception();
    }};

    AuthenticationRestController authenticationRestController = new AuthenticationRestController(
        authenticationManager,
        jwtTokenUtil,
        userDetailsService
    );

    ResponseEntity<JwtResponse> response = authenticationRestController.
        createAuthenticationToken(new JwtRequest("", ""));
    Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
  }

  @Test
  public void testInternalServerErrorIfTokenGenerationFails(@Mocked AuthenticationManager authenticationManager,
                                                            @Mocked JwtTokenUtil jwtTokenUtil,
                                                            @Mocked JwtUserDetailsService userDetailsService){
    new Expectations(){{
      authenticationManager.authenticate((Authentication) any);
      result = any;
      userDetailsService.loadUserByUsername(anyString);
      result = any;
      jwtTokenUtil.generateToken((UserDetails) any);
      result = new Exception();
    }};

    AuthenticationRestController authenticationRestController = new AuthenticationRestController(
        authenticationManager,
        jwtTokenUtil,
        userDetailsService
    );

    ResponseEntity<JwtResponse> response = authenticationRestController.
        createAuthenticationToken(new JwtRequest("", ""));
    Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
  }
}
