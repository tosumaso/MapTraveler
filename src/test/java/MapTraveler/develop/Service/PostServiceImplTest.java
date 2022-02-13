package MapTraveler.develop.Service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import MapTraveler.develop.Auth.ApplicationUser;
import MapTraveler.develop.Entity.Favourite;
import MapTraveler.develop.Entity.Image;
import MapTraveler.develop.Entity.Map;
import MapTraveler.develop.Entity.Post;
import MapTraveler.develop.Entity.User;
import MapTraveler.develop.Form.PostForm;
import MapTraveler.develop.Repository.CommentRepository;
import MapTraveler.develop.Repository.FavouriteRepository;
import MapTraveler.develop.Repository.ImageRepository;
import MapTraveler.develop.Repository.MapRepository;
import MapTraveler.develop.Repository.PostRepository;
import MapTraveler.develop.Repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class PostServiceImplTest {

	@Mock
	PostRepository postRepository;

	@Mock
	UserRepository userRepository;

	@Mock
	MapRepository mapRepository;

	@Mock
	ImageRepository imageRepository;

	@Mock
	FavouriteRepository favouriteRepository;
	
	@Mock
	CommentRepository commentRepository;
	
	@InjectMocks
	PostServiceImpl service;
	
	User user;
	ApplicationUser authUser;
	
	@BeforeEach
	void setUp() throws Exception {
		user = User.builder().id(1).password("test123").username("test").build();
		authUser = new ApplicationUser(user);
	}

	@Test
	void saveMapPost() throws IOException{
		List<String> texts = new ArrayList<>() {
			{
				add("testtext1");
				add("testtext2");
			}
		};
		List<MultipartFile> files = new ArrayList<>() {
			{
				add(new MockMultipartFile("file1","test1.jpeg","image/jpeg","test1".getBytes()));
				add(new MockMultipartFile("file2","test2.jpeg","image/jpeg","test2".getBytes()));
			}
		};
		PostForm form = new PostForm(12345.6,23456.7,"testTitle",4,texts,files);
		
		when(userRepository.findById(anyInt())).thenReturn(Optional.of(user));
		Map returnedMap = service.saveMapPost(authUser, form);
		verify(userRepository).findById(authUser.getId());
		verify(mapRepository).save(returnedMap);
		assertThat(returnedMap.getLat()).isEqualTo(form.getLat());
		assertThat(returnedMap.getPost().getTitle()).isEqualTo(form.getTitle());
		assertThat(returnedMap.getPost().getUser().getId()).isEqualTo(authUser.getId());
	}
	
	@Nested
	public class GetPostWithFlag{
		Post post;
		List<Image> images;
		
		@BeforeEach
		void setUp() {
			images = new ArrayList<>() {
				{
					add(Image.builder().id(1).data("testdata1".getBytes()).build());
					add(Image.builder().id(2).data("testdata2".getBytes()).build());
				}
			};
			post = Post.builder().id(1).build();
			when(postRepository.findById(anyInt())).thenReturn(Optional.of(post));
			when(imageRepository.findByPost(any(Post.class))).thenReturn(images);
			when(userRepository.findById(anyInt())).thenReturn(Optional.of(user));
		}
		
		@Test
		void getPostWithTrueFlag() {
			
			when(favouriteRepository.findByImageAndPostAndUser(any(Image.class), any(Post.class), any(User.class)))
				.thenReturn(Optional.of(new Favourite()));
			
			GetPostInfo info = service.getPost(authUser, 1);
			verify(postRepository).findById(1);
			verify(imageRepository).findByPost(post);
			verify(userRepository,times(images.size())).findById(authUser.getId());
			verify(favouriteRepository,times(images.size())).findByImageAndPostAndUser(any(Image.class), eq(post), eq(user));
			assertThat(info.getBase64List().get(1)).isEqualTo(Base64.getEncoder().encodeToString(images.get(1).getData()));
			assertThat(info.getFavouriteFlag().get(1)).isEqualTo(true);
			assertThat(info.getPost()).isNotNull();
		}
		
		@Test
		void getPostWithFalseFlag() {
			
			when(favouriteRepository.findByImageAndPostAndUser(any(Image.class), any(Post.class), any(User.class)))
				.thenReturn(Optional.ofNullable(null));
			
			GetPostInfo info = service.getPost(authUser, 1);
			verify(postRepository).findById(1);
			verify(imageRepository).findByPost(post);
			verify(userRepository,times(images.size())).findById(authUser.getId());
			verify(favouriteRepository,times(images.size())).findByImageAndPostAndUser(any(Image.class), eq(post), eq(user));
			assertThat(info.getBase64List().get(1)).isEqualTo(Base64.getEncoder().encodeToString(images.get(1).getData()));
			assertThat(info.getFavouriteFlag().get(1)).isEqualTo(false);
			assertThat(info.getPost()).isNotNull();
		}
	}

	
	
	@Test
	void findByKeyword() {
		List<Post> posts = new ArrayList<>() {
			{
				add(Post.builder().title("testKeyword1").build());
				add(Post.builder().title("testKeyword2").build());
			}
		};
		final String keyword = "testKeyword";
		when(postRepository.findByKeywordLike(anyString())).thenReturn(posts);
		List<Post> returnedPosts = service.findByKeyword(keyword);
		verify(postRepository).findByKeywordLike(keyword);
		assertThat(returnedPosts).hasSize(2).isNotNull();
		assertThat(returnedPosts.get(0).getTitle()).contains(keyword);
	}

	@Test
	void getMyPage() {
		
		List<Image> images1 = new ArrayList<>() {
			{
				add(Image.builder().id(1).data("testdata1".getBytes()).build());
				add(Image.builder().id(2).data("testdata2".getBytes()).build());
			}
		};
		List<Image> images2 = new ArrayList<>() {
			{
				add(Image.builder().id(3).data("testdata3".getBytes()).build());
				add(Image.builder().id(4).data("testdata4".getBytes()).build());
			}
		};
		
		List<Post> posts = new ArrayList<>() {
			{
				add(Post.builder().title("testKeyword1").images(images1).build());
				add(Post.builder().title("testKeyword2").images(images2).build());
			}
		};
		List<Favourite> favourites = new ArrayList<>() {
			{
				add(Favourite.builder().image(images1.get(0)).build());
				add(Favourite.builder().image(images2.get(1)).build());
			}
		};
		user.setLikes(favourites);
		
		when(userRepository.findById(anyInt())).thenReturn(Optional.of(user));
		when(postRepository.findByUser(anyInt())).thenReturn(posts);
		MyPageInfo info = service.getMyPage(authUser);
		verify(userRepository).findById(authUser.getId());
		verify(postRepository).findByUser(user.getId());
		assertThat(info.getImages()).hasSize(2);
		int i =0;
		for (String returnedEncoded : info.getImages()) {
			final String encoded = Base64.getEncoder().encodeToString(posts.get(i).getImages().get(0).getData());
			assertThat(returnedEncoded).isEqualTo(encoded);
			i++;
		}
		
	}

}
