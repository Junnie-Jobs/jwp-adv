package next.domain.user;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonTypeName;

@Entity
@DiscriminatorValue(value = UserType.Values.GITHUB)
@JsonTypeName(UserType.Values.GITHUB)
public class GitHubUser extends User {
	private String name;
	
	private String accessToken;

	public GitHubUser() {
	}
	
	public GitHubUser(String userId, String email, String name, String accessToken) {
		super(userId, email);
		this.name = name;
		this.accessToken = accessToken;
	}

	public String getName() {
		return name;
	}
	
	public String getAccessToken() {
		return accessToken;
	}
}
