package next.domain.user;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;

public interface SrelloUserRepository extends CrudRepository<SrelloUser,Long>{
	@Cacheable("SrelloUsers")
	SrelloUser findByUserId(String userId);
}
