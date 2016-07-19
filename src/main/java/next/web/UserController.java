package next.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import next.domain.user.SrelloUser;
import next.domain.user.SrelloUserRepository;

@Controller
@RequestMapping("/users")
public class UserController {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private SrelloUserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@RequestMapping(value = "/signUp", method = RequestMethod.GET)
	public String signUpForm() {
		return "signUp";
	}
	
	@RequestMapping(value = "/signUp", method = RequestMethod.POST)
	public String signUp(SrelloUser user) {
		LOGGER.debug("user : {}", user);
		user.encodePassword(passwordEncoder);
		userRepository.save(user);
		return "redirect:/users/loginForm";
	}
	
	@RequestMapping(value = "/loginForm", method = RequestMethod.GET)
	public String loginForm() {
		return "login";
	}
}
