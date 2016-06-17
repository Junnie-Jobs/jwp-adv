package next.domain;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import core.test.IntegrationTest;

public class CardRepositoryTest extends IntegrationTest {
	private static final Logger log = LoggerFactory.getLogger(CardRepositoryTest.class);
	
	@Autowired
	private CardRepository cardRepository;
	
	@Test
	public void crud() throws Exception {
		Card card1 = new Card("title1", "description1");
		Card card2 = new Card("title2", "description2");
		cardRepository.save(card1);
		cardRepository.save(card2);
		
		Iterable<Card> cards = cardRepository.findAll();
		for (Card c : cards) {
			log.debug("card : {}", c);
		}
	}
}
