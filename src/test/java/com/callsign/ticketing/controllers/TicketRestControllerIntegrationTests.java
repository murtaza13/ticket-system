package com.callsign.ticketing.controllers;

import com.callsign.ticketing.data.requests.JwtRequest;
import com.callsign.ticketing.data.enums.TicketPriority;
import com.callsign.ticketing.data.responses.TicketResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@TestPropertySource("file:src/test/resources/test-application.properties")
public class TicketRestControllerIntegrationTests {
  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
  private static final String GET_TICKETS = "/tickets";
  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private AuthenticationRestController authenticationRestController;

  @Transactional
  @Test
  public void testResponseWithZeroTickets() throws Exception {
    List<TicketResponse> responses = requestAndGetResponse();
    Assert.assertEquals(0, responses.size());
  }

  @Transactional
  @Test
  @Sql("file:src/test/resources/ResponseWithMultipleTickets.sql")
  public void testResponseWithMultipleTickets() throws Exception {
    List<TicketResponse> responses = requestAndGetResponse();

    Assert.assertEquals(3, responses.size());

    TicketResponse one = responses.get(0);
    Assert.assertEquals(Long.valueOf(1), one.getTicketId());
    Assert.assertEquals(Long.valueOf(1), one.getDeliveryId());
    Assert.assertEquals(TicketPriority.HIGH, one.getTicketPriority());

    TicketResponse two = responses.get(1);
    Assert.assertEquals(Long.valueOf(2), two.getTicketId());
    Assert.assertEquals(Long.valueOf(1), two.getDeliveryId());
    Assert.assertEquals(TicketPriority.MEDIUM, two.getTicketPriority());

    TicketResponse three = responses.get(2);
    Assert.assertEquals(Long.valueOf(3), three.getTicketId());
    Assert.assertEquals(Long.valueOf(1), three.getDeliveryId());
    Assert.assertEquals(TicketPriority.LOW, three.getTicketPriority());
  }

  @Transactional
  @Test
  @Sql("file:src/test/resources/ResponseWithMultipeTicketsSorted.sql")
  public void testResponseWithMultipleTicketsSorted() throws Exception {
    List<TicketResponse> responses = requestAndGetResponse();

    Assert.assertEquals(4, responses.size());

    Assert.assertEquals(TicketPriority.HIGH, responses.get(0).getTicketPriority());
    Assert.assertEquals(TicketPriority.HIGH, responses.get(1).getTicketPriority());
    Assert.assertEquals(TicketPriority.MEDIUM, responses.get(2).getTicketPriority());
    Assert.assertEquals(TicketPriority.LOW, responses.get(3).getTicketPriority());
  }

  private List<TicketResponse> requestAndGetResponse() throws Exception {
    String authenticatedJwtToken = authenticationRestController.
        createAuthenticationToken(new JwtRequest("murtaza", "murtaza")).getBody().getToken();
    MvcResult result = mockMvc.perform(
        get(GET_TICKETS)
            .header("Authorization", "Bearer " + authenticatedJwtToken)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andReturn();

    return OBJECT_MAPPER.readValue(result.getResponse().getContentAsString(),
        new TypeReference<List<TicketResponse>>() {
        });
  }
}
