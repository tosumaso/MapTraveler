package MapTraveler.develop.Auth;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ContextConfiguration;

import MapTraveler.develop.Entity.User;
import MapTraveler.develop.Repository.UserRepository;

//Springのコンテンツ(MockBean,Autowired)は使えない。DIしてもnullになる
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = ApplicationUserServiceTest.class)
class ApplicationUserServiceTest {

	@InjectMocks
	ApplicationUserService userService;

	@Mock
	UserRepository userRepository;

	@Test
	void loadUserByUsername() {
		User user = new User(1, "testuser", "password");
		Optional<User> opUser = Optional.of(user);
		when(userRepository.findByUsername(anyString())).thenReturn(opUser);
		UserDetails returnedUser = userService.loadUserByUsername("testuser");
		verify(userRepository).findByUsername(opUser.get().getUsername());
		assertThat(returnedUser.getUsername()).isEqualTo(opUser.get().getUsername());
		assertThat(returnedUser).isNotNull();
	}

	@Test
	void loadUserByUsernameNotFound() {
		String name = "nullTestuser";
		when(userRepository.findByUsername(anyString())).thenReturn(Optional.ofNullable(null));
		assertThrows(UsernameNotFoundException.class, () -> {
			userService.loadUserByUsername(name);
		});
	}
	
	@Test
	void createUser() {
		User user = User.builder().id(1).username("testuser").build();
		when(userRepository.save(any(User.class))).thenReturn(user);
		String password = "testPassword";
		User returnedUser = userService.createUser(user, password);
		verify(userRepository).save(user);
		assertThat(returnedUser).isNotNull();
		assertThat(returnedUser.getPassword()).hasSizeGreaterThan(30);
	}

}
