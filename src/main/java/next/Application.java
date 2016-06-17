package next;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;
import org.springframework.context.annotation.Bean;

import next.domain.Card;
import next.domain.CardRepository;

@SpringBootApplication(exclude = ThymeleafAutoConfiguration.class)
public class Application {
	private static final Logger log = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@Bean
	public CommandLineRunner demo(CardRepository repository) {
		return (args) -> {
			repository.save(new Card("title1", "description1"));
			repository.save(new Card("title2", "description2"));

			// fetch all customers
			log.info("Cards found with findAll():");
			log.info("-------------------------------");
			for (Card customer : repository.findAll()) {
				log.info(customer.toString());
			}
            log.info("");
		};
	}
}
