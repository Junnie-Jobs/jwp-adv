package next.domain.user;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import core.test.IntegrationTest;

public class UserRepositoryTest extends IntegrationTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserRepositoryTest.class);
	
	@Autowired
	private SrelloUserRepository srelloUserRepository;
	
	@Autowired
	private GitHubUserRepository gitHubUserRepository;

	@Test
	public void cache() {
		SrelloUser sUser = new SrelloUser("javajigi", "javajigi@slipp.net", "password");
		srelloUserRepository.save(sUser);
		LOGGER.debug("user : {}", srelloUserRepository.findByUserId("javajigi"));
		LOGGER.debug("user : {}", srelloUserRepository.findByUserId("javajigi"));
		
		GitHubUser gUser = new GitHubUser("javajigi", "javajigi@slipp.net", "재성", "ACCESS_TOKEN");
		gitHubUserRepository.save(gUser);
		LOGGER.debug("user : {}", gitHubUserRepository.findByAccessToken("ACCESS_TOKEN"));
		LOGGER.debug("user : {}", gitHubUserRepository.findByAccessToken("ACCESS_TOKEN"));
	}
}
