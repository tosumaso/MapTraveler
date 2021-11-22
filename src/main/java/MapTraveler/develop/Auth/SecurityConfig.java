package MapTraveler.develop.Auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	
	@Autowired
	ApplicationUserService appUserService;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public void configure(HttpSecurity http) throws Exception{
		http
			.csrf().disable()
			.authorizeRequests()
			.antMatchers("/newUser", "/css/*", "/js/*", "/images/*", "/practice").permitAll()
			.anyRequest()
			.authenticated()
			.and()
			.formLogin()
				.loginPage("/login").permitAll()
				.loginProcessingUrl("/login")
				.defaultSuccessUrl("/index", true)
				.passwordParameter("password")
				.usernameParameter("username")
			.and()
			.logout()
				.logoutUrl("/logout");
	}
	
	@Override
	public void configure(AuthenticationManagerBuilder auth)throws Exception{
		auth.userDetailsService(appUserService).passwordEncoder(passwordEncoder);
	}
	
	
}
