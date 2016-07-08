package next.domain.user;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.Data;

@Entity
@DiscriminatorValue(value = UserType.Values.SRELLO)
@Data
public class SrelloUser extends User {
	private String password;
	
	@Transient
	private String rawPassword;
	
	public SrelloUser() {
	}
	
	public SrelloUser(String name, String email, String password) {
		super(name, email);
		this.password = password;
	}
	
	public void encodePassword(PasswordEncoder passwordEncoder) {
		this.password = passwordEncoder.encode(rawPassword);
	}
}
