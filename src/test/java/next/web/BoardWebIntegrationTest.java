package next.web;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.Collections;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import core.security.BasicAuthInterceptor;
import core.test.WebIntegrationTest;
import next.domain.board.Board;
import next.domain.board.BoardRepository;
import next.domain.user.SrelloUser;
import next.domain.user.SrelloUserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
public class BoardWebIntegrationTest extends WebIntegrationTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(BoardWebIntegrationTest.class);
	
	@Autowired
	private BoardRepository boardRepository;
	
	@Autowired
	private SrelloUserRepository userRepository;
	
	private RestTemplate template;
	
	private SrelloUser creator;
	
	@Before
	public void setup() {
		creator = userRepository.findByUserId("user");
		template = new RestTemplate();
		template.setInterceptors(Collections.singletonList(new BasicAuthInterceptor(creator.getUserId(), "password")));
	}
	
	@Test
	public void createObject() throws Exception {
		Board board = new Board(creator, "name");
		Board actual = template.postForObject(baseUrl() + "/boards", board, Board.class);
		LOGGER.debug("create board : {}", actual);
	}
	
	@Test
	public void createEntity() throws Exception {
		Board board = new Board(creator, "name");
		ResponseEntity<Board> responseEntity = template.postForEntity(baseUrl() + "/boards", board, Board.class);
		assertThat(responseEntity.getStatusCode(), is(HttpStatus.CREATED));
		HttpHeaders headers = responseEntity.getHeaders();
		LOGGER.debug("location headers : {}", headers.getLocation());
	}
	
	@Test
	public void delete() throws Exception {
		Board board = boardRepository.save(new Board(creator, "deleteBoard"));
		template.delete(baseUrl() + "/boards/" + board.getId());
		assertThat(boardRepository.findOne(board.getId()), is(nullValue()));
	}
	
	@Test(expected = HttpClientErrorException.class)
	public void delete404NotFound() throws Exception {
		String notFoundUrl = baseUrl() + "/boards/0";
		template.exchange(notFoundUrl, HttpMethod.DELETE, null, Board.class);
	}
	
	@Test
	public void deleteExchange() throws Exception {
		Board board = boardRepository.save(new Board(creator, "deleteBoard"));
		String noContentUrl = baseUrl() + "/boards/" + board.getId();
		ResponseEntity<Board> responseEntity = template.exchange(noContentUrl, HttpMethod.DELETE, null, Board.class);
		assertThat(responseEntity.getStatusCode(), is(HttpStatus.NO_CONTENT));
	}
}
