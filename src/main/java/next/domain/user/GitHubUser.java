package next.domain.user;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = UserType.Values.GITHUB)
public class GitHubUser extends User {
	
	private String accessToken;

	public GitHubUser() {
	}
	
	public GitHubUser(String name, String email, String accessToken) {
		super(name, email);
		this.accessToken = accessToken;
	}
	
	public String getAccessToken() {
		return accessToken;
	}
}
