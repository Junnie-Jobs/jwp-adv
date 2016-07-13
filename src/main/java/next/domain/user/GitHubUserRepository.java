package next.domain.user;

import org.springframework.data.repository.CrudRepository;

public interface GitHubUserRepository extends CrudRepository<GitHubUser, Long> {
	GitHubUser findByAccessToken(String accessToken);

	GitHubUser findByUserId(String userId);
}
