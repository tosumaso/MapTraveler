package MapTraveler.develop.Controller;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import MapTraveler.develop.Auth.ApplicationUser;
import MapTraveler.develop.Auth.ApplicationUserService;
import MapTraveler.develop.Entity.Comment;
import MapTraveler.develop.Entity.Favourite;
import MapTraveler.develop.Entity.Image;
import MapTraveler.develop.Entity.Map;
import MapTraveler.develop.Entity.Post;
import MapTraveler.develop.Entity.Text;
import MapTraveler.develop.Entity.User;
import MapTraveler.develop.Form.DeleteFavouriteForm;
import MapTraveler.develop.Form.PostForm;
import MapTraveler.develop.Service.CommentService;
import MapTraveler.develop.Service.FavouriteService;
import MapTraveler.develop.Service.GetPostInfo;
import MapTraveler.develop.Service.MapService;
import MapTraveler.develop.Service.MyPageInfo;
import MapTraveler.develop.Service.PostService;

@WebMvcTest(MapTravelerController.class)
//@TestPropertySource: test用のpropertiesファイルを検索
@TestPropertySource(locations = "classpath:test.properties")
class MapTravelerControllerTest {

	@MockBean
	MapService mapService;

	@MockBean
	PostService postService;

	@MockBean
	CommentService commentService;

	@MockBean
	FavouriteService favouriteService;

	@MockBean
	ApplicationUserService userService;

	@MockBean
	PasswordEncoder passwordEncoder;

	@MockBean
	Authentication auth;

	@Autowired
	MockMvc mockMvc;

	@Captor
	ArgumentCaptor<ApplicationUser> appUserCaptor;

	@Captor
	ArgumentCaptor<PostForm> postFormCaptor;

	private ApplicationUser authUser;

	private
	ObjectMapper mapper;
	
	@BeforeEach
	void setUp() throws Exception {
		authUser = new ApplicationUser(new User(1, "username1", "password1"));
	}

	@AfterEach
	void tearDown() {
		reset(mapService, postService, commentService, favouriteService);
	}

	//MockUserでテストを行う場合＠AuthenticationPrincipalやAuthenticationで認証がnullになる
	//Controller内でSecurityContextHolder.getContext().getAuthentication()で認証オブジェクトを直接取得する
	@Test
	void getIndex() throws Exception {
		mockMvc.perform(get("/index").with(user(authUser)))
				.andExpect(status().isOk())
				.andExpect(model().attribute("username", "username1"))
				.andExpect(view().name("indexs"));
	}

	@Test
	void getMarkers() throws Exception {
		List<Map> markers = new ArrayList<Map>();
		markers.add(new Map(123456.7, 890123.4));
		when(mapService.findAll()).thenReturn(markers);
		mockMvc.perform(get("/get/markers").with(user(authUser)))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0].lat", is(123456.7)));
	}

	@Test
	void postGoogleWithErrors() throws Exception {
		mockMvc.perform(post("/postMap").with(user(authUser))
				.param("lat", "123456.7")
				.param("lng", "67890.1")
				.param("title", "")
				.param("star", "5"))
				.andExpect(status().is3xxRedirection())
				//flash(): addFlashAttributeでmodelに登録したときに使用
				.andExpect(flash().attribute("modalError", "全ての空白を埋めてください"))
				.andExpect(view().name("redirect:/index"));
	}

	@Test
	void postGoogleSuccess() throws Exception {
		Map map = new Map();
		Post post = new Post();
		post.setImages(new ArrayList<Image>() {
			{
				add(new Image());
			}
		});
		post.setTexts(new ArrayList<Text>() {
			{
				add(new Text());
			}
		});
		map.setPost(post);

		when(postService.saveMapPost(appUserCaptor.capture(), postFormCaptor.capture())).thenReturn(map);
		mockMvc.perform(post("/postMap").with(user(authUser))
				.param("lat", "123456.7")
				.param("lng", "67890.1")
				.param("title", "testTitle")
				.param("star", "5"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/index"));

		assertThat(map.getPost().getImages().get(0), notNullValue());
		assertThat(map.getPost().getTexts().get(0), notNullValue());
	}

	@Test
	void getPostMap() throws Exception {
		Image image = Image.builder().id(1).name("testImage").type("image/jpeg").build();
		List<Image> images = Arrays.asList(image);
		List<String> base64List = Arrays.asList("testBase64");
		List<Boolean> favouriteFlag = Arrays.asList(false);
		Post post = Post.builder().id(1).title("testTitle").star(5).user(new User(1, "username1", "password1")).build();
		post.setTexts(Arrays.asList(new Text("testContent")));
		GetPostInfo postInfo = new GetPostInfo(images, base64List, favouriteFlag);
		postInfo.setPost(post);

		when(postService.getPost(any(), any())).thenReturn(postInfo);
		when(commentService.findAll()).thenReturn(Arrays.asList(new Comment()));

		mockMvc.perform(get("/getPostMap").with(user(authUser))
				.param("id", "1"))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("username"))
				.andExpect(model().attributeExists("userId"))
				.andExpect(model().attributeExists("post"))
				.andExpect(model().attributeExists("images"))
				.andExpect(model().attributeExists("base64List"))
				.andExpect(model().attributeExists("favouriteFlag"))
				.andExpect(model().attributeExists("comments"))
				.andExpect(view().name("post"));
	}

	@Nested
	public class sendOrDeleteFavourite {

		List<Favourite> favourites;

		@BeforeEach
		void setUp() throws Exception {
			favourites = new ArrayList<Favourite>() {
				{
					add(new Favourite());
					add(new Favourite());
				}
			};
			when(favouriteService.saveOrDeleteFavourite(appUserCaptor.capture(), anyInt(), anyInt()))
					.thenReturn(favourites);
			mapper = new ObjectMapper();
		}

		@Test
		void countUpFavourite() throws Exception {

			mockMvc.perform(get("/send/Myfavourite")
					.with(user(authUser))
					.param("imageIndex", "1")
					.param("postId", "1"))
					.andExpect(status().isOk())
					.andExpect(content().contentType(MediaType.APPLICATION_JSON))
					.andExpect(jsonPath("$", hasSize(2)));
		}

		@Test
		void deleteFavourite() throws Exception {
			favourites.remove(0);
			DeleteFavouriteForm form = new DeleteFavouriteForm(1,1);
			mockMvc.perform(post("/delete/Myfavourite")
				.with(user(authUser))
				//リクエストに含まれたデータがJsonであることを証明
				.contentType(MediaType.APPLICATION_JSON)
				//ObjectMapperを使いリクエストのJsonを作成
				.content(mapper.writeValueAsString(form)))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			
			.andExpect(jsonPath("$",hasSize(1)));
		}
	}

	@Test
	void searchByKeyword() throws Exception{
		List<Post> posts = Arrays.asList(new Post(),new Post());
		when(postService.findByKeyword(anyString())).thenReturn(posts);
		mockMvc.perform(get("/search")
				.with(user(authUser))
				.param("keyword", "testKeyword"))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$",hasSize(2)));
	}
	
	@Test
	void getMypage() throws Exception{
		User user = new User();
		user.setLikes(Arrays.asList(new Favourite()));
		MyPageInfo info = new MyPageInfo(
				user,
				Arrays.asList("testImagesEncoded"),
				Arrays.asList(new Post()),
				Arrays.asList("favouriteImagesEncoded"));
		when(postService.getMyPage(authUser)).thenReturn(info);
		
		mockMvc.perform(get("/mypage")
				.with(user(authUser)))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("user","images","posts","favouriteImages","likes"))
		.andExpect(view().name("mypages"));
	}
}
