package com.capgemini.example.demo;

import com.capgemini.example.demo.connectors.TimeEndpoint;
import com.capgemini.example.demo.models.DateTimeModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.reactive.function.BodyInserters;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest (webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, properties = { "management.server.port=" , "server.port=8081"})
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@AutoConfigureWebTestClient
public class GetTimeITest {

    final static String WIREMOCK_HOST = "localhost";
    final static String WIREMOCK_PORT = "8089";
    final static String MOCK_ENDPOINT_URL = "/api/ip";

    @Autowired
    TimeEndpoint endpoint;

    @Autowired
    MockMvc mvc;

    @Autowired
    private WebTestClient webTestClient;

    @Before
    public void setup() {
        ReflectionTestUtils.setField(
                endpoint,
                "configuredApiEndpointUrl",
                "http://" + WIREMOCK_HOST + ":" + WIREMOCK_PORT + "/" + MOCK_ENDPOINT_URL);
    }

    @ClassRule
    public static WireMockClassRule wireMockRule = new WireMockClassRule(
            WireMockConfiguration.options().port(Integer.parseInt(WIREMOCK_PORT)).containerThreads(15));

    @BeforeClass
    public static void createWiremockStubs() {
        stubFor(get(urlEqualTo(MOCK_ENDPOINT_URL))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody("{\"week_number\":16,\"utc_offset\":\"+01:00\",\"utc_datetime\":\"" +
                                "2020-04-16T08:44:27.895355+00:00\",\"unixtime\":1587026667,\"timezone\":" +
                                "\"Europe/London\",\"raw_offset\":0,\"dst_until\":\"2020-10-25T01:00:00+00:00\"," +
                                "\"dst_offset\":3600,\"dst_from\":\"2020-03-29T01:00:00+00:00\",\"dst\":true," +
                                "\"day_of_year\":107,\"day_of_week\":4,\"datetime\":\"2020-04-16T09:44:27.895355+01:00"+
                                "\",\"client_ip\":\"51.9.125.137\",\"abbreviation\":\"BST\"}")));



    }

    @Test
    public void testForSuccess() throws Exception {
        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.get("http://localhost:8081/what-time-is-it"));
        resultActions.andExpect(status().isOk());

        MvcResult mvcResult = resultActions.andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString();
        DateTimeModel actualResult = new ObjectMapper().readValue(responseBody, DateTimeModel.class);

        assertEquals("Expected and actual times do not match",
                "Thu Apr 16 09:44:27 BST 2020",
                actualResult.getDateTime());

        assertEquals("Expected and actual ip addresses do not match",
                "51.9.125.137",
                actualResult.getIpAddress());

    }

    @Test
    public void testForAysncSuccess() throws Exception {
        String responseBody = webTestClient.get()
                .uri("http://localhost:8081/what-time-is-it-async")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .returnResult(String.class)
                .getResponseBody()
                .blockFirst();

        DateTimeModel actualResult = new ObjectMapper().readValue(responseBody, DateTimeModel.class);

        assertEquals("Expected and actual times do not match",
                "Thu Apr 16 09:44:27 BST 2020",
                actualResult.getDateTime());

        assertEquals("Expected and actual ip addresses do not match",
                "51.9.125.137",
                actualResult.getIpAddress());


    }

}
