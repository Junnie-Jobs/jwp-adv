package next;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import next.domain.board.Board;
import next.domain.board.BoardRepository;
import next.domain.board.Card;
import next.domain.board.CardRepository;
import next.domain.board.Deck;
import next.domain.board.DeckRepository;
import next.domain.user.SrelloUser;
import next.domain.user.SrelloUserRepository;

@SpringBootApplication(exclude = ThymeleafAutoConfiguration.class)
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@Bean
	public CommandLineRunner demo(
			SrelloUserRepository userRepository,
			BoardRepository boardRepository, 
			DeckRepository deckRepository,
			CardRepository cardRepository,
			PasswordEncoder passwordEncoder) {
		return (args) -> {
			SrelloUser user = new SrelloUser("user", "javajigi@srello.xyz", passwordEncoder.encode("password"));
			userRepository.save(user);
			Board board1 = boardRepository.save(new Board(user, "my first board"));
			Deck deck1 = new Deck(board1, "my first list1");
			deckRepository.save(deck1);
			cardRepository.save(new Card(deck1, "card title1", "card description"));
			cardRepository.save(new Card(deck1, "card title2", "card description"));
			deckRepository.save(new Deck(board1, "my first list2"));
			deckRepository.save(new Deck(board1, "my first list3"));
			Board board2= boardRepository.save(new Board(user, "my second board"));
			deckRepository.save(new Deck(board2, "my second list1"));
			deckRepository.save(new Deck(board2, "my second list2"));
		};
	}
}
