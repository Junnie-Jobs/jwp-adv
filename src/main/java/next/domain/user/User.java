package next.domain.user;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import lombok.Data;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="USER_TYPE", discriminatorType=DiscriminatorType.STRING)    
@Data
@JsonTypeInfo(
		use= JsonTypeInfo.Id.NAME,
		include = JsonTypeInfo.As.PROPERTY,
		property = "type"
)
@JsonSubTypes({
	@Type(value = SrelloUser.class, name = UserType.Values.SRELLO),
	@Type(value = GitHubUser.class, name = UserType.Values.GITHUB),
})
public abstract class User implements Serializable {
	private static final long serialVersionUID = 8216304454660739242L;

	public static final GuestUser GUEST_USER = new GuestUser();
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(nullable = false)
	private String userId;
	
	@Column(nullable = false)
	private String email;
	
	public User() {}
	
	public User(String userId, String email) {
		this.userId = userId;
		this.email = email;
	}
	
	public boolean isGuestUser() {
		return false;
	}
	
	@SuppressWarnings("serial")
	private static class GuestUser extends User {
		@Override
		public boolean isGuestUser() {
			return true;
		}
	}
}
