package core.web.argumentresolver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import next.domain.user.GitHubUserRepository;
import next.domain.user.SrelloUserRepository;

public class LoginUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginUserHandlerMethodArgumentResolver.class);
	
	@Autowired
	private GitHubUserRepository gitHubUserRepository;
	
	@Autowired
	private SrelloUserRepository srelloUserRepository;
	
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.hasParameterAnnotation(LoginUser.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
			WebDataBinderFactory binderFactory) throws Exception {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!authentication.isAuthenticated()) {
			return next.domain.user.User.GUEST_USER;
		}
		
		if (authentication instanceof OAuth2Authentication) {
			String principal = (String)authentication.getPrincipal();
			LOGGER.debug("login principal : {}", principal);
			return gitHubUserRepository.findByUserId(principal);
		} else {
			User principal = (User)authentication.getPrincipal();
			LOGGER.debug("login principal : {}", principal);
			return srelloUserRepository.findByUserId(principal.getUsername());
		}
	}
}
