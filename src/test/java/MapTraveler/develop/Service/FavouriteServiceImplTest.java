package MapTraveler.develop.Service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import MapTraveler.develop.Auth.ApplicationUser;
import MapTraveler.develop.Entity.Favourite;
import MapTraveler.develop.Entity.Image;
import MapTraveler.develop.Entity.Post;
import MapTraveler.develop.Entity.User;
import MapTraveler.develop.Repository.FavouriteRepository;
import MapTraveler.develop.Repository.PostRepository;
import MapTraveler.develop.Repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class FavouriteServiceImplTest {

	@Mock
	PostRepository postRepository;
	
	@Mock
	UserRepository userRepository;
	
	@Mock
	FavouriteRepository favouriteRepository;
	
	@InjectMocks
	FavouriteServiceImpl service;
	
	List<Image> images = new ArrayList<Image>();
	List<Favourite> returnedFavourites = new ArrayList<Favourite>();
	User user;
	Post post;
	ApplicationUser authUser;
	Image image;
	Favourite favourite;
	
	@BeforeEach
	void setUp() throws Exception {
		user = User.builder().id(1).password("test123").username("test").build();
		post = Post.builder().id(1).images(images).build();
		authUser = new ApplicationUser(user);
		image = Image.builder().id(1).build();
		images.add(image);
		favourite = Favourite.builder().id(1).build();
		when(postRepository.findById(anyInt())).thenReturn(Optional.of(post));
		when(userRepository.findById(anyInt())).thenReturn(Optional.of(user));
		when(favouriteRepository.findByImage(any(Image.class))).thenReturn(returnedFavourites);
	}
	
	@Test
	void deleteFavourite(){	
		
		when(favouriteRepository.findByImageAndPostAndUser(any(Image.class), any(Post.class), any(User.class)))
			.thenReturn(Optional.of(favourite));
		List<Favourite> actualFavourites = service.saveOrDeleteFavourite(authUser, 0, 1);
		
		verify(postRepository).findById(post.getId());
		verify(userRepository).findById(user.getId());
		verify(favouriteRepository).findByImageAndPostAndUser(image, post, user);
		verify(favouriteRepository).deleteById(favourite.getId());
		verify(favouriteRepository).findByImage(image);
		
		assertThat(actualFavourites).hasSize(0);
	}
	
	@Test
	void saveFavourite() {
		when(favouriteRepository.findByImageAndPostAndUser(any(Image.class), any(Post.class), any(User.class)))
		.thenReturn(Optional.ofNullable(null));
		returnedFavourites.add(favourite);
		List<Favourite> actualFavourites = service.saveOrDeleteFavourite(authUser, 0, 1);
		
		verify(postRepository).findById(post.getId());
		verify(userRepository).findById(user.getId());
		verify(favouriteRepository).findByImageAndPostAndUser(image, post, user);
		verify(favouriteRepository).save(any(Favourite.class));
		verify(favouriteRepository).findByImage(image);
	
		assertThat(actualFavourites).hasSize(1);
	}

}
