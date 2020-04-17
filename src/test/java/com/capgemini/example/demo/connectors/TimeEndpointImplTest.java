package com.capgemini.example.demo.connectors;


import com.capgemini.example.demo.models.WorldTimeAPIResponseModel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(SpringRunner.class)
public class TimeEndpointImplTest {

    private TimeEndpoint unitUnderTest;

    @MockBean
    RestTemplate mockRestTemplate;

    private final String mockUrl = "http://example.com/mock-url";

    @Before
    public void setup() {
        initMocks(this);
        unitUnderTest = new TimeEndpointImpl();
        ReflectionTestUtils.setField(unitUnderTest, "restTemplate", mockRestTemplate);
        ReflectionTestUtils.setField(unitUnderTest, "configuredApiEndpointUrl", mockUrl);
        when(mockRestTemplate.getForObject(mockUrl, WorldTimeAPIResponseModel.class)).thenReturn(getMockAPISuccessResponse());

    }



    @Test
    public void getTimeTest()
    {
        WorldTimeAPIResponseModel actual = unitUnderTest.getTimeFromEndpoint();
        assertEquals("client ip", "192.168.0.1", actual.getClient_ip());
        assertEquals("unix time", 1587026667l, actual.getUnixtime());

    }

    private WorldTimeAPIResponseModel getMockAPISuccessResponse()
    {
        WorldTimeAPIResponseModel mockApiResponse = new WorldTimeAPIResponseModel();
        mockApiResponse.setClient_ip("192.168.0.1");
        mockApiResponse.setUnixtime(1587026667l);
        return mockApiResponse;
    }
}
