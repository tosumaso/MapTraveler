package MapTraveler.develop.Auth;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import MapTraveler.develop.Entity.User;
import MapTraveler.develop.Repository.UserRepository;

@Service
public class ApplicationUserService implements UserDetailsService{
	
	@Autowired
	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
		Optional<User> user= userRepository.findByUsername(username);
		user.orElseThrow(()-> new UsernameNotFoundException("Username:" + username + "not found"));
		return user.map(ApplicationUser::new).get();
	}
	
	public User createUser(User user, String rawPassword) {
		String password = passwordEncoder().encode(rawPassword);
		user.setPassword(password);
		return userRepository.save(user);
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
