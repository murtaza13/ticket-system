package com.callsign.ticketing.services;

import com.callsign.ticketing.data.entities.UserInfo;
import com.callsign.ticketing.data.repositories.UserInfoRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;

@Service
public class JwtUserDetailsService implements UserDetailsService {
  private final UserInfoRepository userInfoRepository;

  public JwtUserDetailsService(UserInfoRepository userInfoRepository) {
    this.userInfoRepository = userInfoRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    UserInfo userInfo = userInfoRepository.findByUserName(username)
        .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    return new User(userInfo.getUserName(), userInfo.getPassword(), Collections.emptyList());
  }
}