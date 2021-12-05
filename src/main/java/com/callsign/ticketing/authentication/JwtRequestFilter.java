package com.callsign.ticketing.authentication;

import com.callsign.ticketing.services.JwtUserDetailsService;
import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 * Class responsible for filtering http requests and validating the authorization token if present
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter {
  private static final Logger LOGGER = LoggerFactory.getLogger(JwtRequestFilter.class);
  private final JwtUserDetailsService userDetailsService;
  private final JwtTokenUtil jwtTokenUtil;

  public JwtRequestFilter(JwtUserDetailsService userDetailsService, JwtTokenUtil jwtTokenUtil) {
    this.userDetailsService = userDetailsService;
    this.jwtTokenUtil = jwtTokenUtil;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws ServletException, IOException {
    String username = null;
    Optional<String> jwtToken = getJwtTokenFromRequest(request);
    // JWT Token is in the form "Bearer token". Remove Bearer word and get
    // only the Token
    if (jwtToken.isPresent()) {
      String token = jwtToken.get();
      username = jwtTokenUtil.getUsernameFromToken(jwtToken.get());
      LOGGER.debug("Found a valid JWT Token. For user: {}", username);
      if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

        // if token is valid configure Spring Security to manually set
        // authentication
        if (jwtTokenUtil.validateToken(token, userDetails)) {

          UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
              userDetails, null, userDetails.getAuthorities());
          usernamePasswordAuthenticationToken
              .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
          // After setting the Authentication in the context, we specify
          // that the current user is authenticated. So it passes the
          // Spring Security Configurations successfully.
          SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
          LOGGER.debug("Successfully validate JWT token for user: {}", username);
        }
      }
    }
    chain.doFilter(request, response);
  }

  private static Optional<String> getJwtTokenFromRequest(HttpServletRequest request){
    String bearerToken = request.getHeader("Authorization");
    if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
      return Optional.of(bearerToken.substring(7));
    }
    return Optional.empty();
  }
}