package next.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import next.domain.UserRepository;

@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomUserDetailsService.class);
	
	@Autowired
	private UserRepository userRepository;

	@Transactional
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		LOGGER.debug("load username : {}", username);
		next.domain.User user = userRepository.findByName(username);
		LOGGER.debug("loaded User : {}", user);
		List<GrantedAuthority> authorities = buildUserAuthority();
		return buildUserForAuthentication(user, authorities);
	}
	
	private User buildUserForAuthentication(next.domain.User user, List<GrantedAuthority> authorities) {
		return new User(user.getName(), user.getPassword(),
				true, true, true, true, authorities);
	}
	
	private List<GrantedAuthority> buildUserAuthority() {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>(0);
		authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		return authorities;
	}
}
