package next.web;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import core.test.WebIntegrationTest;
import next.domain.Board;

@RunWith(SpringJUnit4ClassRunner.class)
public class BoardApiTest extends WebIntegrationTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(BoardApiTest.class);
	
	private RestTemplate template;
	
	@Before
	public void setup() {
		template = new RestTemplate();
	}
	
	@Test
	public void createObject() throws Exception {
		Board board = new Board("name");
		Board actual = template.postForObject(baseUrl() + "/boards", board, Board.class);
		LOGGER.debug("create board : {}", actual);
	}
	
	@Test
	public void createEntity() throws Exception {
		Board board = new Board("name");
		ResponseEntity<Board> responseEntity = template.postForEntity(baseUrl() + "/boards", board, Board.class);
		assertThat(responseEntity.getStatusCode(), is(HttpStatus.CREATED));
		LOGGER.debug("create board : {}", responseEntity.getBody());
	}
}
