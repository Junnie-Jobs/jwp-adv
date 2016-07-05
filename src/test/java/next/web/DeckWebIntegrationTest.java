package next.web;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Collections;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import core.security.BasicAuthInterceptor;
import core.test.WebIntegrationTest;
import next.domain.Board;
import next.domain.BoardRepository;
import next.domain.Deck;
import next.domain.DeckRepository;
import next.domain.User;
import next.domain.UserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
public class DeckWebIntegrationTest extends WebIntegrationTest {
	@Autowired
	private BoardRepository boardRepository;
	
	@Autowired
	private DeckRepository deckRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	private RestTemplate template;
	
	private User creator;
	
	@Before
	public void setup() {
		creator = userRepository.findByName("user");
		template = new RestTemplate();
		template.setInterceptors(Collections.singletonList(new BasicAuthInterceptor(creator.getName(), "password")));
	}
	
	@Test
	public void create() throws Exception {
		Board board = boardRepository.save(new Board(creator, "my board"));
		String url = baseUrl() + String.format("/boards/%d/decks", board.getId());
		ResponseEntity<Void> responseEntity = template.postForEntity(url, "my deck", Void.class);
		assertThat(responseEntity.getStatusCode(), is(HttpStatus.CREATED));
	}
	
	@Test
	public void show() throws Exception {
		Board board = boardRepository.save(new Board(creator, "my board"));
		Deck deck = deckRepository.save(new Deck(board, "my deck"));
		String url = baseUrl() + String.format("/boards/%d/decks/%d", board.getId(), deck.getId());
		ResponseEntity<Deck> responseEntity = template.getForEntity(url, Deck.class);
		assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
		assertThat(responseEntity.getBody(), is(deck));
	}
}
