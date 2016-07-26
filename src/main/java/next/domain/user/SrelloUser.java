package next.domain.user;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.fasterxml.jackson.annotation.JsonTypeName;

@Entity
@DiscriminatorValue(value = UserType.Values.SRELLO)
@JsonTypeName(UserType.Values.SRELLO)
public class SrelloUser extends User {
	private static final long serialVersionUID = -8726379427468310776L;

	private String password;
	
	@Transient
	private String rawPassword;
	
	public SrelloUser() {
	}
	
	public SrelloUser(String userId, String email, String password) {
		super(userId, email);
		this.password = password;
	}
	
	public void setRawPassword(String rawPassword) {
		this.rawPassword = rawPassword;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void encodePassword(PasswordEncoder passwordEncoder) {
		this.password = passwordEncoder.encode(rawPassword);
	}
}
