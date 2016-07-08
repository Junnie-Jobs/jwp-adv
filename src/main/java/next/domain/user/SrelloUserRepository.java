package next.domain.user;

import org.springframework.data.repository.CrudRepository;

public interface SrelloUserRepository extends CrudRepository<SrelloUser,Long>{
	SrelloUser findByUserId(String userId);
}
