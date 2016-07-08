package next.domain.user;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@DiscriminatorValue(value = UserType.Values.SRELLO)
public class SrelloUser extends User {
	private String password;
	
	@Transient
	private String rawPassword;
	
	public SrelloUser() {
	}
	
	public SrelloUser(String userId, String email, String password) {
		super(userId, email);
		this.password = password;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void encodePassword(PasswordEncoder passwordEncoder) {
		this.password = passwordEncoder.encode(rawPassword);
	}
}
