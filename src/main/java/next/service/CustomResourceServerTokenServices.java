package next.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.BaseOAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

import next.domain.user.GitHubUser;
import next.domain.user.GitHubUserRepository;

public class CustomResourceServerTokenServices implements ResourceServerTokenServices {
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomResourceServerTokenServices.class);
	
	@Autowired
	private GitHubUserRepository facebookUserRepository;
	
	private static final String[] PRINCIPAL_KEYS = new String[] { "user", "username",
			"userid", "user_id", "login", "id", "name" };
	
	private final String userInfoEndpointUrl;

	private final String clientId;

	private OAuth2RestOperations restTemplate;

	private String tokenType = DefaultOAuth2AccessToken.BEARER_TYPE;

	public CustomResourceServerTokenServices(String userInfoEndpointUrl, String clientId) {
		this.userInfoEndpointUrl = userInfoEndpointUrl;
		this.clientId = clientId;
	}
	
	@Override
	public OAuth2Authentication loadAuthentication(String accessToken)
			throws AuthenticationException, InvalidTokenException {
		LOGGER.debug("access token : {}", accessToken);
		GitHubUser githubUser = facebookUserRepository.findByAccessToken(accessToken);
		if (githubUser == null) {
			Map<String, Object> map = getMap(this.userInfoEndpointUrl, accessToken);
			LOGGER.debug("UserInfo : {}", map);
			if (map.containsKey("error")) {
				LOGGER.debug("userinfo returned error: " + map.get("error"));
				throw new InvalidTokenException(accessToken);
			}
			githubUser = new GitHubUser(map.get("name") + "", map.get("email") + "", accessToken);
			facebookUserRepository.save(githubUser);
			return extractAuthentication(map);
		}
		
		return null;
	}
	
	private OAuth2Authentication extractAuthentication(Map<String, Object> map) {
		Object principal = getPrincipal(map);
		List<GrantedAuthority> authorities = AuthorityUtils
				.commaSeparatedStringToAuthorityList("ROLE_USER");
		OAuth2Request request = new OAuth2Request(null, this.clientId, null, true, null,
				null, null, null, null);
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
				principal, "N/A", authorities);
		token.setDetails(map);
		return new OAuth2Authentication(request, token);
	}
	
	private Object getPrincipal(Map<String, Object> map) {
		for (String key : PRINCIPAL_KEYS) {
			if (map.containsKey(key)) {
				return map.get(key);
			}
		}
		return "unknown";
	}

	@SuppressWarnings({ "unchecked" })
	private Map<String, Object> getMap(String path, String accessToken) {
		try {
			OAuth2RestOperations restTemplate = this.restTemplate;
			if (restTemplate == null) {
				BaseOAuth2ProtectedResourceDetails resource = new BaseOAuth2ProtectedResourceDetails();
				resource.setClientId(this.clientId);
				restTemplate = new OAuth2RestTemplate(resource);
			}
			DefaultOAuth2AccessToken token = new DefaultOAuth2AccessToken(accessToken);
			token.setTokenType(this.tokenType);
			restTemplate.getOAuth2ClientContext().setAccessToken(token);
			return restTemplate.getForEntity(path, Map.class).getBody();
		}
		catch (Exception ex) {
			LOGGER.info("Could not fetch user details: " + ex.getClass() + ", "
					+ ex.getMessage());
			return Collections.<String, Object> singletonMap("error",
					"Could not fetch user details");
		}
	}
	
	@Override
	public OAuth2AccessToken readAccessToken(String accessToken) {
		throw new UnsupportedOperationException("Not supported: read access token");
	}
}
