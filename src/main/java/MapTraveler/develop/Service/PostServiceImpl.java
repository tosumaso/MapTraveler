package MapTraveler.develop.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.HtmlUtils;

import MapTraveler.develop.Auth.ApplicationUser;
import MapTraveler.develop.Entity.Favourite;
import MapTraveler.develop.Entity.Image;
import MapTraveler.develop.Entity.Map;
import MapTraveler.develop.Entity.Post;
import MapTraveler.develop.Entity.Text;
import MapTraveler.develop.Entity.User;
import MapTraveler.develop.Form.PostForm;
import MapTraveler.develop.Repository.FavouriteRepository;
import MapTraveler.develop.Repository.ImageRepository;
import MapTraveler.develop.Repository.MapRepository;
import MapTraveler.develop.Repository.PostRepository;
import MapTraveler.develop.Repository.UserRepository;

@Service
@Transactional
public class PostServiceImpl implements PostService {

	@Autowired
	PostRepository postRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	MapRepository mapRepository;

	@Autowired
	ImageRepository imageRepository;

	@Autowired
	FavouriteRepository favouriteRepository;

	@Override
	public Map saveMapPost(ApplicationUser principal, PostForm form) throws IOException {
		Map map = new Map(form.getLat(), form.getLng());
		Post post = new Post(form.getTitle(), form.getStar());
		convertImageToBase64(post, form);
		List<String> contents = form.getTexts();
		for (String content : contents) {
			Text text = new Text(content);
			text.setPost(post);
			post.getTexts().add(text);
		}
		map.setPost(post);
		post.setMap(map);
		post.setUser(userRepository.findById(principal.getId()).get());
		mapRepository.save(map);
		return map;
	}

	@Override
	public GetPostInfo getPost(ApplicationUser principal, Integer id) {
		Post post = postRepository.findById(id).get();
		GetPostInfo info = convertImageToString(principal, post);
		info.setPost(post);
		return info;
	}
	
	@Override
	public List<Post> findByKeyword(String keyword){
		String escapedKeyword = HtmlUtils.htmlEscape(keyword);
		List<Post> posts = postRepository.findByKeywordLike(escapedKeyword);
		return posts;
	}
	
	@Override
	public MyPageInfo getMyPage(ApplicationUser principal) {
		User user = userRepository.findById(principal.getId()).get();
		List<String> images = new ArrayList<String>();
		for (Post post : postRepository.findByUser(user.getId())) {
			List<Image> image = post.getImages();
			for (int i = 0; i < image.size(); i++) {
				String im = Base64.getEncoder().encodeToString(image.get(i).getData());
				if (i == 0) {
					images.add(im);
				}
			}
		}
		
		List<String> favouriteImages = new ArrayList<String>();
		List<Post> posts = new ArrayList<Post>();
		List<Favourite> likes = user.getLikes();
		for (Favourite like : likes) {
			String im = Base64.getEncoder().encodeToString(like.getImage().getData());
			posts.add(like.getPost());
			favouriteImages.add(im);
		}
		
		return new MyPageInfo(user,images,posts,favouriteImages);
	}

	private void convertImageToBase64(Post post, PostForm form) throws IOException {
		List<MultipartFile> files = form.getFiles();
		List<String> images = new ArrayList<String>();
		for (MultipartFile file : files) { //複数の画像を保存、画像のバイナリデータをListにしてthymeleafで表示
			String fileName = StringUtils.cleanPath(file.getOriginalFilename()); //画像のパスを正規化
			Image savedFile = new Image(fileName, file.getContentType(), file.getBytes());
			savedFile.setPost(post);
			post.getImages().add(savedFile); //postエンティティの参照にImageエンティティを追加
			byte[] bytes = savedFile.getData(); //保存したImageエンティティのバイナリデータを取得
			images.add(Base64.getEncoder().encodeToString(bytes)); //バイナリデータを文字列にする
		}
	}

	private GetPostInfo convertImageToString(ApplicationUser principal, Post post) {
		List<Image> images = imageRepository.findByPost(post);
		List<String> base64List = images.stream().map(image -> Base64.getEncoder().encodeToString(image.getData()))
				.collect(Collectors.toList());
		List<Boolean> favouriteFlag = new ArrayList<Boolean>();
		for (int i = 0; i < images.size(); i++) {
			favouriteRepository
					.findByImageAndPostAndUser(images.get(i), post, userRepository.findById(principal.getId()).get())
					.ifPresentOrElse((f) -> favouriteFlag.add(true), () -> favouriteFlag.add(false));
		}
		return new GetPostInfo(images,base64List,favouriteFlag);
	}

}
