package com.container.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashMap;
import java.util.Map;

import com.container.demo.web.DemoController;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class DemoApplicationTests {

	@LocalServerPort
	private int port;

	@Autowired
	private DemoController demoController;

	private RestTemplate restTemplate;

	@Autowired
	public DemoApplicationTests(RestTemplateBuilder restTemplateBuilder) {
		restTemplate = restTemplateBuilder.build();
	}

	@Test
	public void contextLoads() throws Exception {
		assertNotNull(demoController);
	}

	@Test
	public void responseMapShouldShow() throws Exception {
		ParameterizedTypeReference<HashMap<String, String>> responseType = 
            new ParameterizedTypeReference<HashMap<String, String>>() {};
		RequestEntity<Void> request = RequestEntity.get("http://localhost:" + port + "/container/demo/msg")
            .accept(MediaType.APPLICATION_JSON).build();
		Map<String, String> responseMap = restTemplate.exchange(request, responseType).getBody();
		assertNotNull(responseMap);
		assertEquals(responseMap.size(), 3);
	}

}
