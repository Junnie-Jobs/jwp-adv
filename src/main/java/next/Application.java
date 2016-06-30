package next;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;
import org.springframework.context.annotation.Bean;

import next.domain.Board;
import next.domain.BoardRepository;
import next.domain.Deck;
import next.domain.DeckRepository;

@SpringBootApplication(exclude = ThymeleafAutoConfiguration.class)
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@Bean
	public CommandLineRunner demo(BoardRepository boardRepository, DeckRepository deckRepository) {
		return (args) -> {
			Board board1 = boardRepository.save(new Board("my first board"));
			deckRepository.save(new Deck(board1, "my first list1"));
			deckRepository.save(new Deck(board1, "my first list2"));
			deckRepository.save(new Deck(board1, "my first list3"));
			Board board2= boardRepository.save(new Board("my second board"));
			deckRepository.save(new Deck(board2, "my second list1"));
			deckRepository.save(new Deck(board2, "my second list2"));
		};
	}
}
