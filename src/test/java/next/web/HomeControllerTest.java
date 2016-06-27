package next.web;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import core.test.WebIntegrationTest;

@RunWith(SpringJUnit4ClassRunner.class)
public class HomeControllerTest extends WebIntegrationTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(HomeControllerTest.class);
	
	private RestTemplate template;
	
	@Before
	public void setup() {
		template = new TestRestTemplate();
	}
	
	@Test
	public void getHome() throws Exception {
		String result = template.getForObject(baseUrl(), String.class);
		LOGGER.debug("result : {}", result);
	}
}
