package next.config;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Resource(name = "customUserDetailsService")
	private UserDetailsService customUserDetailsService;
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	http.csrf().disable();
    	http.headers().frameOptions().disable();
    	
        http
            .authorizeRequests()
            	.antMatchers(HttpMethod.GET, "/b/**").authenticated()
            	.antMatchers("/boards/**").access("hasRole('ROLE_USER')")
                .anyRequest().permitAll()
                .and()
            .formLogin()
                .loginPage("/users/loginForm")
                .loginProcessingUrl("/users/login")
                .permitAll()
                .and()
            .logout()
                .permitAll();
        
        http.httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    	auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
    }
    
    @Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
