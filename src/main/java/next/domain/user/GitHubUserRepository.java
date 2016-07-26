package next.domain.user;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;

public interface GitHubUserRepository extends CrudRepository<GitHubUser, Long> {
	@Cacheable("GithubUsers")
	GitHubUser findByAccessToken(String accessToken);

	GitHubUser findByUserId(String userId);
}
